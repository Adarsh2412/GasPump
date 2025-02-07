package com.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import pumpservice.PaymentServiceGrpc;
import pumpservice.PumpServiceGrpc;
import pumpservice.Pumpservice.*;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FuelPump {
    private final ManagedChannel pumpChannel;
    private final ManagedChannel paymentChannel;
    private final PumpServiceGrpc.PumpServiceStub asyncStub;
    private final PaymentServiceGrpc.PaymentServiceStub paymentStub;
    private final Scanner scanner;
    private boolean authenticated = false;
    private Map<String, Double> fuelPrices;

    // ✅ Constructor for Testing
    public FuelPump(ManagedChannel pumpChannel, ManagedChannel paymentChannel,
                PumpServiceGrpc.PumpServiceStub asyncStub, PaymentServiceGrpc.PaymentServiceStub paymentStub) {
    this.pumpChannel = pumpChannel;
    this.paymentChannel = paymentChannel;
    this.asyncStub = asyncStub;
    this.paymentStub = paymentStub;
    this.scanner = new Scanner(System.in);
}


    // ✅ Constructor for Actual Execution
    public FuelPump(String pumpAddress, String paymentAddress) {
        this.pumpChannel = ManagedChannelBuilder.forTarget(pumpAddress).usePlaintext().build();
        this.paymentChannel = ManagedChannelBuilder.forTarget(paymentAddress).usePlaintext().build();
        this.asyncStub = PumpServiceGrpc.newStub(pumpChannel);
        this.paymentStub = PaymentServiceGrpc.newStub(paymentChannel);
        this.scanner = new Scanner(System.in);
    }

    public void authenticatePump() {
        System.out.print("🔢 Enter Pump ID (4-digit): ");
        int pumpId = scanner.nextInt();

        System.out.print("🔑 Enter Passcode (3-digit): ");
        int passcode = scanner.nextInt();
        scanner.nextLine();  // ✅ Consume newline

        PumpAuthRequest authRequest = PumpAuthRequest.newBuilder()
                .setPumpId(pumpId)
                .setPasscode(passcode)
                .build();

        CountDownLatch latch = new CountDownLatch(1);

        asyncStub.authenticatePump(authRequest, new StreamObserver<PumpAuthResponse>() {
            @Override
            public void onNext(PumpAuthResponse response) {
                if (!response.getAuthenticated()) {
                    System.out.println("❌ Authentication failed. Exiting.");
                    shutdown();
                } else {
                    authenticated = true;
                    fuelPrices = response.getFuelPricesMap();
                    System.out.println("✅ Authentication successful. You can request fuel.");
                    displayFuelPrices();
                    latch.countDown();
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("⚠️ Error during authentication: " + t.getMessage());
                latch.countDown();
                shutdown();
            }

            @Override
            public void onCompleted() {
            }
        });

        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("❌ Authentication timed out.");
        }

        if (authenticated) {
            requestFuelLoop();
        }
    }

    private void displayFuelPrices() {
        System.out.println("📢 Current Fuel Prices:");
        for (Map.Entry<String, Double> entry : fuelPrices.entrySet()) {
            System.out.println("  ⛽ " + entry.getKey() + ": $" + entry.getValue() + " per liter");
        }
    }

    public void requestFuelLoop() {
        while (true) {
            System.out.print("🛢 Select Fuel Type (R - Regular, M - Mid-Grade, S - Super) or type 'exit' to quit: ");
            String fuelType = scanner.nextLine().toUpperCase();

            if (fuelType.equals("EXIT")) {
                System.out.println("🚪 Exiting fuel pump session...");
                break;
            }

            if (!fuelPrices.containsKey(fuelType)) {
                System.out.println("❌ Invalid fuel type. Try again.");
                continue;
            }

            System.out.print("⛽ Enter Fuel Liters: ");
            double liters;
            try {
                liters = Double.parseDouble(scanner.nextLine());
                if (liters <= 0) {
                    System.out.println("❌ Fuel liters must be positive. Try again.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                continue;
            }

            double totalCost = fuelPrices.get(fuelType) * liters;
            System.out.println("💲 Total Cost: $" + totalCost);

            System.out.print("💳 Choose payment type (CASH / CREDIT_CARD): ");
            String paymentType = scanner.nextLine().toUpperCase();

            boolean paymentApproved = false;
            if (paymentType.equals("CASH")) {
                System.out.print("💰 Enter payment amount: ");
                double payment;
                try {
                    payment = Double.parseDouble(scanner.nextLine());
                    if (payment < totalCost) {
                        System.out.println("❌ Insufficient funds. Transaction cancelled.");
                        continue;
                    }
                    paymentApproved = true;
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid payment. Please enter a valid number.");
                    continue;
                }
            } else if (paymentType.equals("CREDIT_CARD")) {
                System.out.print("💳 Enter card number: ");
                long cardNumber = scanner.nextLong();
                scanner.nextLine();  // ✅ Consume newline
                paymentApproved = processCreditCardPayment(totalCost, cardNumber);
            } else {
                System.out.println("❌ Invalid payment type. Try again.");
                continue;
            }

            if (paymentApproved) {
                sendFuelRequest(fuelType, liters, totalCost);
            }
        }
        shutdown();
    }

    private boolean processCreditCardPayment(double amount, long cardNumber) {
        CountDownLatch latch = new CountDownLatch(1);
        final boolean[] approved = {false};

        PaymentRequest paymentRequest = PaymentRequest.newBuilder()
                .setPaymentType("CREDIT_CARD")
                .setAmount(amount)
                .setCardNumber(cardNumber)
                .build();

        paymentStub.processPayment(paymentRequest, new StreamObserver<PaymentResponse>() {
            @Override
            public void onNext(PaymentResponse response) {
                approved[0] = response.getApproved();
                System.out.println("📢 Payment Processing Response: " + response.getMessage());
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("⚠️ Error processing payment: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
            }
        });

        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("❌ Payment request timed out.");
        }

        return approved[0];
    }

    private void sendFuelRequest(String fuelType, double liters, double payment) {
        FuelRequest request = FuelRequest.newBuilder()
                .setFuelType(fuelType)
                .setRequestedLiters(liters)
                .setPaymentAmount(payment)
                .build();

        CountDownLatch latch = new CountDownLatch(1);

        asyncStub.requestFuel(request, new StreamObserver<FuelResponse>() {
            @Override
            public void onNext(FuelResponse response) {
                System.out.println("📢 Server Response: " + response.getMessage());
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("⚠️ Error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
            }
        });

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("❌ Fuel request timed out.");
        }
    }

    public void shutdown() {
        scanner.close();
        pumpChannel.shutdown();
        paymentChannel.shutdown();
        System.out.println("🔴 FuelPump shut down.");
    }

    public static void main(String[] args) {
        FuelPump pump = new FuelPump("localhost:50051", "localhost:50052");
        pump.authenticatePump();
    }
}

package com.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import pumpservice.PumpServiceGrpc;
import pumpservice.Pumpservice.FuelRequest;
import pumpservice.Pumpservice.FuelResponse;
import pumpservice.Pumpservice.PumpAuthRequest;
import pumpservice.Pumpservice.PumpAuthResponse;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CentralNode extends PumpServiceGrpc.PumpServiceImplBase {
    private final HashMap<String, Double> fuelPrices = new HashMap<>();
    private final HashMap<String, Double> fuelStock = new HashMap<>();
    private final HashMap<Integer, Integer> registeredPumps = new HashMap<>();
    private double totalSales = 0.0;  // ✅ Tracks total money made in a day
    private static final String PUMP_DATA_FILE = "pumps.dat";

    public CentralNode() {
        Scanner scanner = new Scanner(System.in);
        
        // Set fuel prices
        System.out.print("💰 Enter price for Regular fuel: ");
        fuelPrices.put("R", scanner.nextDouble());

        System.out.print("💰 Enter price for Mid-Grade fuel: ");
        fuelPrices.put("M", scanner.nextDouble());

        System.out.print("💰 Enter price for Super fuel: ");
        fuelPrices.put("S", scanner.nextDouble());

        // Set initial fuel stock
        System.out.print("⛽ Enter starting fuel stock for Regular: ");
        fuelStock.put("R", scanner.nextDouble());

        System.out.print("⛽ Enter starting fuel stock for Mid-Grade: ");
        fuelStock.put("M", scanner.nextDouble());

        System.out.print("⛽ Enter starting fuel stock for Super: ");
        fuelStock.put("S", scanner.nextDouble());

        System.out.println("✅ Prices set: " + fuelPrices);
        System.out.println("✅ Fuel stock initialized: " + fuelStock);

        // Load pump data from binary file
        loadPumpData();
    }

    private void loadPumpData() {
        File file = new File(PUMP_DATA_FILE);
        if (!file.exists()) {
            System.err.println("⚠️ Warning: Pump data file not found. No pumps are registered.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            registeredPumps.clear();
            registeredPumps.putAll((HashMap<Integer, Integer>) ois.readObject());
            System.out.println("✅ Registered Pumps Loaded: " + registeredPumps);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("⚠️ Error reading pump data: " + e.getMessage());
        }
    }

    @Override
    public void authenticatePump(PumpAuthRequest request, StreamObserver<PumpAuthResponse> responseObserver) {
        int pumpId = request.getPumpId();
        int passcode = request.getPasscode();

        boolean isAuthenticated = registeredPumps.containsKey(pumpId) && registeredPumps.get(pumpId) == passcode;

        PumpAuthResponse.Builder responseBuilder = PumpAuthResponse.newBuilder()
                .setAuthenticated(isAuthenticated);

        if (isAuthenticated) {
            responseBuilder.putAllFuelPrices(fuelPrices);
            System.out.println("✅ Pump " + pumpId + " authenticated.");
        } else {
            System.out.println("❌ Authentication failed for pump " + pumpId);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void requestFuel(FuelRequest request, StreamObserver<FuelResponse> responseObserver) {
        String fuelType = request.getFuelType();
        double liters = request.getRequestedLiters();
        double amountPaid = request.getPaymentAmount();
        double requiredAmount = fuelPrices.getOrDefault(fuelType, 0.0) * liters;

        if (!fuelStock.containsKey(fuelType) || fuelStock.get(fuelType) < liters) {
            System.out.println("❌ Not enough " + fuelType + " fuel available.");
            responseObserver.onNext(FuelResponse.newBuilder()
                    .setMessage("❌ Not enough " + fuelType + " fuel available.")
                    .setApproved(false)
                    .build());
        } else if (amountPaid < requiredAmount) {
            System.out.println("❌ Payment rejected. Insufficient amount.");
            responseObserver.onNext(FuelResponse.newBuilder()
                    .setMessage("❌ Payment rejected. Amount required: $" + requiredAmount)
                    .setApproved(false)
                    .build());
        } else {
            // Update fuel stock and sales
            fuelStock.put(fuelType, fuelStock.get(fuelType) - liters);
            totalSales += requiredAmount;
            System.out.println("✅ Payment accepted: $" + requiredAmount);
            System.out.println("📢 Total sales today: $" + totalSales);
            System.out.println("📢 Remaining fuel stock: " + fuelStock);

            responseObserver.onNext(FuelResponse.newBuilder()
                    .setMessage("✅ Fuel approved for " + liters + " liters of " + fuelType + ".")
                    .setApproved(true)
                    .build());
        }
        responseObserver.onCompleted();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        CentralNode server = new CentralNode();
        Server grpcServer = ServerBuilder.forPort(50051)
                .addService(server)
                .build()
                .start();

        System.out.println("🚀 Central Node is running on port 50051...");
        grpcServer.awaitTermination();
    }
}

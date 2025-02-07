package com.example;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.Server;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.*;
import pumpservice.PumpServiceGrpc;
import pumpservice.PaymentServiceGrpc;
import pumpservice.Pumpservice.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FuelPumpTest {
    private Server pumpServer;
    private Server paymentServer;
    private ManagedChannel pumpChannel;
    private ManagedChannel paymentChannel;
    private PumpServiceGrpc.PumpServiceBlockingStub pumpStub;
    private PaymentServiceGrpc.PaymentServiceBlockingStub paymentStub;

    @BeforeAll
    void setupServer() throws IOException {
        // Mock Central Node
        pumpServer = InProcessServerBuilder.forName("test-fuel-pump")
                .directExecutor()
                .addService(new FakeCentralNodeService()) // ✅ Attach Fake CentralNode
                .build()
                .start();

        // Mock Payment Processing Node
        paymentServer = InProcessServerBuilder.forName("test-payment-node")
                .directExecutor()
                .addService(new FakePaymentProcessingNode()) // ✅ Attach Fake Payment Node
                .build()
                .start();

        pumpChannel = InProcessChannelBuilder.forName("test-fuel-pump")
                .directExecutor()
                .build();
        paymentChannel = InProcessChannelBuilder.forName("test-payment-node")
                .directExecutor()
                .build();

        pumpStub = PumpServiceGrpc.newBlockingStub(pumpChannel);
        paymentStub = PaymentServiceGrpc.newBlockingStub(paymentChannel);
    }

    @AfterAll
    void teardownServer() throws InterruptedException {
        pumpServer.shutdown().awaitTermination();
        paymentServer.shutdown().awaitTermination();
        pumpChannel.shutdown();
        paymentChannel.shutdown();
    }

    @Test
    void testValidFuelRequest() {
        FuelRequest request = FuelRequest.newBuilder()
                .setFuelType("R")
                .setRequestedLiters(15)
                .setPaymentAmount(20)
                .build();

        FuelResponse response = pumpStub.requestFuel(request);
        assertTrue(response.getApproved(), "Fuel request should be approved");
    }

    @Test
    void testInvalidFuelTypeRequest() {
        FuelRequest request = FuelRequest.newBuilder()
                .setFuelType("X")
                .setRequestedLiters(15)
                .setPaymentAmount(20)
                .build();

        FuelResponse response = pumpStub.requestFuel(request);
        assertFalse(response.getApproved(), "Fuel request with invalid fuel type should be rejected");
    }

    @Test
    void testValidCreditCardPayment() {
        PaymentRequest request = PaymentRequest.newBuilder()
                .setPaymentType("CREDIT_CARD")
                .setAmount(50)
                .setCardNumber(1234567890123456L)
                .build();

        PaymentResponse response = paymentStub.processPayment(request);
        assertTrue(response.getApproved(), "Credit card payment should be approved");
    }

    @Test
    void testInvalidPaymentType() {
        PaymentRequest request = PaymentRequest.newBuilder()
                .setPaymentType("BITCOIN")
                .setAmount(50)
                .build();

        PaymentResponse response = paymentStub.processPayment(request);
        assertFalse(response.getApproved(), "Invalid payment type should be rejected");
    }

    private static class FakeCentralNodeService extends PumpServiceGrpc.PumpServiceImplBase {
        @Override
        public void requestFuel(FuelRequest request, StreamObserver<FuelResponse> responseObserver) {
            if (!request.getFuelType().matches("[RMS]") || request.getRequestedLiters() <= 0) {
                responseObserver.onNext(FuelResponse.newBuilder().setApproved(false).setMessage("Invalid request").build());
            } else {
                responseObserver.onNext(FuelResponse.newBuilder().setApproved(true).setMessage("Fuel approved").build());
            }
            responseObserver.onCompleted();
        }
    }

    private static class FakePaymentProcessingNode extends PaymentServiceGrpc.PaymentServiceImplBase {
        @Override
        public void processPayment(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
            boolean approved = request.getPaymentType().equals("CREDIT_CARD") && request.getAmount() > 0;

            responseObserver.onNext(PaymentResponse.newBuilder()
                    .setApproved(approved)
                    .setMessage(approved ? "✅ Payment Approved" : "❌ Payment Declined")
                    .build());

            responseObserver.onCompleted();
        }
    }
}

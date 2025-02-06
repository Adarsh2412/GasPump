package com.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import pumpservice.PaymentServiceGrpc;
import pumpservice.Pumpservice.PaymentRequest;
import pumpservice.Pumpservice.PaymentResponse;

import java.io.IOException;
import java.util.Random;

public class PaymentProcessingNode extends PaymentServiceGrpc.PaymentServiceImplBase {
    private final Random random = new Random();

    @Override
    public void processPayment(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
        if ("CREDIT_CARD".equals(request.getPaymentType())) {
            boolean approved = random.nextBoolean();  // Simulate random approval
            String message = approved ? "✅ Payment Approved" : "❌ Payment Declined";

            System.out.println("💳 Processing credit card payment of $" + request.getAmount());
            System.out.println("🔔 Status: " + message);

            responseObserver.onNext(PaymentResponse.newBuilder()
                    .setApproved(approved)
                    .setMessage(message)
                    .build());
        } else {
            responseObserver.onNext(PaymentResponse.newBuilder()
                    .setApproved(false)
                    .setMessage("❌ Invalid payment type")
                    .build());
        }

        responseObserver.onCompleted();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        PaymentProcessingNode server = new PaymentProcessingNode();
        Server grpcServer = ServerBuilder.forPort(50052)
                .addService(server)
                .build()
                .start();

        System.out.println("💳 Payment Processing Server is running on port 50052...");
        grpcServer.awaitTermination();
    }
}

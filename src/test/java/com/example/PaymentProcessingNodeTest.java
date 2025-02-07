package com.example;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.Server;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.*;
import pumpservice.PaymentServiceGrpc;
import pumpservice.Pumpservice.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentProcessingNodeTest {
    private Server server;
    private ManagedChannel channel;
    private PaymentServiceGrpc.PaymentServiceBlockingStub blockingStub;

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
    

    @BeforeAll
    void setupServer() throws IOException {
        server = InProcessServerBuilder.forName("test-payment-node")
                .directExecutor()
                .addService(new FakePaymentProcessingNode())
                .build()
                .start();

        channel = InProcessChannelBuilder.forName("test-payment-node")
                .directExecutor()
                .build();

        blockingStub = PaymentServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    void teardownServer() throws InterruptedException {
        server.shutdown().awaitTermination();
        channel.shutdown();
    }

    @Test
    void testValidCreditCardPayment() {
        PaymentRequest request = PaymentRequest.newBuilder()
                .setPaymentType("CREDIT_CARD")
                .setAmount(50)
                .setCardNumber(1234567890123456L)
                .build();

        PaymentResponse response = blockingStub.processPayment(request);
        assertTrue(response.getApproved(), "Credit card payment should be approved");
    }

    @Test
    void testInvalidPaymentType() {
        PaymentRequest request = PaymentRequest.newBuilder()
                .setPaymentType("BITCOIN")
                .setAmount(50)
                .build();

        PaymentResponse response = blockingStub.processPayment(request);
        assertFalse(response.getApproved(), "Invalid payment type should be rejected");
    }
}

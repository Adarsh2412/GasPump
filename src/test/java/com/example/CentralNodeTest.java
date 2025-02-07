package com.example;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.Server;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.*;
import pumpservice.PumpServiceGrpc;
import pumpservice.Pumpservice.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CentralNodeTest {
    private Server server;
    private ManagedChannel channel;
    private PumpServiceGrpc.PumpServiceBlockingStub blockingStub;

    @BeforeAll
    void setupServer() throws IOException {
        server = InProcessServerBuilder.forName("test-central-node")
                .directExecutor()
                .addService(new FakeCentralNodeService()) // Attach Fake Service
                .build()
                .start();

        channel = InProcessChannelBuilder.forName("test-central-node")
                .directExecutor()
                .build();

        blockingStub = PumpServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    void teardownServer() throws InterruptedException {
        server.shutdown().awaitTermination();
        channel.shutdown();
    }

    @Test
    void testSuccessfulAuthentication() {
        PumpAuthRequest request = PumpAuthRequest.newBuilder()
                .setPumpId(1234)
                .setPasscode(567)
                .build();

        PumpAuthResponse response = blockingStub.authenticatePump(request);
        assertTrue(response.getAuthenticated(), "Pump authentication should be successful");
    }

    @Test
    void testFailedAuthentication() {
        PumpAuthRequest request = PumpAuthRequest.newBuilder()
                .setPumpId(9999)
                .setPasscode(111)
                .build();

        PumpAuthResponse response = blockingStub.authenticatePump(request);
        assertFalse(response.getAuthenticated(), "Pump authentication should fail");
    }

    @Test
    void testValidFuelRequest() {
        FuelRequest request = FuelRequest.newBuilder()
                .setFuelType("R")
                .setRequestedLiters(10)
                .setPaymentAmount(10)
                .build();

        FuelResponse response = blockingStub.requestFuel(request);
        assertTrue(response.getApproved(), "Fuel request should be approved");
    }

    @Test
    void testInvalidFuelType() {
        FuelRequest request = FuelRequest.newBuilder()
                .setFuelType("X")
                .setRequestedLiters(10)
                .setPaymentAmount(10)
                .build();

        FuelResponse response = blockingStub.requestFuel(request);
        assertFalse(response.getApproved(), "Fuel request with invalid fuel type should fail");
    }

    @Test
    void testInvalidLitersRequest() {
        FuelRequest request = FuelRequest.newBuilder()
                .setFuelType("R")
                .setRequestedLiters(-5)
                .setPaymentAmount(10)
                .build();

        FuelResponse response = blockingStub.requestFuel(request);
        assertFalse(response.getApproved(), "Fuel request with negative liters should fail");
    }

    private static class FakeCentralNodeService extends PumpServiceGrpc.PumpServiceImplBase {
        @Override
        public void authenticatePump(PumpAuthRequest request, StreamObserver<PumpAuthResponse> responseObserver) {
            boolean authenticated = request.getPumpId() == 1234 && request.getPasscode() == 567;
            responseObserver.onNext(PumpAuthResponse.newBuilder().setAuthenticated(authenticated).build());
            responseObserver.onCompleted();
        }

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
}

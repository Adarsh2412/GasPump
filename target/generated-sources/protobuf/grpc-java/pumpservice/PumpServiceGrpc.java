package pumpservice;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.44.1)",
    comments = "Source: pumpservice.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PumpServiceGrpc {

  private PumpServiceGrpc() {}

  public static final String SERVICE_NAME = "pumpservice.PumpService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pumpservice.Pumpservice.PumpAuthRequest,
      pumpservice.Pumpservice.PumpAuthResponse> getAuthenticatePumpMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AuthenticatePump",
      requestType = pumpservice.Pumpservice.PumpAuthRequest.class,
      responseType = pumpservice.Pumpservice.PumpAuthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pumpservice.Pumpservice.PumpAuthRequest,
      pumpservice.Pumpservice.PumpAuthResponse> getAuthenticatePumpMethod() {
    io.grpc.MethodDescriptor<pumpservice.Pumpservice.PumpAuthRequest, pumpservice.Pumpservice.PumpAuthResponse> getAuthenticatePumpMethod;
    if ((getAuthenticatePumpMethod = PumpServiceGrpc.getAuthenticatePumpMethod) == null) {
      synchronized (PumpServiceGrpc.class) {
        if ((getAuthenticatePumpMethod = PumpServiceGrpc.getAuthenticatePumpMethod) == null) {
          PumpServiceGrpc.getAuthenticatePumpMethod = getAuthenticatePumpMethod =
              io.grpc.MethodDescriptor.<pumpservice.Pumpservice.PumpAuthRequest, pumpservice.Pumpservice.PumpAuthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AuthenticatePump"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pumpservice.Pumpservice.PumpAuthRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pumpservice.Pumpservice.PumpAuthResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PumpServiceMethodDescriptorSupplier("AuthenticatePump"))
              .build();
        }
      }
    }
    return getAuthenticatePumpMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pumpservice.Pumpservice.FuelRequest,
      pumpservice.Pumpservice.FuelResponse> getRequestFuelMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RequestFuel",
      requestType = pumpservice.Pumpservice.FuelRequest.class,
      responseType = pumpservice.Pumpservice.FuelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pumpservice.Pumpservice.FuelRequest,
      pumpservice.Pumpservice.FuelResponse> getRequestFuelMethod() {
    io.grpc.MethodDescriptor<pumpservice.Pumpservice.FuelRequest, pumpservice.Pumpservice.FuelResponse> getRequestFuelMethod;
    if ((getRequestFuelMethod = PumpServiceGrpc.getRequestFuelMethod) == null) {
      synchronized (PumpServiceGrpc.class) {
        if ((getRequestFuelMethod = PumpServiceGrpc.getRequestFuelMethod) == null) {
          PumpServiceGrpc.getRequestFuelMethod = getRequestFuelMethod =
              io.grpc.MethodDescriptor.<pumpservice.Pumpservice.FuelRequest, pumpservice.Pumpservice.FuelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RequestFuel"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pumpservice.Pumpservice.FuelRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pumpservice.Pumpservice.FuelResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PumpServiceMethodDescriptorSupplier("RequestFuel"))
              .build();
        }
      }
    }
    return getRequestFuelMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PumpServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PumpServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PumpServiceStub>() {
        @java.lang.Override
        public PumpServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PumpServiceStub(channel, callOptions);
        }
      };
    return PumpServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PumpServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PumpServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PumpServiceBlockingStub>() {
        @java.lang.Override
        public PumpServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PumpServiceBlockingStub(channel, callOptions);
        }
      };
    return PumpServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PumpServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PumpServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PumpServiceFutureStub>() {
        @java.lang.Override
        public PumpServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PumpServiceFutureStub(channel, callOptions);
        }
      };
    return PumpServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PumpServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void authenticatePump(pumpservice.Pumpservice.PumpAuthRequest request,
        io.grpc.stub.StreamObserver<pumpservice.Pumpservice.PumpAuthResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuthenticatePumpMethod(), responseObserver);
    }

    /**
     */
    public void requestFuel(pumpservice.Pumpservice.FuelRequest request,
        io.grpc.stub.StreamObserver<pumpservice.Pumpservice.FuelResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRequestFuelMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAuthenticatePumpMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pumpservice.Pumpservice.PumpAuthRequest,
                pumpservice.Pumpservice.PumpAuthResponse>(
                  this, METHODID_AUTHENTICATE_PUMP)))
          .addMethod(
            getRequestFuelMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pumpservice.Pumpservice.FuelRequest,
                pumpservice.Pumpservice.FuelResponse>(
                  this, METHODID_REQUEST_FUEL)))
          .build();
    }
  }

  /**
   */
  public static final class PumpServiceStub extends io.grpc.stub.AbstractAsyncStub<PumpServiceStub> {
    private PumpServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PumpServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PumpServiceStub(channel, callOptions);
    }

    /**
     */
    public void authenticatePump(pumpservice.Pumpservice.PumpAuthRequest request,
        io.grpc.stub.StreamObserver<pumpservice.Pumpservice.PumpAuthResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuthenticatePumpMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void requestFuel(pumpservice.Pumpservice.FuelRequest request,
        io.grpc.stub.StreamObserver<pumpservice.Pumpservice.FuelResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRequestFuelMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PumpServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<PumpServiceBlockingStub> {
    private PumpServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PumpServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PumpServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public pumpservice.Pumpservice.PumpAuthResponse authenticatePump(pumpservice.Pumpservice.PumpAuthRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuthenticatePumpMethod(), getCallOptions(), request);
    }

    /**
     */
    public pumpservice.Pumpservice.FuelResponse requestFuel(pumpservice.Pumpservice.FuelRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRequestFuelMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PumpServiceFutureStub extends io.grpc.stub.AbstractFutureStub<PumpServiceFutureStub> {
    private PumpServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PumpServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PumpServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pumpservice.Pumpservice.PumpAuthResponse> authenticatePump(
        pumpservice.Pumpservice.PumpAuthRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuthenticatePumpMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pumpservice.Pumpservice.FuelResponse> requestFuel(
        pumpservice.Pumpservice.FuelRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRequestFuelMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AUTHENTICATE_PUMP = 0;
  private static final int METHODID_REQUEST_FUEL = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PumpServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PumpServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AUTHENTICATE_PUMP:
          serviceImpl.authenticatePump((pumpservice.Pumpservice.PumpAuthRequest) request,
              (io.grpc.stub.StreamObserver<pumpservice.Pumpservice.PumpAuthResponse>) responseObserver);
          break;
        case METHODID_REQUEST_FUEL:
          serviceImpl.requestFuel((pumpservice.Pumpservice.FuelRequest) request,
              (io.grpc.stub.StreamObserver<pumpservice.Pumpservice.FuelResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PumpServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PumpServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pumpservice.Pumpservice.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PumpService");
    }
  }

  private static final class PumpServiceFileDescriptorSupplier
      extends PumpServiceBaseDescriptorSupplier {
    PumpServiceFileDescriptorSupplier() {}
  }

  private static final class PumpServiceMethodDescriptorSupplier
      extends PumpServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PumpServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PumpServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PumpServiceFileDescriptorSupplier())
              .addMethod(getAuthenticatePumpMethod())
              .addMethod(getRequestFuelMethod())
              .build();
        }
      }
    }
    return result;
  }
}

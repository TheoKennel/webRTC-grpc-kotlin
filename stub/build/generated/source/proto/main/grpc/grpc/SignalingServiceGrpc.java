package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc/signaling.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SignalingServiceGrpc {

  private SignalingServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "grpc.SignalingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.Client,
      com.google.protobuf.Empty> getRegisterClientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterClient",
      requestType = grpc.Client.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.Client,
      com.google.protobuf.Empty> getRegisterClientMethod() {
    io.grpc.MethodDescriptor<grpc.Client, com.google.protobuf.Empty> getRegisterClientMethod;
    if ((getRegisterClientMethod = SignalingServiceGrpc.getRegisterClientMethod) == null) {
      synchronized (SignalingServiceGrpc.class) {
        if ((getRegisterClientMethod = SignalingServiceGrpc.getRegisterClientMethod) == null) {
          SignalingServiceGrpc.getRegisterClientMethod = getRegisterClientMethod =
              io.grpc.MethodDescriptor.<grpc.Client, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterClient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  grpc.Client.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .build();
        }
      }
    }
    return getRegisterClientMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      grpc.ClientList> getGetConnectedClientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConnectedClient",
      requestType = com.google.protobuf.Empty.class,
      responseType = grpc.ClientList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      grpc.ClientList> getGetConnectedClientMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, grpc.ClientList> getGetConnectedClientMethod;
    if ((getGetConnectedClientMethod = SignalingServiceGrpc.getGetConnectedClientMethod) == null) {
      synchronized (SignalingServiceGrpc.class) {
        if ((getGetConnectedClientMethod = SignalingServiceGrpc.getGetConnectedClientMethod) == null) {
          SignalingServiceGrpc.getGetConnectedClientMethod = getGetConnectedClientMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, grpc.ClientList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetConnectedClient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  grpc.ClientList.getDefaultInstance()))
              .build();
        }
      }
    }
    return getGetConnectedClientMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.SignalingMessage,
      grpc.SignalingMessage> getForwardMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ForwardMessage",
      requestType = grpc.SignalingMessage.class,
      responseType = grpc.SignalingMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.SignalingMessage,
      grpc.SignalingMessage> getForwardMessageMethod() {
    io.grpc.MethodDescriptor<grpc.SignalingMessage, grpc.SignalingMessage> getForwardMessageMethod;
    if ((getForwardMessageMethod = SignalingServiceGrpc.getForwardMessageMethod) == null) {
      synchronized (SignalingServiceGrpc.class) {
        if ((getForwardMessageMethod = SignalingServiceGrpc.getForwardMessageMethod) == null) {
          SignalingServiceGrpc.getForwardMessageMethod = getForwardMessageMethod =
              io.grpc.MethodDescriptor.<grpc.SignalingMessage, grpc.SignalingMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ForwardMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  grpc.SignalingMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  grpc.SignalingMessage.getDefaultInstance()))
              .build();
        }
      }
    }
    return getForwardMessageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SignalingServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SignalingServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SignalingServiceStub>() {
        @java.lang.Override
        public SignalingServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SignalingServiceStub(channel, callOptions);
        }
      };
    return SignalingServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SignalingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SignalingServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SignalingServiceBlockingStub>() {
        @java.lang.Override
        public SignalingServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SignalingServiceBlockingStub(channel, callOptions);
        }
      };
    return SignalingServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SignalingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SignalingServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SignalingServiceFutureStub>() {
        @java.lang.Override
        public SignalingServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SignalingServiceFutureStub(channel, callOptions);
        }
      };
    return SignalingServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void registerClient(grpc.Client request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterClientMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<com.google.protobuf.Empty> getConnectedClient(
        io.grpc.stub.StreamObserver<grpc.ClientList> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getGetConnectedClientMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<grpc.SignalingMessage> forwardMessage(
        io.grpc.stub.StreamObserver<grpc.SignalingMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getForwardMessageMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SignalingService.
   */
  public static abstract class SignalingServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SignalingServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SignalingService.
   */
  public static final class SignalingServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SignalingServiceStub> {
    private SignalingServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SignalingServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SignalingServiceStub(channel, callOptions);
    }

    /**
     */
    public void registerClient(grpc.Client request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterClientMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.google.protobuf.Empty> getConnectedClient(
        io.grpc.stub.StreamObserver<grpc.ClientList> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getGetConnectedClientMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<grpc.SignalingMessage> forwardMessage(
        io.grpc.stub.StreamObserver<grpc.SignalingMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getForwardMessageMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SignalingService.
   */
  public static final class SignalingServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SignalingServiceBlockingStub> {
    private SignalingServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SignalingServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SignalingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty registerClient(grpc.Client request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterClientMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SignalingService.
   */
  public static final class SignalingServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SignalingServiceFutureStub> {
    private SignalingServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SignalingServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SignalingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> registerClient(
        grpc.Client request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterClientMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_CLIENT = 0;
  private static final int METHODID_GET_CONNECTED_CLIENT = 1;
  private static final int METHODID_FORWARD_MESSAGE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_CLIENT:
          serviceImpl.registerClient((grpc.Client) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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
        case METHODID_GET_CONNECTED_CLIENT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getConnectedClient(
              (io.grpc.stub.StreamObserver<grpc.ClientList>) responseObserver);
        case METHODID_FORWARD_MESSAGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.forwardMessage(
              (io.grpc.stub.StreamObserver<grpc.SignalingMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRegisterClientMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.Client,
              com.google.protobuf.Empty>(
                service, METHODID_REGISTER_CLIENT)))
        .addMethod(
          getGetConnectedClientMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              grpc.ClientList>(
                service, METHODID_GET_CONNECTED_CLIENT)))
        .addMethod(
          getForwardMessageMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              grpc.SignalingMessage,
              grpc.SignalingMessage>(
                service, METHODID_FORWARD_MESSAGE)))
        .build();
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SignalingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getRegisterClientMethod())
              .addMethod(getGetConnectedClientMethod())
              .addMethod(getForwardMessageMethod())
              .build();
        }
      }
    }
    return result;
  }
}

package grpc

import com.google.protobuf.Empty
import grpc.SignalingServiceGrpc.getServiceDescriptor
import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.ServiceDescriptor
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls.bidiStreamingRpc
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls.bidiStreamingServerMethodDefinition
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.String
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlinx.coroutines.flow.Flow

/**
 * Holder for Kotlin coroutine-based client and server APIs for grpc.SignalingService.
 */
public object SignalingServiceGrpcKt {
  public const val SERVICE_NAME: String = SignalingServiceGrpc.SERVICE_NAME

  @JvmStatic
  public val serviceDescriptor: ServiceDescriptor
    get() = getServiceDescriptor()

  public val registerClientMethod: MethodDescriptor<Client, Empty>
    @JvmStatic
    get() = SignalingServiceGrpc.getRegisterClientMethod()

  public val getConnectedClientMethod: MethodDescriptor<Empty, ClientList>
    @JvmStatic
    get() = SignalingServiceGrpc.getGetConnectedClientMethod()

  public val forwardMessageMethod: MethodDescriptor<SignalingMessage, SignalingMessage>
    @JvmStatic
    get() = SignalingServiceGrpc.getForwardMessageMethod()

  /**
   * A stub for issuing RPCs to a(n) grpc.SignalingService service as suspending coroutines.
   */
  @StubFor(SignalingServiceGrpc::class)
  public class SignalingServiceCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT,
  ) : AbstractCoroutineStub<SignalingServiceCoroutineStub>(channel, callOptions) {
    override fun build(channel: Channel, callOptions: CallOptions): SignalingServiceCoroutineStub =
        SignalingServiceCoroutineStub(channel, callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun registerClient(request: Client, headers: Metadata = Metadata()): Empty =
        unaryRpc(
      channel,
      SignalingServiceGrpc.getRegisterClientMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Returns a [Flow] that, when collected, executes this RPC and emits responses from the
     * server as they arrive.  That flow finishes normally if the server closes its response with
     * [`Status.OK`][io.grpc.Status], and fails by throwing a [StatusException] otherwise.  If
     * collecting the flow downstream fails exceptionally (including via cancellation), the RPC
     * is cancelled with that exception as a cause.
     *
     * The [Flow] of requests is collected once each time the [Flow] of responses is
     * collected. If collection of the [Flow] of responses completes normally or
     * exceptionally before collection of `requests` completes, the collection of
     * `requests` is cancelled.  If the collection of `requests` completes
     * exceptionally for any other reason, then the collection of the [Flow] of responses
     * completes exceptionally for the same reason and the RPC is cancelled with that reason.
     *
     * @param requests A [Flow] of request messages.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return A flow that, when collected, emits the responses from the server.
     */
    public fun getConnectedClient(requests: Flow<Empty>, headers: Metadata = Metadata()):
        Flow<ClientList> = bidiStreamingRpc(
      channel,
      SignalingServiceGrpc.getGetConnectedClientMethod(),
      requests,
      callOptions,
      headers
    )

    /**
     * Returns a [Flow] that, when collected, executes this RPC and emits responses from the
     * server as they arrive.  That flow finishes normally if the server closes its response with
     * [`Status.OK`][io.grpc.Status], and fails by throwing a [StatusException] otherwise.  If
     * collecting the flow downstream fails exceptionally (including via cancellation), the RPC
     * is cancelled with that exception as a cause.
     *
     * The [Flow] of requests is collected once each time the [Flow] of responses is
     * collected. If collection of the [Flow] of responses completes normally or
     * exceptionally before collection of `requests` completes, the collection of
     * `requests` is cancelled.  If the collection of `requests` completes
     * exceptionally for any other reason, then the collection of the [Flow] of responses
     * completes exceptionally for the same reason and the RPC is cancelled with that reason.
     *
     * @param requests A [Flow] of request messages.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return A flow that, when collected, emits the responses from the server.
     */
    public fun forwardMessage(requests: Flow<SignalingMessage>, headers: Metadata = Metadata()):
        Flow<SignalingMessage> = bidiStreamingRpc(
      channel,
      SignalingServiceGrpc.getForwardMessageMethod(),
      requests,
      callOptions,
      headers
    )
  }

  /**
   * Skeletal implementation of the grpc.SignalingService service based on Kotlin coroutines.
   */
  public abstract class SignalingServiceCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for grpc.SignalingService.RegisterClient.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun registerClient(request: Client): Empty = throw
        StatusException(UNIMPLEMENTED.withDescription("Method grpc.SignalingService.RegisterClient is unimplemented"))

    /**
     * Returns a [Flow] of responses to an RPC for grpc.SignalingService.GetConnectedClient.
     *
     * If creating or collecting the returned flow fails with a [StatusException], the RPC
     * will fail with the corresponding [io.grpc.Status].  If it fails with a
     * [java.util.concurrent.CancellationException], the RPC will fail with status
     * `Status.CANCELLED`.  If creating
     * or collecting the returned flow fails for any other reason, the RPC will fail with
     * `Status.UNKNOWN` with the exception as a cause.
     *
     * @param requests A [Flow] of requests from the client.  This flow can be
     *        collected only once and throws [java.lang.IllegalStateException] on attempts to
     * collect
     *        it more than once.
     */
    public open fun getConnectedClient(requests: Flow<Empty>): Flow<ClientList> = throw
        StatusException(UNIMPLEMENTED.withDescription("Method grpc.SignalingService.GetConnectedClient is unimplemented"))

    /**
     * Returns a [Flow] of responses to an RPC for grpc.SignalingService.ForwardMessage.
     *
     * If creating or collecting the returned flow fails with a [StatusException], the RPC
     * will fail with the corresponding [io.grpc.Status].  If it fails with a
     * [java.util.concurrent.CancellationException], the RPC will fail with status
     * `Status.CANCELLED`.  If creating
     * or collecting the returned flow fails for any other reason, the RPC will fail with
     * `Status.UNKNOWN` with the exception as a cause.
     *
     * @param requests A [Flow] of requests from the client.  This flow can be
     *        collected only once and throws [java.lang.IllegalStateException] on attempts to
     * collect
     *        it more than once.
     */
    public open fun forwardMessage(requests: Flow<SignalingMessage>): Flow<SignalingMessage> = throw
        StatusException(UNIMPLEMENTED.withDescription("Method grpc.SignalingService.ForwardMessage is unimplemented"))

    final override fun bindService(): ServerServiceDefinition = builder(getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = SignalingServiceGrpc.getRegisterClientMethod(),
      implementation = ::registerClient
    ))
      .addMethod(bidiStreamingServerMethodDefinition(
      context = this.context,
      descriptor = SignalingServiceGrpc.getGetConnectedClientMethod(),
      implementation = ::getConnectedClient
    ))
      .addMethod(bidiStreamingServerMethodDefinition(
      context = this.context,
      descriptor = SignalingServiceGrpc.getForwardMessageMethod(),
      implementation = ::forwardMessage
    )).build()
  }
}

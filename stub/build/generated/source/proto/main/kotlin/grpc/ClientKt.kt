// Generated by the protocol buffer compiler. DO NOT EDIT!
// source: grpc/signaling.proto

// Generated files should ignore deprecation warnings
@file:Suppress("DEPRECATION")
package grpc;

@kotlin.jvm.JvmName("-initializeclient")
public inline fun client(block: grpc.ClientKt.Dsl.() -> kotlin.Unit): grpc.Client =
  grpc.ClientKt.Dsl._create(grpc.Client.newBuilder()).apply { block() }._build()
/**
 * Protobuf type `grpc.Client`
 */
public object ClientKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: grpc.Client.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: grpc.Client.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): grpc.Client = _builder.build()

    /**
     * `string serial = 1;`
     */
    public var serial: kotlin.String
      @JvmName("getSerial")
      get() = _builder.getSerial()
      @JvmName("setSerial")
      set(value) {
        _builder.setSerial(value)
      }
    /**
     * `string serial = 1;`
     */
    public fun clearSerial() {
      _builder.clearSerial()
    }

    /**
     * `string app = 2;`
     */
    public var app: kotlin.String
      @JvmName("getApp")
      get() = _builder.getApp()
      @JvmName("setApp")
      set(value) {
        _builder.setApp(value)
      }
    /**
     * `string app = 2;`
     */
    public fun clearApp() {
      _builder.clearApp()
    }
  }
}
public inline fun grpc.Client.copy(block: `grpc`.ClientKt.Dsl.() -> kotlin.Unit): grpc.Client =
  `grpc`.ClientKt.Dsl._create(this.toBuilder()).apply { block() }._build()


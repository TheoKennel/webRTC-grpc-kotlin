// Generated by the protocol buffer compiler. DO NOT EDIT!
// source: grpc/signaling.proto

// Generated files should ignore deprecation warnings
@file:Suppress("DEPRECATION")
package grpc;

@kotlin.jvm.JvmName("-initializeclientList")
public inline fun clientList(block: grpc.ClientListKt.Dsl.() -> kotlin.Unit): grpc.ClientList =
  grpc.ClientListKt.Dsl._create(grpc.ClientList.newBuilder()).apply { block() }._build()
/**
 * Protobuf type `grpc.ClientList`
 */
public object ClientListKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: grpc.ClientList.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: grpc.ClientList.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): grpc.ClientList = _builder.build()

    /**
     * An uninstantiable, behaviorless type to represent the field in
     * generics.
     */
    @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
    public class ClientsProxy private constructor() : com.google.protobuf.kotlin.DslProxy()
    /**
     * `repeated .grpc.Client clients = 1;`
     */
     public val clients: com.google.protobuf.kotlin.DslList<grpc.Client, ClientsProxy>
      @kotlin.jvm.JvmSynthetic
      get() = com.google.protobuf.kotlin.DslList(
        _builder.getClientsList()
      )
    /**
     * `repeated .grpc.Client clients = 1;`
     * @param value The clients to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("addClients")
    public fun com.google.protobuf.kotlin.DslList<grpc.Client, ClientsProxy>.add(value: grpc.Client) {
      _builder.addClients(value)
    }
    /**
     * `repeated .grpc.Client clients = 1;`
     * @param value The clients to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("plusAssignClients")
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun com.google.protobuf.kotlin.DslList<grpc.Client, ClientsProxy>.plusAssign(value: grpc.Client) {
      add(value)
    }
    /**
     * `repeated .grpc.Client clients = 1;`
     * @param values The clients to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("addAllClients")
    public fun com.google.protobuf.kotlin.DslList<grpc.Client, ClientsProxy>.addAll(values: kotlin.collections.Iterable<grpc.Client>) {
      _builder.addAllClients(values)
    }
    /**
     * `repeated .grpc.Client clients = 1;`
     * @param values The clients to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("plusAssignAllClients")
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun com.google.protobuf.kotlin.DslList<grpc.Client, ClientsProxy>.plusAssign(values: kotlin.collections.Iterable<grpc.Client>) {
      addAll(values)
    }
    /**
     * `repeated .grpc.Client clients = 1;`
     * @param index The index to set the value at.
     * @param value The clients to set.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("setClients")
    public operator fun com.google.protobuf.kotlin.DslList<grpc.Client, ClientsProxy>.set(index: kotlin.Int, value: grpc.Client) {
      _builder.setClients(index, value)
    }
    /**
     * `repeated .grpc.Client clients = 1;`
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("clearClients")
    public fun com.google.protobuf.kotlin.DslList<grpc.Client, ClientsProxy>.clear() {
      _builder.clearClients()
    }
  }
}
public inline fun grpc.ClientList.copy(block: `grpc`.ClientListKt.Dsl.() -> kotlin.Unit): grpc.ClientList =
  `grpc`.ClientListKt.Dsl._create(this.toBuilder()).apply { block() }._build()


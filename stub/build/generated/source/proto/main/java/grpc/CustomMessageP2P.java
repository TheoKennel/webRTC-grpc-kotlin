// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: grpc/messageP2p.proto

// Protobuf Java Version: 3.25.3
package grpc;

/**
 * Protobuf type {@code grpc.CustomMessageP2P}
 */
public  final class CustomMessageP2P extends
    com.google.protobuf.GeneratedMessageLite<
        CustomMessageP2P, CustomMessageP2P.Builder> implements
    // @@protoc_insertion_point(message_implements:grpc.CustomMessageP2P)
    CustomMessageP2POrBuilder {
  private CustomMessageP2P() {
    message_ = "";
  }
  public static final int SIGNALINGCOMMAND_FIELD_NUMBER = 1;
  private int signalingCommand_;
  /**
   * <code>.grpc.SignalingCommand signalingCommand = 1;</code>
   * @return The enum numeric value on the wire for signalingCommand.
   */
  @java.lang.Override
  public int getSignalingCommandValue() {
    return signalingCommand_;
  }
  /**
   * <code>.grpc.SignalingCommand signalingCommand = 1;</code>
   * @return The signalingCommand.
   */
  @java.lang.Override
  public grpc.SignalingCommand getSignalingCommand() {
    grpc.SignalingCommand result = grpc.SignalingCommand.forNumber(signalingCommand_);
    return result == null ? grpc.SignalingCommand.UNRECOGNIZED : result;
  }
  /**
   * <code>.grpc.SignalingCommand signalingCommand = 1;</code>
   * @param value The enum numeric value on the wire for signalingCommand to set.
   */
  private void setSignalingCommandValue(int value) {
      signalingCommand_ = value;
  }
  /**
   * <code>.grpc.SignalingCommand signalingCommand = 1;</code>
   * @param value The signalingCommand to set.
   */
  private void setSignalingCommand(grpc.SignalingCommand value) {
    signalingCommand_ = value.getNumber();

  }
  /**
   * <code>.grpc.SignalingCommand signalingCommand = 1;</code>
   */
  private void clearSignalingCommand() {

    signalingCommand_ = 0;
  }

  public static final int MESSAGE_FIELD_NUMBER = 2;
  private java.lang.String message_;
  /**
   * <code>string message = 2;</code>
   * @return The message.
   */
  @java.lang.Override
  public java.lang.String getMessage() {
    return message_;
  }
  /**
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getMessageBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(message_);
  }
  /**
   * <code>string message = 2;</code>
   * @param value The message to set.
   */
  private void setMessage(
      java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
  
    message_ = value;
  }
  /**
   * <code>string message = 2;</code>
   */
  private void clearMessage() {

    message_ = getDefaultInstance().getMessage();
  }
  /**
   * <code>string message = 2;</code>
   * @param value The bytes for message to set.
   */
  private void setMessageBytes(
      com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    message_ = value.toStringUtf8();

  }

  public static grpc.CustomMessageP2P parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static grpc.CustomMessageP2P parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static grpc.CustomMessageP2P parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static grpc.CustomMessageP2P parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static grpc.CustomMessageP2P parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static grpc.CustomMessageP2P parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static grpc.CustomMessageP2P parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static grpc.CustomMessageP2P parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static grpc.CustomMessageP2P parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static grpc.CustomMessageP2P parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static grpc.CustomMessageP2P parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static grpc.CustomMessageP2P parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }
  public static Builder newBuilder(grpc.CustomMessageP2P prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   * Protobuf type {@code grpc.CustomMessageP2P}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        grpc.CustomMessageP2P, Builder> implements
      // @@protoc_insertion_point(builder_implements:grpc.CustomMessageP2P)
      grpc.CustomMessageP2POrBuilder {
    // Construct using grpc.CustomMessageP2P.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <code>.grpc.SignalingCommand signalingCommand = 1;</code>
     * @return The enum numeric value on the wire for signalingCommand.
     */
    @java.lang.Override
    public int getSignalingCommandValue() {
      return instance.getSignalingCommandValue();
    }
    /**
     * <code>.grpc.SignalingCommand signalingCommand = 1;</code>
     * @param value The signalingCommand to set.
     * @return This builder for chaining.
     */
    public Builder setSignalingCommandValue(int value) {
      copyOnWrite();
      instance.setSignalingCommandValue(value);
      return this;
    }
    /**
     * <code>.grpc.SignalingCommand signalingCommand = 1;</code>
     * @return The signalingCommand.
     */
    @java.lang.Override
    public grpc.SignalingCommand getSignalingCommand() {
      return instance.getSignalingCommand();
    }
    /**
     * <code>.grpc.SignalingCommand signalingCommand = 1;</code>
     * @param value The enum numeric value on the wire for signalingCommand to set.
     * @return This builder for chaining.
     */
    public Builder setSignalingCommand(grpc.SignalingCommand value) {
      copyOnWrite();
      instance.setSignalingCommand(value);
      return this;
    }
    /**
     * <code>.grpc.SignalingCommand signalingCommand = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearSignalingCommand() {
      copyOnWrite();
      instance.clearSignalingCommand();
      return this;
    }

    /**
     * <code>string message = 2;</code>
     * @return The message.
     */
    @java.lang.Override
    public java.lang.String getMessage() {
      return instance.getMessage();
    }
    /**
     * <code>string message = 2;</code>
     * @return The bytes for message.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getMessageBytes() {
      return instance.getMessageBytes();
    }
    /**
     * <code>string message = 2;</code>
     * @param value The message to set.
     * @return This builder for chaining.
     */
    public Builder setMessage(
        java.lang.String value) {
      copyOnWrite();
      instance.setMessage(value);
      return this;
    }
    /**
     * <code>string message = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearMessage() {
      copyOnWrite();
      instance.clearMessage();
      return this;
    }
    /**
     * <code>string message = 2;</code>
     * @param value The bytes for message to set.
     * @return This builder for chaining.
     */
    public Builder setMessageBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setMessageBytes(value);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:grpc.CustomMessageP2P)
  }
  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0, java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new grpc.CustomMessageP2P();
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case BUILD_MESSAGE_INFO: {
          java.lang.Object[] objects = new java.lang.Object[] {
            "signalingCommand_",
            "message_",
          };
          java.lang.String info =
              "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\f\u0002\u0208" +
              "";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
      }
      // fall through
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        com.google.protobuf.Parser<grpc.CustomMessageP2P> parser = PARSER;
        if (parser == null) {
          synchronized (grpc.CustomMessageP2P.class) {
            parser = PARSER;
            if (parser == null) {
              parser =
                  new DefaultInstanceBasedParser<grpc.CustomMessageP2P>(
                      DEFAULT_INSTANCE);
              PARSER = parser;
            }
          }
        }
        return parser;
    }
    case GET_MEMOIZED_IS_INITIALIZED: {
      return (byte) 1;
    }
    case SET_MEMOIZED_IS_INITIALIZED: {
      return null;
    }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:grpc.CustomMessageP2P)
  private static final grpc.CustomMessageP2P DEFAULT_INSTANCE;
  static {
    CustomMessageP2P defaultInstance = new CustomMessageP2P();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
      CustomMessageP2P.class, defaultInstance);
  }

  public static grpc.CustomMessageP2P getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<CustomMessageP2P> PARSER;

  public static com.google.protobuf.Parser<CustomMessageP2P> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}


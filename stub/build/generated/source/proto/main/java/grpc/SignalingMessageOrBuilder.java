// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: grpc/signaling.proto

// Protobuf Java Version: 3.25.3
package grpc;

public interface SignalingMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.SignalingMessage)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <code>string senderSerial = 1;</code>
   * @return The senderSerial.
   */
  java.lang.String getSenderSerial();
  /**
   * <code>string senderSerial = 1;</code>
   * @return The bytes for senderSerial.
   */
  com.google.protobuf.ByteString
      getSenderSerialBytes();

  /**
   * <code>string serialReceiver = 2;</code>
   * @return The serialReceiver.
   */
  java.lang.String getSerialReceiver();
  /**
   * <code>string serialReceiver = 2;</code>
   * @return The bytes for serialReceiver.
   */
  com.google.protobuf.ByteString
      getSerialReceiverBytes();

  /**
   * <code>.google.protobuf.Any payload = 3;</code>
   * @return Whether the payload field is set.
   */
  boolean hasPayload();
  /**
   * <code>.google.protobuf.Any payload = 3;</code>
   * @return The payload.
   */
  com.google.protobuf.Any getPayload();
}

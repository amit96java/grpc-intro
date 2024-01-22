// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bank-service.proto

package com.vinsguru.models;

public final class BankServiceOuterClass {
  private BankServiceOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_BalanceCheckRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_BalanceCheckRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Balance_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Balance_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_WithdrawRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_WithdrawRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Money_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Money_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_DepositRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DepositRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_WithdrawalError_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_WithdrawalError_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022bank-service.proto\"-\n\023BalanceCheckRequ" +
      "est\022\026\n\016account_number\030\001 \001(\005\"\031\n\007Balance\022\016" +
      "\n\006amount\030\001 \001(\005\"9\n\017WithdrawRequest\022\026\n\016acc" +
      "ount_number\030\001 \001(\005\022\016\n\006amount\030\002 \001(\005\"\026\n\005Mon" +
      "ey\022\r\n\005value\030\001 \001(\005\"8\n\016DepositRequest\022\026\n\016a" +
      "ccount_number\030\001 \001(\005\022\016\n\006amount\030\002 \001(\005\"G\n\017W" +
      "ithdrawalError\022$\n\rerror_message\030\001 \001(\0162\r." +
      "ErrorMessage\022\016\n\006amount\030\002 \001(\005*@\n\014ErrorMes" +
      "sage\022\026\n\022ONLY_TEN_MULTIPLES\020\000\022\030\n\024INSUFFIC" +
      "IENT_BALANCE\020\0012\217\001\n\013BankService\022,\n\ngetBal" +
      "ance\022\024.BalanceCheckRequest\032\010.Balance\022&\n\010" +
      "withdraw\022\020.WithdrawRequest\032\006.Money0\001\022*\n\013" +
      "cashDeposit\022\017.DepositRequest\032\010.Balance(\001" +
      "B\027\n\023com.vinsguru.modelsP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_BalanceCheckRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_BalanceCheckRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_BalanceCheckRequest_descriptor,
        new java.lang.String[] { "AccountNumber", });
    internal_static_Balance_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_Balance_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Balance_descriptor,
        new java.lang.String[] { "Amount", });
    internal_static_WithdrawRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_WithdrawRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_WithdrawRequest_descriptor,
        new java.lang.String[] { "AccountNumber", "Amount", });
    internal_static_Money_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_Money_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Money_descriptor,
        new java.lang.String[] { "Value", });
    internal_static_DepositRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_DepositRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_DepositRequest_descriptor,
        new java.lang.String[] { "AccountNumber", "Amount", });
    internal_static_WithdrawalError_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_WithdrawalError_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_WithdrawalError_descriptor,
        new java.lang.String[] { "ErrorMessage", "Amount", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

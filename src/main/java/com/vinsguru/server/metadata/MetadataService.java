package com.vinsguru.server.metadata;

import com.vinsguru.models.*;
import com.vinsguru.server.rpctypes.streamingTypes.AccountDatabase;
import com.vinsguru.server.rpctypes.streamingTypes.CashStreamingRequest;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;

public class MetadataService extends BankServiceGrpc.BankServiceImplBase{
    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber = request.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(AccountDatabase.getBalance(accountNumber))
                .build();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }
//
//    @Override
//    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
//        int accountNumber = request.getAccountNumber();
//        int amount = request.getAmount();
//        int balance = AccountDatabase.getBalance(accountNumber);
//        if(balance < amount) {
//            Status status = Status.FAILED_PRECONDITION.withDescription("Not enough balance, only you have " + balance);
//            responseObserver.onError(status.asRuntimeException());
//            return;
//        }
//        // all the validations passed successfully
//        for (int i = 0; i < amount/10; i++) {
//            Money money = Money.newBuilder()
//                    .setValue(10).build();
//            //simulate time consuming call
//            Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
//            //below code ensure that client is still listening or not.
//            if(!Context.current().isCancelled()) {
//                responseObserver.onNext(money);
//                System.out.println("Delivered $10");
//                AccountDatabase.deductBalance(accountNumber, 10);
//            } else {
//                break;
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        responseObserver.onCompleted();
//    }



    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount();
        int balance = AccountDatabase.getBalance(accountNumber);
        if(amount < 10 || (amount%10) != 0) {
            Metadata metadata = new Metadata();
            Metadata.Key<WithdrawalError> errorKey = ProtoUtils.keyForProto(WithdrawalError.getDefaultInstance());
            WithdrawalError withdrawalError = WithdrawalError.newBuilder().setAmount(balance).setErrorMessage(ErrorMessage.ONLY_TEN_MULTIPLES).build();
            metadata.put(errorKey, withdrawalError);
            StatusRuntimeException exception = Status.FAILED_PRECONDITION.asRuntimeException(metadata);
            responseObserver.onError(exception);
            return;
        }
        if(balance < amount) {
            Metadata metadata = new Metadata();
            Metadata.Key<WithdrawalError> errorKey = ProtoUtils.keyForProto(WithdrawalError.getDefaultInstance());
            WithdrawalError withdrawalError = WithdrawalError.newBuilder().setAmount(balance).setErrorMessage(ErrorMessage.INSUFFICIENT_BALANCE).build();
            metadata.put(errorKey, withdrawalError);
            StatusRuntimeException exception = Status.FAILED_PRECONDITION.asRuntimeException(metadata);
            responseObserver.onError(exception);
            return;
        }
        // all the validations passed successfully
        for (int i = 0; i < amount/10; i++) {
            Money money = Money.newBuilder()
                    .setValue(10).build();
                responseObserver.onNext(money);
                System.out.println("Delivered $10");
                AccountDatabase.deductBalance(accountNumber, 10);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequest(responseObserver);
    }
}

package com.vinsguru.server.deadline;

import com.google.common.util.concurrent.Uninterruptibles;
import com.vinsguru.models.*;
import com.vinsguru.server.rpctypes.streamingTypes.AccountDatabase;
import com.vinsguru.server.rpctypes.streamingTypes.CashStreamingRequest;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class DeadlineService extends BankServiceGrpc.BankServiceImplBase{
    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber = request.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(AccountDatabase.getBalance(accountNumber))
                .build();
        //simulate time consuming call
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount();
        int balance = AccountDatabase.getBalance(accountNumber);
        if(balance < amount) {
            Status status = Status.FAILED_PRECONDITION.withDescription("Not enough balance, only you have " + balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }
        // all the validations passed successfully
        for (int i = 0; i < amount/10; i++) {
            Money money = Money.newBuilder()
                    .setValue(10).build();
            //simulate time consuming call
            Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
            //below code ensure that client is still listening or not.
            if(!Context.current().isCancelled()) {
                responseObserver.onNext(money);
                System.out.println("Delivered $10");
                AccountDatabase.deductBalance(accountNumber, 10);
            } else {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequest(responseObserver);
    }
}

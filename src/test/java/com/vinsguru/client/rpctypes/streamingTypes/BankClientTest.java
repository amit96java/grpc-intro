package com.vinsguru.client.rpctypes.streamingTypes;

import com.vinsguru.models.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

    //blocking stub
    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    //non blocking stub
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setUp() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        System.out.println("channel is created...");
        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);
        System.out.println("Stubs are created...");
    }

    // unary
    @Test
    void balanceTest() throws InterruptedException {
        BalanceCheckRequest checkRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(2)
                .build();

        Balance balance = this.blockingStub.getBalance(checkRequest);

        System.out.println("balance is " + balance.getAmount());
    }

    @Test
    void withdrawTest() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(40)
                .build();

        this.blockingStub.withdraw(withdrawRequest)
                .forEachRemaining(money -> System.out.println("Received: " + money.getValue()));

    }

    // server-side streaming
    @Test
    void withdrawAsyncTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(9)
                .setAmount(41)
                .build();

        this.bankServiceStub.withdraw(withdrawRequest, new MoneyStreamingResponse(countDownLatch));
        countDownLatch.await();
//        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

    }

    //client-side streaming
    @Test
    void cashStreamingRequest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        //this is the object which client will send data in stream.
        StreamObserver<DepositRequest> streamObserver = this.bankServiceStub.cashDeposit(new BalanceStreamObserver(latch));
        for (int i = 0; i < 10; i++) {
            DepositRequest depositRequest = DepositRequest.newBuilder().setAccountNumber(8).setAmount(10).build();
            streamObserver.onNext(depositRequest);
        }

        //client is done to send data.
        streamObserver.onCompleted();
        latch.await();

    }
}

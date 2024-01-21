package com.vinsguru.client;

import com.vinsguru.models.Balance;
import com.vinsguru.models.BalanceCheckRequest;
import com.vinsguru.models.BankServiceGrpc;
import com.vinsguru.models.WithdrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);
    }

    @Test
    void balanceTest() {
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

    @Test
    void withdrawAsyncTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(9)
                .setAmount(40)
                .build();

        this.bankServiceStub.withdraw(withdrawRequest, new MoneyStreamingResponse(countDownLatch));
        countDownLatch.await();
//        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

    }
}

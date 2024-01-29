package com.vinsguru.client.deadline;

import com.vinsguru.client.rpctypes.streamingTypes.MoneyStreamingResponse;
import com.vinsguru.models.Balance;
import com.vinsguru.models.BalanceCheckRequest;
import com.vinsguru.models.BankServiceGrpc;
import com.vinsguru.models.WithdrawRequest;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeadlineClientTest {

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

    @Test
    void balanceTest() {
        BalanceCheckRequest checkRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(7)
                .build();

        try {
            Balance balance = this.blockingStub
                    .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                    .getBalance(checkRequest);

            System.out.println("balance is " + balance.getAmount());
        } catch (StatusRuntimeException e) {
            // go with defaut values
        }

    }

    @Test
    void withdrawTest() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(50)
                .build();
        try {
            this.blockingStub
                    .withDeadline(Deadline.after(4, TimeUnit.SECONDS))
                    .withdraw(withdrawRequest)
                    .forEachRemaining(money -> System.out.println("Received: " + money.getValue()));
        } catch (StatusRuntimeException e) {
            //
        }


    }

    @Test
    void withdrawAsyncTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(9)
                .setAmount(40)
                .build();

        this.bankServiceStub
                .withDeadline(Deadline.after(4, TimeUnit.SECONDS))
                .withdraw(withdrawRequest, new MoneyStreamingResponse(countDownLatch));
        countDownLatch.await();
//        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

    }
}

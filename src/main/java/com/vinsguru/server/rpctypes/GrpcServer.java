package com.vinsguru.server.rpctypes;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Server starting...");
        Server server = ServerBuilder.forPort(6565)
                .addService(new BankService())
                .addService(new TransferService())
                .build();
        server.start();
        System.out.println("Server Started...");
        server.awaitTermination();
    }
}

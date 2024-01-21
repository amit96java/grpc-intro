package com.vinsguru.server.loadbalancing;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 7575;
        System.out.println("Server starting..."+port);
        Server server = ServerBuilder.forPort(port)
                .addService(new BankService())
                .build();
        server.start();
        System.out.println("Server Started..."+port);
        server.awaitTermination();
    }
}

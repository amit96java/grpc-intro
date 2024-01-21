package com.vinsguru.server.Sec03gRPCIntro;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Server starting...");
        Server server = ServerBuilder.forPort(6565)
                .addService(new BankService())
                .build();
        server.start();
        System.out.println("Server Started...");
        server.awaitTermination();
    }
}

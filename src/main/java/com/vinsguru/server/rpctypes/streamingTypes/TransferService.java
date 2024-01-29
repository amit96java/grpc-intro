package com.vinsguru.server.rpctypes.streamingTypes;

import com.vinsguru.models.TransferRequest;
import com.vinsguru.models.TransferResponse;
import com.vinsguru.models.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    // bi-directional streaming
    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferStreamingRequest(responseObserver);
    }
}

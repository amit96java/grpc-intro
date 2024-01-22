package com.vinsguru.client.metadata;

import io.grpc.CallCredentials;
import io.grpc.Metadata;

import java.util.concurrent.Executor;

import static com.vinsguru.client.metadata.ClientConstants.USER_TOKEN;

public class UserSessionToken extends CallCredentials {

    private String jwt ;

    public UserSessionToken(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(() -> {
            Metadata metadata = new Metadata();
            metadata.put(USER_TOKEN, this.jwt);
            metadataApplier.apply(metadata);
        });
    }
}

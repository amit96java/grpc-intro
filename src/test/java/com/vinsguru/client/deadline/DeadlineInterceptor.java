package com.vinsguru.client.deadline;

import io.grpc.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DeadlineInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        Deadline deadline = callOptions.getDeadline();
        //here we set if deadline configured explicitly than take that one otherwise take below one.
        if (Objects.isNull(deadline)) {
            callOptions = callOptions.withDeadline(Deadline.after(4, TimeUnit.SECONDS));
        }
        return channel.newCall(methodDescriptor, callOptions);
    }
}

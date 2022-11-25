package com.grpc.server.common.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.apache.logging.log4j.ThreadContext;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;

import java.util.UUID;

@GRpcGlobalInterceptor
public class CorrelationIdInterceptor implements ServerInterceptor {

    private static final String CORRELATION_ID_KEY = "X-Correlation-Id";

    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {

        final Metadata.Key<String> correlationId = Metadata.Key.of(CORRELATION_ID_KEY, Metadata.ASCII_STRING_MARSHALLER);
        String correlationIdValue = metadata.get(correlationId);

        if (correlationIdValue == null) {
            correlationIdValue = String.format("gRPC-S-%s", UUID.randomUUID().toString().replace("-", "").toLowerCase());
        }
        ThreadContext.put("correlationId", correlationIdValue);

        return serverCallHandler.startCall(serverCall, metadata);
    }

}

package com.grpc.server.common.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler;
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope;
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice;

@GRpcServiceAdvice
public class CustomExceptionHandler {

    /*@GRpcExceptionHandler
    public Status handle(InvalidRequestException exc, GRpcExceptionScope scope) {

        String description = scope.getHintAs(String.class)
                .orElse(exc.getStatus().getDesc());

        Status status = Status.INVALID_ARGUMENT
                .withDescription(description)
                .withCause(exc);

        Metadata.Key key = Metadata.Key.of("custom", Metadata.ASCII_STRING_MARSHALLER);
        scope.getResponseHeaders().put(key, description);

        return status;
    }*/

    /*@GRpcExceptionHandler
    public StatusRuntimeException handle(InvalidRequestException exc) {

        String description = exc.getStatus().getDesc();

        Status status = Status.INVALID_ARGUMENT
                .withDescription(description)
                .withCause(exc);

        Metadata.Key<String> key = Metadata.Key.of("error_description", Metadata.ASCII_STRING_MARSHALLER);
        Metadata metadata = new Metadata();

        metadata.put(key, description);

        return status.asRuntimeException(metadata);
    }*/
}

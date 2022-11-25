package com.grpc.server.common.exception;

import lombok.Getter;

import static com.grpc.server.common.StatusConstants.HttpConstants;

@Getter
public class InvalidRequestException extends RuntimeException {

    private final HttpConstants status;

    public InvalidRequestException(HttpConstants status) {
        super(status.getDesc(), null);
        this.status = status;
    }

}

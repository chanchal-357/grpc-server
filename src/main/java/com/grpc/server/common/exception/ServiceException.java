package com.grpc.server.common.exception;

import lombok.Getter;

import static com.grpc.server.common.StatusConstants.HttpConstants;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpConstants status;

    public ServiceException(HttpConstants status) {
        super(status.getDesc(), null);
        this.status = status;
    }

}

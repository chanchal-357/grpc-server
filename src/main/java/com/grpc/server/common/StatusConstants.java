package com.grpc.server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class StatusConstants {

    @Getter
    @AllArgsConstructor
    public enum HttpConstants {
        SUCCESS("0", "Success"),
        CELSIUS_IS_INVALID("GRS0001", "celsius is invalid"),
        CELSIUS_EXCEEDS_THE_LIMIT("GRS0002", "celsius exceeds the limit"),
        NUMBER_IS_INVALID("GRS0003", "number is invalid"),
        NUMBER_EXCEEDS_THE_LIMIT("GRS0004", "number exceeds the limit"),

        BAD_REQUEST("GRS9997", "Bad request"),
        UNPARSABLE_REQUEST("GRS9998", "Unparsable request"),
        INTERNAL_SERVER_ERROR("GRS9999", "Internal server error");

        private final String code;
        private final String desc;
    }

}

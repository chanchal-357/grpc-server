package com.grpc.server.common.constant;

import com.grpc.server.common.Status;
import io.grpc.Metadata;
import io.grpc.protobuf.ProtoUtils;
import lombok.experimental.UtilityClass;

import static com.grpc.server.common.StatusConstants.HttpConstants;

@UtilityClass
public class AppConstant {

    public static final Status SUCCESS_STATUS = Status.newBuilder()
            .setCode(HttpConstants.SUCCESS.getCode())
            .setMessage(HttpConstants.SUCCESS.getDesc())
            .build();

    public static Status getGenericStatus(HttpConstants httpConstants) {

        return Status.newBuilder()
                .setCode(httpConstants.getCode())
                .setMessage(httpConstants.getDesc())
                .build();
    }

    public static Metadata getErrorMetadata(HttpConstants httpConstants) {

        Status status = Status.newBuilder().setCode(httpConstants.getCode())
                .setMessage(httpConstants.getDesc())
                .build();

        Metadata.Key<Status> statusKey = ProtoUtils.keyForProto(Status.getDefaultInstance());

        Metadata metadata = new Metadata();
        metadata.put(statusKey, status);

        return metadata;
    }

}

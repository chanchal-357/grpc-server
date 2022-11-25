package com.grpc.server.service.temperature;

import com.ascendcorp.logger.log4j.ACNLogger;
import com.google.protobuf.Any;
import com.google.rpc.Code;
import com.grpc.server.common.constant.AppConstant;
import com.grpc.server.common.exception.InvalidRequestException;
import com.grpc.server.common.exception.ServiceException;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import static com.grpc.server.common.StatusConstants.HttpConstants;

@GRpcService
public class TemperatureGrpcServiceImpl extends TemperatureServiceGrpc.TemperatureServiceImplBase {

    private static final ACNLogger LOG = ACNLogger.create(TemperatureGrpcServiceImpl.class);

    @Override
    public void convertCelsiusToFahrenheit(TemperatureRequest request, StreamObserver<TemperatureResponse> responseObserver) {

        try {
            LOG.info("Start converting temperature with celsius: {}", request.getCelsius());
            if (request.getCelsius() < 1) {
                throw new InvalidRequestException(HttpConstants.CELSIUS_IS_INVALID);
            } else if (request.getCelsius() > 199) {
                throw new ServiceException(HttpConstants.CELSIUS_EXCEEDS_THE_LIMIT);
            }
            float fahrenheit = ((request.getCelsius() * 9) / 5) + 32;

            TemperatureData data = TemperatureData.newBuilder()
                    .setFahrenheit(fahrenheit)
                    .build();

            TemperatureResponse response = TemperatureResponse.newBuilder()
                    .setStatus(AppConstant.SUCCESS_STATUS)
                    .setData(data)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            LOG.info("Done converting temperature");
        } catch (InvalidRequestException e) {
            String desc = e.getStatus().getDesc();
            LOG.error("Failed converting temperature with bad request: {}", desc);

            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT.withDescription(desc)
                    .asRuntimeException(AppConstant.getErrorMetadata(e.getStatus())));

        } catch (ServiceException e) {
            String desc = e.getStatus().getDesc();
            LOG.error("Failed converting temperature with error: {}", desc);

            TemperatureResponse response = TemperatureResponse.newBuilder()
                    .setStatus(AppConstant.getGenericStatus(e.getStatus()))
                    .build();

            com.google.rpc.Status status = com.google.rpc.Status.newBuilder()
                    .setCode(Code.FAILED_PRECONDITION.getNumber())
                    .setMessage(desc)
                    .addDetails(Any.pack(response))
                    .build();

            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
        } catch (Exception e) {
            LOG.error("Failed converting temperature with error: {}, {}", e, e.getMessage());

            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT.withDescription(HttpConstants.INTERNAL_SERVER_ERROR.getDesc())
                    .asRuntimeException());
        }
    }

}

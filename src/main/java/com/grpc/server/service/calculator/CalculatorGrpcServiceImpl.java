package com.grpc.server.service.calculator;

import com.ascendcorp.logger.log4j.ACNLogger;
import com.grpc.server.common.Status;
import com.grpc.server.common.constant.AppConstant;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import static com.grpc.server.common.StatusConstants.HttpConstants;

@GRpcService
public class CalculatorGrpcServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    private static final ACNLogger LOG = ACNLogger.create(CalculatorGrpcServiceImpl.class);

    @Override
    public void calculateSquare(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {

        LOG.info("Start calculating square with number: {}", request.getNumber());
        CalculatorResponse.Builder responseBuilder = CalculatorResponse.newBuilder();

        if (request.getNumber() < 1) {
            Status status = AppConstant.getGenericStatus(HttpConstants.NUMBER_IS_INVALID);
            responseBuilder.setStatus(status);
            LOG.error("Failed calculating square with bad request: {}", status.getMessage());
        } else if (request.getNumber() > 150) {
            Status status = AppConstant.getGenericStatus(HttpConstants.NUMBER_EXCEEDS_THE_LIMIT);
            responseBuilder.setStatus(status);
            LOG.error("Failed calculating square with bad request: {}", status.getMessage());
        } else {
            int square = request.getNumber() * request.getNumber();

            SquareData data = SquareData.newBuilder()
                    .setResult(square)
                    .build();

            responseBuilder.setStatus(AppConstant.SUCCESS_STATUS)
                    .setData(data);
            LOG.info("Done calculating square");
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}

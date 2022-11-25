package com.grpc.server.service.hello;

import com.ascendcorp.logger.log4j.ACNLogger;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class HelloGrpcServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    private static final ACNLogger LOG = ACNLogger.create(HelloGrpcServiceImpl.class);

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        LOG.info("Start greeting with first_name: {}, last_name: {}", request.getFistName(), request.getLastName());

        String greeting = new StringBuffer()
                .append("Hello, ")
                .append(request.getFistName())
                .append(" ")
                .append(request.getLastName())
                .toString();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        LOG.info("Done greeting");
    }

}

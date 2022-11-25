package com.grpc.server.hello.service;

import com.grpc.server.service.hello.HelloGrpcServiceImpl;
import com.grpc.server.service.hello.HelloRequest;
import com.grpc.server.service.hello.HelloResponse;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class HelloGrpcServiceImplTest {

    @InjectMocks
    private HelloGrpcServiceImpl helloGrpcService;

    @Test
    void testHello() throws Exception {

        //given
        String firstName = "Ramesh";
        String lastName = "Yadav";

        HelloRequest request = HelloRequest.newBuilder()
                .setFistName(firstName)
                .setLastName(lastName)
                .build();

        StreamRecorder<HelloResponse> responseObserver = StreamRecorder.create();

        //when
        helloGrpcService.hello(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            Assertions.fail("The call did not terminate in time");
        }

        //then
        Assertions.assertNull(responseObserver.getError());

        List<HelloResponse> results = responseObserver.getValues();

        Assertions.assertEquals(1, results.size());

        HelloResponse response = results.get(0);

        Assertions.assertEquals(HelloResponse.newBuilder()
                .setGreeting("Hello, " + firstName + " " + lastName)
                .build(), response);

    }


}
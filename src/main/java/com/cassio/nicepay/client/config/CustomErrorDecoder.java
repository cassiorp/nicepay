package com.cassio.nicepay.client.config;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.cassio.nicepay.exception.BusinessException;
import com.cassio.nicepay.exception.ForbiddenException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        if (responseStatus.equals(FORBIDDEN)) {
            return new ForbiddenException();
        } else {
            return new BusinessException();
        }
    }
}
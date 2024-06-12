package org.delivery.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE) //최우선 처리
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(
        ApiException apiException
    ) {
        log.error("", apiException);

        var errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity
            .status(errorCode.getHttpStatusCode())
            .body(
                Api.ERROR(errorCode, apiException.getErrorDescription())

            );

    }
}


/*
try{

}catch(Exception e){
throw new ApiException(ErrorCode.SERVER_ERROR, e, "에러 발생")
}

 */
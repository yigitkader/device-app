package com.device.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeviceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleDeviceNotFound(DeviceNotFound ex) {
        return new ErrorResponse(
                "DEVICE_NOT_FOUND",
                ex.getMessage()
        );
    }

    @ExceptionHandler(DeviceDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDeviceDeleteException(DeviceNotFound ex) {
        return new ErrorResponse(
                "DEVICE_CAN_NOT_DELETE",
                ex.getMessage()
        );
    }
}

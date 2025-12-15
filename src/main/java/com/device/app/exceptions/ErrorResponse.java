package com.device.app.exceptions;

public record ErrorResponse(
        String code,
        String message
) {}

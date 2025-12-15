package com.device.app.exceptions;

import java.util.Map;

public record ValidationErrorResponse(
        String code,
        Map<String, String> errors
) {
}
package com.device.app.dto;

import com.device.app.enums.StateType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateDeviceRequest(
        @NotNull(message = "Id cannot be null")
        Long id,
        String name,
        String brand,
        StateType state
) {
}

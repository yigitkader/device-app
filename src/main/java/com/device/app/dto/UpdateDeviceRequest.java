package com.device.app.dto;

import com.device.app.enums.StateType;
import jakarta.validation.constraints.NotBlank;

public record UpdateDeviceRequest(
        @NotBlank(message = "Id cannot be blank")
        Long id,
        String name,
        String brand,
        StateType state
) {
}

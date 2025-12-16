package com.device.app.dto;

import com.device.app.enums.StateType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDeviceRequest(
        @NotBlank(message = "Device name cannot be blank")
        String name,

        @NotBlank(message = "Brand name cannot be blank")
        String brand,

        @NotNull(message = "State type cannot be null")
        StateType state
) {
}

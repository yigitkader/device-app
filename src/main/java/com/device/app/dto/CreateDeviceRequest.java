package com.device.app.dto;

import com.device.app.enums.StateType;
import jakarta.validation.constraints.NotBlank;

public record CreateDeviceRequest(
        @NotBlank(message = "Device name cannot be blank")
        String name,

        @NotBlank(message = "Brand name cannot be blank")
        String brand,

        @NotBlank(message = "State type cannot be blank")
        StateType state
) {
}

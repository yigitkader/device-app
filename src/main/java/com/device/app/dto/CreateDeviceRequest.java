package com.device.app.dto;

import com.device.app.enums.StateType;

public record CreateDeviceRequest(
        String name,
        String brand,
        StateType state
) {
}

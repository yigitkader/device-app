package com.device.app.dto;

import com.device.app.enums.StateType;

public record UpdateDeviceRequest(
        Long id,
        String name,
        String brand,
        StateType state
) {
}

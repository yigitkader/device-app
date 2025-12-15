package com.device.app.dto;

import java.time.Instant;

public record DeviceResponseDto(
        Long id,
        String name,
        String brand,
        String state,
        Instant creationTime
) {}

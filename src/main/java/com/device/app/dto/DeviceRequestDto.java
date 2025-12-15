package com.device.app.dto;

public record DeviceRequestDto(
        Long id,
        String name,
        String brand,
        String state
) {}

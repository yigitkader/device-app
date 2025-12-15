package com.device.app.exceptions;

public class DeviceNotFound extends RuntimeException {
    public DeviceNotFound(Long id) {
        super("Device not found with ID: " + id);
    }
}

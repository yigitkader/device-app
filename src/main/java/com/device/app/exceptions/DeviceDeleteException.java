package com.device.app.exceptions;

public class DeviceDeleteException extends RuntimeException {
    public DeviceDeleteException(Long id) {
        super("Failed to delete device cause of device in use with ID: " + id);
    }
}

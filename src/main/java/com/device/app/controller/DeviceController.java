package com.device.app.controller;

import com.device.app.domain.Device;
import com.device.app.dto.DeviceRequestDto;
import com.device.app.dto.DeviceResponseDto;
import com.device.app.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/v1")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/device/create")
    public DeviceResponseDto createDevice(DeviceRequestDto device) {
        deviceService.createDevice();
    }

    @PutMapping("/device/update")
    public Device updateDevice(DeviceRequestDto device) {
        deviceService.updateDevice(device);
    }

    @GetMapping("/device/{id}")
    public DeviceResponseDto fetchDeviceById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/devices")
    public List<DeviceResponseDto> fetchAllDevices() {
        return null;
    }

    @GetMapping("/devices/brand/{brand}")
    public List<DeviceResponseDto> fetchDevicesByBrand(@PathVariable String brand) {
        return null;
    }

    @GetMapping("/devices/state/{state}")
    public List<DeviceResponseDto> fetchDevicesByState(@PathVariable String state) {
        return null;
    }

    @DeleteMapping("/device/delete/{id}")
    public Boolean deleteDevice(@PathVariable Long id) {
        return false;
    }
}

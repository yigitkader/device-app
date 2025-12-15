package com.device.app.controller;

import com.device.app.dto.CreateDeviceRequest;
import com.device.app.dto.DeviceResponseDto;
import com.device.app.dto.UpdateDeviceRequest;
import com.device.app.enums.StateType;
import com.device.app.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/device/create")
    public DeviceResponseDto createDevice(@Valid @RequestBody CreateDeviceRequest device) {
        return deviceService.createDevice(device);
    }

    @PutMapping("/device/update")
    public DeviceResponseDto updateDevice(@RequestBody UpdateDeviceRequest device) {
        return deviceService.updateDevice(device);
    }

    @GetMapping("/device/{id}")
    public DeviceResponseDto fetchDeviceById(@PathVariable Long id) {
        return deviceService.fetchDeviceById(id);
    }

    @GetMapping("/devices")
    public List<DeviceResponseDto> fetchAllDevices() {
        return deviceService.fetchAllDevices();
    }

    @GetMapping("/devices/brand/{brand}")
    public List<DeviceResponseDto> fetchDevicesByBrand(@PathVariable String brand) {
        return deviceService.fetchDevicesByBrand(brand);
    }

    @GetMapping("/devices/state/{state}")
    public List<DeviceResponseDto> fetchDevicesByState(@PathVariable StateType state) {
        return deviceService.fetchDevicesByState(state);
    }

    @DeleteMapping("/device/delete/{id}")
    public void deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
    }
}

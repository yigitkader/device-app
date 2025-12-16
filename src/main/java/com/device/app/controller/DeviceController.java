package com.device.app.controller;

import com.device.app.dto.CreateDeviceRequest;
import com.device.app.dto.DeviceResponseDto;
import com.device.app.dto.UpdateDeviceRequest;
import com.device.app.enums.StateType;
import com.device.app.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Device Management", description = "APIs for managing devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/device/create")
    @Operation(summary = "Create a new device", description = "Creates a new device with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public DeviceResponseDto createDevice(@Valid @RequestBody CreateDeviceRequest device) {
        return deviceService.createDevice(device);
    }

    @PutMapping("/device/update")
    @Operation(summary = "Update a device", description = "Updates an existing device")
    public DeviceResponseDto updateDevice(@RequestBody UpdateDeviceRequest device) {
        return deviceService.updateDevice(device);
    }

    @GetMapping("/device/{id}")
    @Operation(summary = "Get device by ID", description = "Retrieves a device by its ID")
    public DeviceResponseDto fetchDeviceById(@PathVariable Long id) {
        return deviceService.fetchDeviceById(id);
    }

    @GetMapping("/devices")
    @Operation(summary = "Get all devices", description = "Retrieves all devices")
    public List<DeviceResponseDto> fetchAllDevices() {
        return deviceService.fetchAllDevices();
    }

    @GetMapping("/devices/brand/{brand}")
    @Operation(summary = "Get devices by brand", description = "Retrieves all devices of a specific brand")
    public List<DeviceResponseDto> fetchDevicesByBrand(@PathVariable String brand) {
        return deviceService.fetchDevicesByBrand(brand);
    }

    @GetMapping("/devices/state/{state}")
    @Operation(summary = "Get devices by state", description = "Retrieves all devices in a specific state")
    public List<DeviceResponseDto> fetchDevicesByState(@PathVariable StateType state) {
        return deviceService.fetchDevicesByState(state);
    }

    @DeleteMapping("/device/delete/{id}")
    @Operation(summary = "Delete a device", description = "Deletes a device by its ID")
    public void deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
    }
}

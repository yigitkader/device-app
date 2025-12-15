package com.device.app.service;

import com.device.app.domain.Device;
import com.device.app.dto.CreateDeviceRequest;
import com.device.app.dto.DeviceResponseDto;
import com.device.app.dto.UpdateDeviceRequest;
import com.device.app.enums.StateType;
import com.device.app.exceptions.DeviceDeleteException;
import com.device.app.exceptions.DeviceNotFound;
import com.device.app.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceResponseDto createDevice(CreateDeviceRequest dto) {
        final Device device = new Device(dto.name(), dto.brand(), dto.state());
        return this.deviceRepository.save(device).toDto();
    }

    public DeviceResponseDto updateDevice(UpdateDeviceRequest dto) {
        final Device device = this.deviceRepository.findDeviceById(dto.id())
                .orElseThrow(() -> new DeviceNotFound(dto.id()));

        if (device.getState().equals(StateType.IN_USE)) {
            return this.deviceRepository.save(new Device(device.getName(), device.getBrand(), dto.state())).toDto();
        }

        return this.deviceRepository.save(new Device(dto.name(), dto.brand(), dto.state())).toDto();
    }

    public DeviceResponseDto fetchDeviceById(Long id) {
        final Device device = this.deviceRepository.findDeviceById(id)
                .orElseThrow(() -> new DeviceNotFound(id));

        return device.toDto();
    }

    public List<DeviceResponseDto> fetchAllDevices() {
        return this.deviceRepository.findAll().stream().map(Device::toDto).toList();
    }

    public List<DeviceResponseDto> fetchDevicesByBrand(String brand) {
        return this.deviceRepository.findDevicesByBrand(brand).stream().map(Device::toDto).toList();
    }

    public List<DeviceResponseDto> fetchDevicesByState(StateType state) {
        return this.deviceRepository.findDevicesByState(state).stream().map(Device::toDto).toList();
    }

    public void deleteDevice(Long id) {
        final Device device = this.deviceRepository.findDeviceById(id).orElseThrow(() -> new DeviceNotFound(id));
        if (device.getState().equals(StateType.IN_USE)) {
            throw new DeviceDeleteException(id);
        }
        this.deviceRepository.deleteById(id);
    }
}

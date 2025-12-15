package com.device.app.service;

import com.device.app.domain.Device;
import com.device.app.dto.CreateDeviceRequest;
import com.device.app.dto.DeviceResponseDto;
import com.device.app.dto.UpdateDeviceRequest;
import com.device.app.enums.StateType;
import com.device.app.exceptions.DeviceDeleteException;
import com.device.app.exceptions.DeviceNotFound;
import com.device.app.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeviceService Unit Tests")
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    private Device testDevice;
    private CreateDeviceRequest createRequest;
    private UpdateDeviceRequest updateRequest;
    private Device deviceInUse;

    @BeforeEach
    void setUp() {
        testDevice = new Device("iPhone 15", "Apple", StateType.AVAILABLE);
        createRequest = new CreateDeviceRequest("iPhone 15", "Apple", StateType.AVAILABLE);
        updateRequest = new UpdateDeviceRequest(1L, "iPhone 15 Pro", "Apple", StateType.IN_USE);
        deviceInUse = new Device("iPhone 8", "Apple", StateType.AVAILABLE);
    }

    @Test
    @DisplayName("Should create device successfully")
    void shouldCreateDeviceSuccessfully() {
        // Given
        when(deviceRepository.save(any())).thenReturn(testDevice);

        // When
        DeviceResponseDto result = deviceService.createDevice(createRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("iPhone 15");
        assertThat(result.brand()).isEqualTo("Apple");
        assertThat(result.state()).isEqualTo(StateType.AVAILABLE);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    @DisplayName("Should update device successfully when not IN_USE")
    void shouldUpdateDeviceWhenNotInUse() {
        // Given
        Device existingDevice = new Device("iPhone 14", "Apple", StateType.AVAILABLE);
        Device updatedDevice = new Device("iPhone 15 Pro", "Apple", StateType.IN_USE);

        when(deviceRepository.findDeviceById(1L)).thenReturn(Optional.of(existingDevice));
        when(deviceRepository.save(any(Device.class))).thenReturn(updatedDevice);

        // When
        DeviceResponseDto result = deviceService.updateDevice(updateRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("iPhone 15 Pro");
        assertThat(result.state()).isEqualTo(StateType.IN_USE);
        verify(deviceRepository, times(1)).findDeviceById(1L);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }


    @Test
    @DisplayName("Should update only state when device is IN_USE")
    void shouldUpdateOnlyStateWhenDeviceInUse() {
        // Given
        Device existingDevice = new Device("iPhone 14", "Apple", StateType.IN_USE);
        Device updatedDevice = new Device("iPhone 14", "Apple", StateType.INACTIVE);

        when(deviceRepository.findDeviceById(1L)).thenReturn(Optional.of(existingDevice));
        when(deviceRepository.save(any(Device.class))).thenReturn(updatedDevice);

        UpdateDeviceRequest request = new UpdateDeviceRequest(
                1L,
                "iPhone 15 Pro",
                "Samsung",
                StateType.INACTIVE
        );

        // When
        DeviceResponseDto result = deviceService.updateDevice(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("iPhone 14");
        assertThat(result.brand()).isEqualTo("Apple");
        verify(deviceRepository, times(1)).findDeviceById(1L);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    @DisplayName("Should throw DeviceNotFound when updating non-existent device")
    void shouldThrowExceptionWhenUpdatingNonExistentDevice() {
        // Given
        when(deviceRepository.findDeviceById(999L)).thenReturn(Optional.empty());

        UpdateDeviceRequest request = new UpdateDeviceRequest(
                999L,
                "iPhone 15",
                "Apple",
                StateType.AVAILABLE
        );

        // When & Then
        assertThatThrownBy(() -> deviceService.updateDevice(request))
                .isInstanceOf(DeviceNotFound.class)
                .hasMessageContaining("Device not found with ID: 999");

        verify(deviceRepository, times(1)).findDeviceById(999L);
        verify(deviceRepository, never()).save(any(Device.class));
    }

    @Test
    @DisplayName("Should fetch device by id successfully")
    void shouldFetchDeviceByIdSuccessfully() {
        // Given
        when(deviceRepository.findDeviceById(1L)).thenReturn(Optional.of(testDevice));

        // When
        DeviceResponseDto result = deviceService.fetchDeviceById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("iPhone 15");
        assertThat(result.brand()).isEqualTo("Apple");
        verify(deviceRepository, times(1)).findDeviceById(1L);
    }

    @Test
    @DisplayName("Should throw DeviceNotFound when fetching non-existent device")
    void shouldThrowExceptionWhenFetchingNonExistentDevice() {
        // Given
        when(deviceRepository.findDeviceById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> deviceService.fetchDeviceById(999L))
                .isInstanceOf(DeviceNotFound.class)
                .hasMessageContaining("Device not found with ID: 999");

        verify(deviceRepository, times(1)).findDeviceById(999L);
    }

    @Test
    @DisplayName("Should fetch all devices successfully")
    void shouldFetchAllDevicesSuccessfully() {
        // Given
        Device device1 = new Device("iPhone 15", "Apple", StateType.AVAILABLE);
        Device device2 = new Device("Galaxy S24", "Samsung", StateType.IN_USE);
        List<Device> devices = Arrays.asList(device1, device2);

        when(deviceRepository.findAll()).thenReturn(devices);

        // When
        List<DeviceResponseDto> result = deviceService.fetchAllDevices();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).name()).isEqualTo("iPhone 15");
        assertThat(result.get(1).name()).isEqualTo("Galaxy S24");
        verify(deviceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no devices exist")
    void shouldReturnEmptyListWhenNoDevices() {
        // Given
        when(deviceRepository.findAll()).thenReturn(List.of());

        // When
        List<DeviceResponseDto> result = deviceService.fetchAllDevices();

        // Then
        assertThat(result).isEmpty();
        verify(deviceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should fetch devices by brand successfully")
    void shouldFetchDevicesByBrandSuccessfully() {
        // Given
        Device device1 = new Device("iPhone 15", "Apple", StateType.AVAILABLE);
        Device device2 = new Device("iPhone 14", "Apple", StateType.IN_USE);
        List<Device> appleDevices = Arrays.asList(device1, device2);

        when(deviceRepository.findDevicesByBrand("Apple")).thenReturn(appleDevices);

        // When
        List<DeviceResponseDto> result = deviceService.fetchDevicesByBrand("Apple");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(device -> device.brand().equals("Apple"));
        verify(deviceRepository, times(1)).findDevicesByBrand("Apple");
    }

    @Test
    @DisplayName("Should return empty list when no devices found for brand")
    void shouldReturnEmptyListWhenNoBrandDevices() {
        // Given
        when(deviceRepository.findDevicesByBrand("Unknown")).thenReturn(List.of());

        // When
        List<DeviceResponseDto> result = deviceService.fetchDevicesByBrand("Unknown");

        // Then
        assertThat(result).isEmpty();
        verify(deviceRepository, times(1)).findDevicesByBrand("Unknown");
    }

    @Test
    @DisplayName("Should fetch devices by state successfully")
    void shouldFetchDevicesByStateSuccessfully() {
        // Given
        Device device1 = new Device("iPhone 15", "Apple", StateType.AVAILABLE);
        Device device2 = new Device("Galaxy S24", "Samsung", StateType.AVAILABLE);
        List<Device> availableDevices = Arrays.asList(device1, device2);

        when(deviceRepository.findDevicesByState(StateType.AVAILABLE)).thenReturn(availableDevices);

        // When
        List<DeviceResponseDto> result = deviceService.fetchDevicesByState(StateType.AVAILABLE);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(device -> device.state().equals(StateType.AVAILABLE));
        verify(deviceRepository, times(1)).findDevicesByState(StateType.AVAILABLE);
    }

    @Test
    @DisplayName("Should return empty list when no devices in given state")
    void shouldReturnEmptyListWhenNoDevicesInState() {
        // Given
        when(deviceRepository.findDevicesByState(StateType.INACTIVE)).thenReturn(List.of());

        // When
        List<DeviceResponseDto> result = deviceService.fetchDevicesByState(StateType.INACTIVE);

        // Then
        assertThat(result).isEmpty();
        verify(deviceRepository, times(1)).findDevicesByState(StateType.INACTIVE);
    }

    @Test
    @DisplayName("Should delete device successfully")
    void shouldDeleteDeviceSuccessfully() {
        // Given
        when(deviceRepository.findDeviceById(1L)).thenReturn(Optional.of(testDevice));
        doNothing().when(deviceRepository).deleteById(1L);

        // When
        deviceService.deleteDevice(1L);

        // Then
        verify(deviceRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should not delete device when in use")
    void shouldNotDeleteDeviceWhenInUse() {
        // Given
        when(deviceRepository.findDeviceById(1L)).thenReturn(Optional.of(deviceInUse));

        // when and Then
        assertThatThrownBy(() -> deviceService.deleteDevice(1L))
                .isInstanceOf(DeviceDeleteException.class)
                .hasMessageContaining("Failed to delete device cause of device in use with ID: 1");

        verify(deviceRepository, times(0)).deleteById(1L);
    }

    @Test
    @DisplayName("Should handle deletion of non-existent device")
    void shouldHandleDeletionOfNonExistentDevice() {
        // Given
        doNothing().when(deviceRepository).deleteById(999L);

        // When
        assertThatThrownBy(() -> deviceService.fetchDeviceById(999L))
                .isInstanceOf(DeviceNotFound.class)
                .hasMessageContaining("Device not found with ID: 999");

        // Then
        verify(deviceRepository, times(0)).deleteById(999L);
    }
}
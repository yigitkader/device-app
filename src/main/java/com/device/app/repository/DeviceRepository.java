package com.device.app.repository;

import com.device.app.domain.Device;
import com.device.app.enums.StateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device createDevice(Device device);

    Device updateDevice(Device device);

    Optional<Device> findDeviceById(Long id);

    List<Device> findDevicesByBrand(String brand);

    List<Device> findDevicesByState(StateType state);
}

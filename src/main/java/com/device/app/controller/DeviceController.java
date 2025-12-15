package com.device.app.controller;

import com.device.app.domain.Device;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/device/v1")
public class DeviceController {

    public Device createDevice(){
        return null;
    }

    public Device updateDevice(Device device){
        return null;
    }

    public Device fetchDeviceById(String id){
        return null;
    }

    public List<Device> fetchAllDevices(){
        return null;
    }

    public List<Device> fetchDevicesByBrand(String brand){
        return null;
    }

    public List<Device> fetchDevicesByState(String state){
        return null;
    }

    public Boolean deleteDevice(String id){
        return false;
    }
}

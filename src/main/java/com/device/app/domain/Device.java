package com.device.app.domain;

import com.device.app.dto.DeviceResponseDto;
import com.device.app.enums.StateType;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StateType state = StateType.AVAILABLE;

    @Column(nullable = false, updatable = false)
    private Instant creationTime;


    public Device() {}

    public Device(String name, String brand, StateType state) {
        this.name = name;
        this.brand = brand;
        this.state = state;
        this.creationTime = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public StateType getState() {
        return state;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public DeviceResponseDto toDto() {
        return new DeviceResponseDto(this.id, this.name, this.brand, this.state, this.creationTime);
    }
}
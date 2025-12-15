package com.device.app.domain;

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

    @Column(nullable = false)
    private StateType state = StateType.AVAILABLE;

    @Column(nullable = false, updatable = false)
    private Instant creationTime;
}


package com.example.webvehicles.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Long price;
    private Long weight;
    private Long cylinder;
    private Long length;
    private String color;
    private Integer doorNumbers;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private VehicleType vehicleType;

    @OneToMany(mappedBy = "vehicle")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    private LocalDate lastUpdatedDate;

    @ManyToOne
    @JoinColumn(name = "last_updated_by")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Admin lastUpdatedBy;

    @ManyToOne
    @JoinColumn(name = "sales_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User sales;

}

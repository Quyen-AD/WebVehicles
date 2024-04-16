package com.example.webvehicles.model;

import lombok.Data;

@Data
public class VehicleDto {
    private Integer id;
    private String name;
    private Long price;
    private Long weight;
    private Long cylinder;
    private Long length;
    private String color;
    private Integer doorNumbers;
    private Integer vehicleTypeId;
    private String vehicleTypeName;
    private Integer userId;
    private String fullName;
}

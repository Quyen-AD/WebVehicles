package com.example.webvehicles.model;

import lombok.Data;

@Data
public class CommentDto {
    private Integer id;
    private String content;
    private String createdDate;
    private Integer vehicleId;
    private Integer name;
    private Integer userId;
    private String username;
}

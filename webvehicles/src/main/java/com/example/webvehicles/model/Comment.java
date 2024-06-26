package com.example.webvehicles.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;
    private LocalDate commentDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    private LocalDate lastUpdatedDate;

    @ManyToOne
    @JoinColumn(name = "last_updated_by")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Admin lastUpdatedBy;

}

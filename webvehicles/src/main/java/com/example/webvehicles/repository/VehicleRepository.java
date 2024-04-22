package com.example.webvehicles.repository;

import com.example.webvehicles.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllByVehicleType_Id(Integer id);

    List<Vehicle> findAllBySales_Id(int id);

    Integer countAllByVehicleType_Id(Integer id);

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.agency.id = :agencyId")
    Integer countVehiclesByAgencyId(@Param("agencyId") Integer agencyId);

}

package com.example.webvehicles.repository;

import com.example.webvehicles.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllByVehicleType_Id(Integer id);

    List<Vehicle> findAllBySales_Id(int id);

    @Query("SELECT SUM(v.price) FROM Vehicle v WHERE v.vehicleType.id IN (2, 4)")
    Long getTotalValueOfTwoWheeledVehicles();

    @Query("SELECT SUM(v.price) FROM Vehicle v WHERE v.vehicleType.id IN (1, 3)")
    Long getTotalValueOfFourWheeledVehicles();
}

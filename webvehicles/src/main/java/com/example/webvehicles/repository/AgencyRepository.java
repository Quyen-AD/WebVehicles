package com.example.webvehicles.repository;

import com.example.webvehicles.model.Agency;
import com.example.webvehicles.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgencyRepository extends JpaRepository<Agency, Integer> {

}

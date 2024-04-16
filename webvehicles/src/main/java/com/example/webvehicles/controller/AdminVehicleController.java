package com.example.webvehicles.controller;

import com.example.webvehicles.model.User;
import com.example.webvehicles.model.Vehicle;
import com.example.webvehicles.model.VehicleDto;
import com.example.webvehicles.model.VehicleType;
import com.example.webvehicles.repository.UserRepository;
import com.example.webvehicles.repository.VehicleRepository;
import com.example.webvehicles.repository.VehicleTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class AdminVehicleController {
    private final VehicleTypeRepository vehicleTypeRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public AdminVehicleController(VehicleTypeRepository vehicleTypeRepository, VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/vehicle")
    public String vehicles(Model model) {
        model.addAttribute("vehicles", vehicleRepository.findAll());
        return "admin/vehicle/list-vehicle";
    }

    @GetMapping("/admin/vehicle/form-create-vehicle")
    public String getFormCreateVehicle(Model model){
        VehicleDto vehicleDto = new VehicleDto();
        model.addAttribute("vehicle",vehicleDto);
        model.addAttribute("users", userRepository.findAllByRole("SALES"));
        model.addAttribute("types", vehicleTypeRepository.findAll());
        return "admin/vehicle/vehicle-create";
    }

    @PostMapping("/admin/vehicle/save")
    public String saveVehicle(@ModelAttribute(name = "vehicle") VehicleDto vehicleDto){
        Vehicle vehicle = new Vehicle();
        VehicleType vehicleType = vehicleTypeRepository.findById(vehicleDto.getVehicleTypeId()).get();
        User user = userRepository.findById(vehicleDto.getUserId()).get();
        vehicle.setColor(vehicleDto.getColor());
        vehicle.setCylinder(vehicleDto.getCylinder());
        vehicle.setPrice(vehicleDto.getPrice());
        vehicle.setWeight(vehicleDto.getWeight());
        vehicle.setName(vehicleDto.getName());
        vehicle.setDoorNumbers(vehicleDto.getDoorNumbers());
        vehicle.setVehicleType(vehicleType);
        vehicle.setSales(user);
        vehicleRepository.save(vehicle);
        return "redirect:/admin/vehicle";
    }

    @GetMapping("/admin/vehicle/form-update-vehicle/{id}")
    public String getFormUpdateVehicle(@PathVariable("id") int id, Model model){
        Vehicle vehicle = vehicleRepository.findById(id).get();
        model.addAttribute("types", vehicleTypeRepository.findAll());
        model.addAttribute("users", userRepository.findAllByRole("SALES"));
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setColor(vehicle.getColor());
        vehicleDto.setCylinder(vehicle.getCylinder());
        vehicleDto.setPrice(vehicle.getPrice());
        vehicleDto.setWeight(vehicle.getWeight());
        vehicleDto.setName(vehicle.getName());
        vehicleDto.setDoorNumbers(vehicle.getDoorNumbers());
        vehicleDto.setVehicleTypeId(vehicle.getVehicleType().getId());
        vehicleDto.setVehicleTypeName(vehicle.getVehicleType().getName());
        if(vehicle.getSales() == null) {
            vehicleDto.setUserId(0);
            vehicleDto.setFullName("");
        }
        else {
            vehicleDto.setUserId(vehicle.getSales().getId());
            vehicleDto.setFullName(vehicle.getSales().getFullName());
        }
        model.addAttribute("vehicle",vehicleDto);
        return "admin/vehicle/vehicle-update";
    }

    @PostMapping("/admin/vehicle/update")
    public String update(@ModelAttribute(name = "vehicle") VehicleDto vehicleDto){
        Vehicle vehicle = vehicleRepository.findById(vehicleDto.getId()).get();
        VehicleType vehicleType = vehicleTypeRepository.findById(vehicleDto.getVehicleTypeId()).get();
        User user = userRepository.findById(vehicleDto.getUserId()).get();
        vehicle.setColor(vehicleDto.getColor());
        vehicle.setCylinder(vehicleDto.getCylinder());
        vehicle.setPrice(vehicleDto.getPrice());
        vehicle.setWeight(vehicleDto.getWeight());
        vehicle.setName(vehicleDto.getName());
        vehicle.setDoorNumbers(vehicleDto.getDoorNumbers());
        vehicle.setVehicleType(vehicleType);
        vehicle.setSales(user);
        vehicleRepository.save(vehicle);
        return "redirect:/admin/vehicle";
    }

    @GetMapping("/admin/vehicle/delete/{id}")
    public String delete(@PathVariable("id") int id){
        vehicleRepository.deleteById(id);
        return "redirect:/admin/vehicle";
    }

}

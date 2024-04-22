package com.example.webvehicles.controller;

import java.text.DecimalFormat;
import com.example.webvehicles.model.Admin;
import com.example.webvehicles.model.Agency;
import com.example.webvehicles.model.AgencyDto;
import com.example.webvehicles.model.User;
import com.example.webvehicles.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ReservationRepository reservationRepository;
    private final AgencyRepository agencyRepository;
    private int userId = 0;

    public AdminController(AdminRepository adminRepository, UserRepository userRepository, VehicleRepository vehicleRepository, ReservationRepository reservationRepository, AgencyRepository agencyRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.reservationRepository = reservationRepository;
        this.agencyRepository = agencyRepository;
    }

    @GetMapping("/admin/login")
    public String login() {
        return "admin-login";
    }

    @PostMapping("/admin/login/try")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {
        Admin admin = adminRepository.findByEmailAndPassword(email, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            this.userId = admin.getId();
            return "redirect:/admin";
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("sales", userRepository.findAllByRole("SALES").size());
        model.addAttribute("cars", vehicleRepository.findAllByVehicleType_Id(1).size());
        model.addAttribute("trucks", vehicleRepository.findAllByVehicleType_Id(3).size());
        model.addAttribute("motorcycles", vehicleRepository.findAllByVehicleType_Id(2).size());
        model.addAttribute("clients", userRepository.findAllByRole("CLIENTS").size());
        model.addAttribute("reservations", reservationRepository.count());
        model.addAttribute("total2Wheels", reservationRepository.getTotalValueOfTwoWheeledVehicles());
        model.addAttribute("total4Wheels", reservationRepository.getTotalValueOfFourWheeledVehicles());
        model.addAttribute("sum", reservationRepository.getTotalValueOfTwoWheeledVehicles() + reservationRepository.getTotalValueOfFourWheeledVehicles());

        Integer car = vehicleRepository.countAllByVehicleType_Id(1);
        Integer truck = vehicleRepository.countAllByVehicleType_Id(3);
        Integer twoWheels = vehicleRepository.countAllByVehicleType_Id(2) + vehicleRepository.countAllByVehicleType_Id(4);
        Integer sum = car + truck + twoWheels;


        DecimalFormat df = new DecimalFormat("#.##");
        double carPercent = Double.parseDouble(df.format((double) car / sum * 100));
        double truckPercent = Double.parseDouble(df.format((double) truck / sum * 100));
        double twoWheelPercent = Double.parseDouble(df.format((double) twoWheels / sum * 100));

        model.addAttribute("carPercent", carPercent);
        model.addAttribute("truckPercent", truckPercent);
        model.addAttribute("twoWheelPercent", twoWheelPercent);

        Long count = vehicleRepository.count();
        List<AgencyDto> agencyDtos = new ArrayList<>();
        List<Agency> agencies = agencyRepository.findAll();
        for(Agency agency : agencies) {
            Integer countVehicle = vehicleRepository.countVehiclesByAgencyId(agency.getId());
            double percent = Double.parseDouble(df.format((double) countVehicle / count * 100));
            AgencyDto agencyDto = new AgencyDto();
            agencyDto.setId(agency.getId());
            agencyDto.setName(agency.getName());
            agencyDto.setPercent(percent);
            agencyDtos.add(agencyDto);
        }

        model.addAttribute("agencies", agencyDtos);
        return "admin/index";
    }


}

package com.example.webvehicles.controller;

import com.example.webvehicles.model.Admin;
import com.example.webvehicles.repository.AdminRepository;
import com.example.webvehicles.repository.ReservationRepository;
import com.example.webvehicles.repository.UserRepository;
import com.example.webvehicles.repository.VehicleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ReservationRepository reservationRepository;
    private int userId = 0;

    public AdminController(AdminRepository adminRepository, UserRepository userRepository, VehicleRepository vehicleRepository, ReservationRepository reservationRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.reservationRepository = reservationRepository;
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
        model.addAttribute("total2Wheels", vehicleRepository.getTotalValueOfTwoWheeledVehicles());
        model.addAttribute("total4Wheels", vehicleRepository.getTotalValueOfTwoWheeledVehicles());
        model.addAttribute("sum", vehicleRepository.getTotalValueOfTwoWheeledVehicles() + vehicleRepository.getTotalValueOfFourWheeledVehicles());
        return "admin/index";
    }


}

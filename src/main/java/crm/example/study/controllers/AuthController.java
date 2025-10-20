package crm.example.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import crm.example.study.model.DTO.EmployeeDTO;
import crm.example.study.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    private EmployeeRepository EmployeeRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(EmployeeRepository EmployeeRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.EmployeeRepository = EmployeeRepository;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "employee_create_form";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // @PostMapping("/register")
    // public String processRegistration(EmployeeDTO EmployeeDTO) {
    // EmployeeRepository.save(EmployeeDTO.toEmployee(passwordEncoder));

    // return "redirect:/";
    // }

}

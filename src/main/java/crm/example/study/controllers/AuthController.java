package crm.example.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import crm.example.study.model.Employee;
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


    @GetMapping("/login")
    public String login(@AuthenticationPrincipal Employee employee) {
        if (employee != null) {
            return "redirect:home";
        }
        return "login";
    }

}

package crm.example.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import crm.example.study.model.Employee;
import crm.example.study.model.DTO.ChangePasswordDTO;
import crm.example.study.services.EmployeeService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/personal")
@EnableMethodSecurity(prePostEnabled = true)
public class ProfileController {

    private final EmployeeService employeeService;

    @Autowired
    public ProfileController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String getPersonalPage(@AuthenticationPrincipal Employee employee,
        Model model) {
        model.addAttribute("employee", employeeService.getAuthProfile(employee));
        return "personal_profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/password")
    public String getPasswordChangeForm(@AuthenticationPrincipal Employee employee,
        Model model) {
        model.addAttribute("password", new ChangePasswordDTO());
        return "pass_change_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/password")
    public String postMethodName(@AuthenticationPrincipal Employee employee,
        @Valid @ModelAttribute("password") ChangePasswordDTO dto,
        BindingResult bindingResult,
        Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("password", dto);
            return "pass_change_form";
        }
        try {
            employeeService.changeEmployeePassword(employee, dto);
        } catch (Exception e) {
            bindingResult.rejectValue("oldPass", "error.password", e.getMessage());
            model.addAttribute("password", dto);
            return "pass_change_form";
        }
        
        return "redirect:/personal/profile";
    }
    
    
}

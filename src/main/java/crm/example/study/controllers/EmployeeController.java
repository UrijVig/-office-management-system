package crm.example.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import crm.example.study.model.DTO.EmployeeDTO;
import crm.example.study.model.DTO.EmployeePasswordDTO;
import crm.example.study.services.EmployeeService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@RequestMapping("/employees")
@EnableMethodSecurity(prePostEnabled = true)
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public String showEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees_list";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/create")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "employee_create_form";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employeeDTO);
            return "employee_create_form";
        }
        try {
            employeeService.saveEmployee(employeeDTO);
        } catch (Exception e) {
            bindingResult.rejectValue("username", "error.employee", e.getMessage());
            model.addAttribute("employee", employeeDTO);
            return "employee_create_form";
        }
        return "redirect:/employees";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findEmployeeById(id));
        return "employee_update_form";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/update")
    public String updateEmployee(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employeeDTO);
            return "employee_update_form";
        }
        try {
            employeeService.updateEmployee(employeeDTO);
        } catch (Exception e) {
            bindingResult.rejectValue("username", "error.employee", e.getMessage());
            model.addAttribute("employee", employeeDTO);
            return "employee_update_form";
        }
        return "redirect:/employees";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/update/{id}/password")
    public String getPasswordResetForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findEmployeeById(id));
        return "pass_reset_form";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/update/{id}/password")
    public String resetEmployeePassword(@PathVariable Long id, 
            @Valid @ModelAttribute("employee") EmployeePasswordDTO employeePasswordDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employeePasswordDTO);
            System.out.println(bindingResult.getAllErrors());
            return "pass_reset_form";
        }        
        employeeService.resetEmployeePassword(employeePasswordDTO);
        return "redirect:/employees";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/setactive/{id}")
    public String setEmployeeActive(@PathVariable Long id) {
        System.out.println(id);
        employeeService.setEmployeeActive(id);
        return "redirect:/employees";
    }
    
}

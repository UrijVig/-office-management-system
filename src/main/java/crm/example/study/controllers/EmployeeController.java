package crm.example.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import crm.example.study.model.DTO.EmployeeDTO;
import crm.example.study.services.EmployeeService;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute EmployeeDTO employeeDTO) {
        employeeService.saveEmployee(employeeDTO);
        return "redirect:/employees";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(employeeDTO);
        return "redirect:/employees";
    }

    @GetMapping
    public String showEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees_list";
    }

    @GetMapping("/create")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "employee_create_form";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findEmployeeById(id));
        return "employee_update_form";
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }

}

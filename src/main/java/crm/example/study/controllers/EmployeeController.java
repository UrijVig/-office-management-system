package crm.example.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import crm.example.study.model.Employee;
import crm.example.study.model.DTO.EmployeeDTO;
import crm.example.study.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeController(EmployeeRepository EmployeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepo = EmployeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute EmployeeDTO employeeDTO) {
        Employee employee = employeeDTO.toEmployee(passwordEncoder);
        employeeRepo.save(employee);
        return "redirect:/employees";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute EmployeeDTO employeeDTO) {
        Employee employee = new Employee(
                employeeDTO.getId(),
                employeeDTO.getLogin(),
                employeeDTO.getPassword(),
                employeeDTO.getName(),
                employeeDTO.getSurname(),
                employeeDTO.getRole());
        employeeRepo.save(employee);
        return "redirect:/employees";
    }

    @GetMapping
    public String showEmployees(Model model) {
        model.addAttribute("employees", employeeRepo.findAll());
        return "employees_list";
    }

    @GetMapping("/create")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "employee_create_form";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeRepo.findEmployeeById(id));
        return "employee_update_form";
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model) {
        employeeRepo.removeEmployeeById(id);
        return "redirect:/employees";
    }

}

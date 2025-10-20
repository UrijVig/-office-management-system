package crm.example.study.model.DTO;

import org.springframework.security.crypto.password.PasswordEncoder;

import crm.example.study.model.Employee;
import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private Employee.Role role;

    public Employee toEmployee(PasswordEncoder passwordEncoder) {
        return new Employee(null, login, passwordEncoder.encode(password), name, surname, role);
    }
}

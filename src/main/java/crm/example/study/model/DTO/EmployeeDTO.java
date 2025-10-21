package crm.example.study.model.DTO;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import crm.example.study.model.Employee;
import crm.example.study.model.Role;
import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String role;
}

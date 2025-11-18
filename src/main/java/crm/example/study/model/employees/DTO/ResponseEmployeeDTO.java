package crm.example.study.model.employees.DTO;

import java.time.LocalDateTime;

import crm.example.study.model.employees.Employee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ResponseEmployeeDTO {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    private LocalDateTime deactivatedAt;
    private LocalDateTime passwordUpdatedAt;
    private String workplace;
    public ResponseEmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.username = employee.getUsername();
        this.name = employee.getName();
        this.surname = employee.getSurname();
        this.role = employee.getRole().getRole();
        this.createdAt = employee.getCreatedAt();
        this.updatedAt = employee.getUpdatedAt();
        this.active = employee.isActive();
        this.deactivatedAt = employee.getDeactivatedAt();
        this.passwordUpdatedAt = employee.getPasswordUpdatedAt();
        this.workplace = employee.getWorkplace() != null ? employee.getWorkplace().getName() : "";
    }

    
}

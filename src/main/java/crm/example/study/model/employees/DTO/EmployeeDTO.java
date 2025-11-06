package crm.example.study.model.employees.DTO;

import crm.example.study.model.employees.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) для сущности Employee.
 * Используется для передачи данных между слоями приложения, 
 * валидации входящих данных и взаимодействия с клиентом.
 * Содержит аннотации валидации для проверки корректности данных.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    @NotBlank(message = "Имя пользователя не должно быть пустым!")
    private String username;
    @NotBlank(message = "Поле пароля не должно быть пустым!")
    @Size(min = 4, message = "Пароль должен быть не короче 4х символов!")
    private String password;
    @NotBlank(message = "Поле имени не должно быть пустым!")
    private String name;
    @NotBlank(message = "Поле фамилии не должно быть пустым!")
    private String surname;
    @NotNull(message = "Выберите уровень доступа!")
    private String role;

    /**
     * Конструктор для создания DTO на основе сущности Employee.
     * Преобразует сущность базы данных в объект для передачи данных.
     * 
     * @param employee сущность Employee, на основе которой создается DTO
     */
    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.username = employee.getUsername();
        this.name = employee.getName();
        this.surname = employee.getSurname();
        this.password = employee.getPassword();
        this.role = employee.getRole().getRole();
    }
}

package crm.example.study.model.employees.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object (DTO) для установки или сброса пароля сотрудника.
 * Используется администраторами для управления паролями пользователей.
 * Содержит базовую валидацию для обеспечения корректности пароля.
 */
@Data
public class EmployeePasswordDTO {
    /**
     * Уникальный идентификатор сотрудника, для которого устанавливается пароль
     */
    private Long id;
    /**
     * Новый пароль для сотрудника
     * Должен быть не пустым и соответствовать требованиям системы
     */
    @NotBlank(message = "Поле пароля не должно быть пустым!")
    @Size(min = 4, message = "Пароль должен быть не короче 4х символов!")
    private String password;
}

package crm.example.study.model.DTO;

import jakarta.validation.constraints.NotBlank;
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
    private String password;
}

package crm.example.study.model.employees.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object (DTO) для смены пароля пользователя.
 * Используется для валидации данных при изменении пароля в системе.
 * Содержит проверки на корректность старого и нового пароля.
 */
@Data
public class ChangePasswordDTO {
    /**
     * Текущий (старый) пароль пользователя
     * Используется для проверки подлинности пользователя перед сменой пароля
     */
    @NotBlank(message = "Введите старый пароль!")
    @Size(min = 4, message = "Пароль должен быть не короче 4х символов!")
    private String oldPass;
    /**
     * Новый пароль пользователя
     * Должен соответствовать требованиям минимальной длины
     */
    @NotBlank(message = "Введите новый пароль!")
    @Size(min = 4, message = "Пароль должен быть не короче 4х символов!")
    private String newPass;
}

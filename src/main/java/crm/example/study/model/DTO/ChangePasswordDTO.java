package crm.example.study.model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    @NotBlank(message = "Введите старый пароль!")
    @Size(min = 4, message = "Пароль должен быть не короче 4х символов!")
    private String oldPass;
    @NotBlank(message = "Введите новый пароль!")
    @Size(min = 4, message = "Пароль должен быть не короче 4х символов!")
    private String newPass;
}

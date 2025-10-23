package crm.example.study.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeePasswordDTO {
    private Long id;
    @NotBlank(message = "Поле пароля не должно быть пустым!")
    private String password;
}

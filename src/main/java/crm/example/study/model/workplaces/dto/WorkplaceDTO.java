package crm.example.study.model.workplaces.dto;

import crm.example.study.model.workplaces.Workplace;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkplaceDTO {

    private Long id;
    @Size(min = 0, max = 20, message = "Имя должно быть не больше 20 символов! ")
    private String name;
    @Size(min = 0, max = 255, message = "Описание не может быть больше 255 символов! ")
    private String description;

    public WorkplaceDTO(Workplace workplace){
        this.id = workplace.getId();
        this.name = workplace.getName();
        this.description = workplace.getDescription();
    }
}

package crm.example.study.model.workplaces.dto;

import java.util.List;

import crm.example.study.model.equipment.Equipment;
import crm.example.study.model.workplaces.Workplace;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkplaceDesignerDTO {    
    private Long id;
    @Size(min = 0, max = 20, message = "Имя должно быть не больше 20 символов! ")
    private String name;
    @Size(min = 0, max = 255, message = "Описание не может быть больше 255 символов! ")
    private String description;
    @NotEmpty(message = "Выберите оборудование из списка! ")
    private List<String> equipments;

    public WorkplaceDesignerDTO(Workplace workplace) {
        for (Equipment equipment : workplace.getEquipments()) {
            this.equipments.add(equipment.getSerialNumber());
        }
    }
}

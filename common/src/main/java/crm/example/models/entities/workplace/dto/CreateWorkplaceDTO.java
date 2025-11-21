package crm.example.models.entities.workplace.dto;

import java.util.ArrayList;
import java.util.List;

import crm.example.models.entities.equipment.Equipment;
import crm.example.models.entities.workplace.Workplace;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateWorkplaceDTO {
    private Long id;
    @Size(min = 0, max = 20, message = "Имя должно быть не больше 20 символов! ")
    private String name;
    @Size(min = 0, max = 255, message = "Описание не может быть больше 255 символов! ")
    private String description;
    private List<String> equipments = new ArrayList<>();

    public CreateWorkplaceDTO(Workplace workplace) {
        this.id = workplace.getId();
        this.name = workplace.getName();
        this.description = workplace.getDescription();
        for (Equipment equipment : workplace.getEquipments()) {
            this.equipments.add(equipment.getSerialNumber());
        }
    }
}

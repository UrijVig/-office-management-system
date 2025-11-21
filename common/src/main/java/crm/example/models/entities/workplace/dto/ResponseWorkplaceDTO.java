package crm.example.models.entities.workplace.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import crm.example.models.entities.workplace.Workplace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWorkplaceDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String employee;
    private List<String> equipments;
    
    public ResponseWorkplaceDTO(Workplace workplace) {
        this.id = workplace.getId();
        this.name = workplace.getName() != null ? workplace.getName() : "";
        this.description = workplace.getDescription() != null ? workplace.getDescription() : "";
        this.createdAt = workplace.getCreatedAt();
        this.updatedAt = workplace.getUpdatedAt();
        this.employee = workplace.getEmployee() != null ? workplace.getEmployee().getUsername() : "";
        if (workplace.getEquipments() != null){
            equipments = new ArrayList<>();
            workplace.getEquipments().stream().forEach(eq -> equipments.add(eq.getSerialNumber()));
        }
    }

    
}

package crm.example.models.entities.equipment.dto;

import java.time.LocalDateTime;

import crm.example.models.entities.equipment.Equipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEquipmentDTO {
    private Long id;
    private String serialNumber;
    private String type;
    private String name;
    private String brand;
    private String model;
    private String description;
    private Double size;
    private String location;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime servedAt;
    private Double price;
    private String workplace;

    public ResponseEquipmentDTO(Equipment equipment) {
        this.id = equipment.getId();
        this.serialNumber = equipment.getSerialNumber();
        this.type = equipment.getType() != null ? equipment.getType().getType() : "";
        this.name = equipment.getName();
        this.brand = equipment.getBrand();
        this.model = equipment.getModel();
        this.description = equipment.getDescription();
        this.size = equipment.getSize();
        this.location = equipment.getLocation() != null ? equipment.getLocation().getLocation() : "";
        this.status = equipment.getStatus() != null ? equipment.getStatus().getStatus() : "";
        this.createdAt = equipment.getCreatedAt();
        this.updatedAt = equipment.getUpdatedAt();
        this.servedAt = equipment.getServedAt();
        this.price = equipment.getPrice();
        this.workplace = equipment.getWorkplace() != null ? equipment.getWorkplace().getName() : "";
    }
}

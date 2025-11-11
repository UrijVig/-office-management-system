package crm.example.study.model.equipment.DTO;

import crm.example.study.model.equipment.Equipment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO {
    private Long id;
    @NotBlank(message = "Введите серийный номер оборудования!")
    @Size(min = 4, max = 50, message = "От4 до 50 символов!")
    private String serialNumber;
    @NotNull(message = "Выберите тип оборудования")
    private String type;
    @NotBlank(message = "Введите наименование оборудования")
    @Size(min = 4, max = 50, message = "От4 до 50 символов!")
    private String name;
    @NotBlank(message = "Укажите бренд/издателя оборудования")
    @Size(min = 4, max = 50, message = "От4 до 50 символов!")
    private String brand;
    @NotBlank(message = "Укажите можель оборудования")
    @Size(min = 4, max = 50, message = "От4 до 50 символов!")
    private String model;
    @Size(max = 255, message = "Описание не может быть больше 255 символов! ")
    private String description;
    @Min(message = "Размер не может бытьотрицательным!", value = 0)
    private Double size;
    @NotNull(message = "Уточните расположение!")
    private String location;
    @NotNull(message = "Уточните статус оборудования!")
    private String status;
    @Min(message = "Цена не может быть отрицательной!", value = 0)
    private Double price;

    public EquipmentDTO(Equipment eq) {
        this.id = eq.getId();
        this.serialNumber = eq.getSerialNumber();
        this.type = eq.getType().getType();
        this.name = eq.getName();
        this.brand = eq.getBrand();
        this.model = eq.getModel();
        this.description = eq.getDescription();
        this.size = eq.getSize();
        this.location = eq.getLocation().getLocation();
        this.status = eq.getStatus().getStatus();
        this.price = eq.getPrice();
    }
}

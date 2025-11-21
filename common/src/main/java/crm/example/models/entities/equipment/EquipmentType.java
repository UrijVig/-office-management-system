package crm.example.models.entities.equipment;
import java.time.Period;

import crm.example.models.entities.equipment.handlers.PeriodToStringConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "equipment_types")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class EquipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    @Convert(converter = PeriodToStringConverter.class)
    private Period serviceFrequency;
    @Convert(converter = PeriodToStringConverter.class)
    private Period serviceLife;

}
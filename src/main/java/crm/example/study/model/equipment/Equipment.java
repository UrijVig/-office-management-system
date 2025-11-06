package crm.example.study.model.equipment;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "equipments")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serialNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = true)
    private EquipmentType type;
    private String name;
    private String brand;
    private String model;
    private String description;
    private Double size;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private EquipmentLocation location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private EquipmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime servedAt;
    private Double price;
    // private Workplace workplace;

}

package crm.example.models.entities.workplace;

import java.time.LocalDateTime;
import java.util.List;

import crm.example.models.entities.emplyee.Employee;
import crm.example.models.entities.equipment.Equipment;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "workplaces")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workplace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "workplace", fetch = FetchType.LAZY)
    private Employee employee;

    @OneToMany(mappedBy = "workplace", fetch = FetchType.LAZY)
    private List<Equipment> equipments;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        if (equipments != null) {
            equipments.forEach(eq -> eq.setWorkplace(this));
        }
    }

    @PreUpdate
    public void onUpdate(){
        this.createdAt = LocalDateTime.now();
        if (equipments != null) {
            equipments.forEach(eq -> eq.setWorkplace(this));
        }
    }

    @PreRemove
    public void onRemove(){
        if (employee != null) {
            employee.setWorkplace(null);
        }
    }
}

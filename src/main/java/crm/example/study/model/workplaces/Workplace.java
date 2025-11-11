package crm.example.study.model.workplaces;

import java.time.LocalDateTime;
import java.util.List;

import crm.example.study.model.employees.Employee;
import crm.example.study.model.equipment.Equipment;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
}

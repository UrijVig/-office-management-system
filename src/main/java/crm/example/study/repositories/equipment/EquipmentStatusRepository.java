package crm.example.study.repositories.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.equipment.EquipmentStatus;

@Repository
public interface EquipmentStatusRepository extends JpaRepository<EquipmentStatus, Long>{

    EquipmentStatus findByStatus(String status);
    
}

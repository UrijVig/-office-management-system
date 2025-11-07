package crm.example.study.repositories.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.equipment.EquipmentType;

@Repository
public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long>{

    EquipmentType findByType(String type);
    
}

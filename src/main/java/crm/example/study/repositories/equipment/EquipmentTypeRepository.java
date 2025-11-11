package crm.example.study.repositories.equipment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.equipment.EquipmentType;

@Repository
public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long>{

    Optional<EquipmentType> findByType(String type);
    
}

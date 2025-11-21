package crm.example.repositories.equipment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.models.entities.equipment.EquipmentType;

@Repository
public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long>{

    Optional<EquipmentType> findByType(String type);
    
}

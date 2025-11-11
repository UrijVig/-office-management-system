package crm.example.study.repositories.equipment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.equipment.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long>{

    Optional<Equipment> findBySerialNumber(String serialNumber);
    
}

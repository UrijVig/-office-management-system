package crm.example.repositories.equipment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.models.entities.equipment.EquipmentStatus;


@Repository
public interface EquipmentStatusRepository extends JpaRepository<EquipmentStatus, Long>{

    Optional<EquipmentStatus> findByStatus(String status);
    
}

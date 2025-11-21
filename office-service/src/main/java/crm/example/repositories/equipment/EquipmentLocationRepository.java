package crm.example.repositories.equipment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.models.entities.equipment.EquipmentLocation;



@Repository
public interface EquipmentLocationRepository extends JpaRepository<EquipmentLocation, Long>{

    Optional<EquipmentLocation> findByLocation(String location);
    
}

package crm.example.study.repositories.equipment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.equipment.EquipmentLocation;

@Repository
public interface EquipmentLocationRepository extends JpaRepository<EquipmentLocation, Long>{

    Optional<EquipmentLocation> findByLocation(String location);
    
}

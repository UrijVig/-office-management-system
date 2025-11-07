package crm.example.study.repositories.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.equipment.EquipmentLocation;

@Repository
public interface EquipmentLocationRepository extends JpaRepository<EquipmentLocation, Long>{

    EquipmentLocation findByLocation(String location);
    
}

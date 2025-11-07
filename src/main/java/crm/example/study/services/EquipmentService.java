package crm.example.study.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import crm.example.study.exceptions.InvalidSerialNumberException;
import crm.example.study.model.equipment.Equipment;
import crm.example.study.model.equipment.DTO.EquipmentDTO;
import crm.example.study.repositories.equipment.EquipmentLocationRepository;
import crm.example.study.repositories.equipment.EquipmentRepository;
import crm.example.study.repositories.equipment.EquipmentStatusRepository;
import crm.example.study.repositories.equipment.EquipmentTypeRepository;
import jakarta.transaction.Transactional;

@Service
public class EquipmentService {
    
    private EquipmentRepository equipRepo;
    private EquipmentStatusRepository equipStatusRepo;
    private EquipmentTypeRepository equipTypeRepo;
    private EquipmentLocationRepository equipLocationRepo;

    @Autowired
    public EquipmentService(EquipmentRepository equipRepo, EquipmentStatusRepository equipStatusRepo,
            EquipmentTypeRepository equipTypeRepo, EquipmentLocationRepository equipLocationRepo) {
        this.equipRepo = equipRepo;
        this.equipStatusRepo = equipStatusRepo;
        this.equipTypeRepo = equipTypeRepo;
        this.equipLocationRepo = equipLocationRepo;
    }


    public List<Equipment> getAll(){
        return equipRepo.findAll();
    }
    public List<Equipment> getAllBySort(){
        return equipRepo.findAll(Sort.by("type"));
    }

    @Transactional
    public void saveEquipment(EquipmentDTO dto) throws InvalidSerialNumberException{
        if (equipRepo.findBySerialNumber(dto.getSerialNumber()) != null) {
            throw new InvalidSerialNumberException("Данные серийный номер уже зарегистрирован!");
        } 
        Equipment equipment = new Equipment(null, 
        dto.getSerialNumber(),
        equipTypeRepo.findByType(dto.getType()), 
        dto.getName(),
        dto.getBrand(), 
        dto.getModel(),
        dto.getDescription(), 
        dto.getSize(), 
        equipLocationRepo.findByLocation(dto.getLocation()),
        equipStatusRepo.findByStatus(dto.getStatus()),
        null, null, null,
        dto.getPrice());
        equipRepo.save(equipment);
    }

    public void updateEquipment(EquipmentDTO dto){
        Equipment equipment = equipRepo.findById(dto.getId()).orElseThrow();
        equipment.setName(dto.getName());
        equipment.setBrand(dto.getBrand());
        equipment.setModel(dto.getModel());
        equipment.setSize(dto.getSize());
        equipment.setPrice(dto.getPrice());
        equipRepo.save(equipment);
    }

    public void deleteEquipment(Long id){
        equipRepo.deleteById(id);
    }

    public EquipmentDTO getEquipmentDTOById(Long id){
        return new EquipmentDTO(equipRepo.findById(id).orElseThrow());
    }

    public Equipment getEquipmentById(Long id){
        return equipRepo.findById(id).orElseThrow();
    }

    public void changeEquipmentLocation(Long id, String loc){
        Equipment eq = equipRepo.findById(id).orElseThrow();
        eq.setLocation(equipLocationRepo.findByLocation(loc));
        equipRepo.save(eq);
    }

    public void changeEquipmentStatus(Long id, String status){
        Equipment eq = equipRepo.findById(id).orElseThrow();
        eq.setStatus(equipStatusRepo.findByStatus(status));
        equipRepo.save(eq);
    }


}

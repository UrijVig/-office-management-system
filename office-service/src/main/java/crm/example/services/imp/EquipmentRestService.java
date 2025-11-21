package crm.example.services.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import crm.example.exceptions.InvalidUniqueDataException;
import crm.example.models.entities.equipment.Equipment;
import crm.example.models.entities.equipment.dto.CreateEquipmentDTO;
import crm.example.models.entities.equipment.dto.ResponseEquipmentDTO;
import crm.example.repositories.equipment.EquipmentLocationRepository;
import crm.example.repositories.equipment.EquipmentRepository;
import crm.example.repositories.equipment.EquipmentStatusRepository;
import crm.example.repositories.equipment.EquipmentTypeRepository;
import crm.example.services.RestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentRestService implements RestService<ResponseEquipmentDTO, CreateEquipmentDTO>{

    private final EquipmentRepository equipRepo;
    private final EquipmentStatusRepository equipStatusRepo;
    private final EquipmentTypeRepository equipTypeRepo;
    private final EquipmentLocationRepository equipLocationRepo;

    @Override
    @Transactional
    public ResponseEquipmentDTO save(CreateEquipmentDTO dto) throws InvalidUniqueDataException{
        if (equipRepo.findBySerialNumber(dto.getSerialNumber()) != null) {
            throw new InvalidUniqueDataException("Данные серийный номер уже зарегистрирован!");
        } 
        Equipment equipment = new Equipment(null, 
        dto.getSerialNumber(),
        equipTypeRepo.findByType(dto.getType()).orElseThrow(), 
        dto.getName(),
        dto.getBrand(), 
        dto.getModel(),
        dto.getDescription(), 
        dto.getSize(), 
        equipLocationRepo.findByLocation(dto.getLocation()).orElseThrow(),
        equipStatusRepo.findByStatus(dto.getStatus()).orElseThrow(),
        null, null, null,
        dto.getPrice(), null);
        equipRepo.save(equipment);
        return new ResponseEquipmentDTO(equipment);
    }

    @Override
    public ResponseEquipmentDTO getById(Long id) {
        return new ResponseEquipmentDTO(equipRepo.findById(id).orElseThrow());
    }

    @Override
    public List<ResponseEquipmentDTO> getAll() {
        List<ResponseEquipmentDTO> equipments = new ArrayList<>();
        equipRepo.findAll().stream().forEach(eq -> equipments.add(new ResponseEquipmentDTO(eq)));
        return equipments;
    }

    @Override
    @Transactional
    public void update(CreateEquipmentDTO dto) {
        Equipment equipment = equipRepo.findById(dto.getId()).orElseThrow();
        equipment.setName(dto.getName());
        equipment.setBrand(dto.getBrand());
        equipment.setModel(dto.getModel());
        equipment.setSize(dto.getSize());
        equipment.setPrice(dto.getPrice());
        equipment.setDescription(dto.getDescription());
        equipRepo.save(equipment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        equipRepo.deleteById(id);
    }
    
}

package crm.example.services.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crm.example.exceptions.InvalidUniqueDataException;
import crm.example.models.entities.equipment.Equipment;
import crm.example.models.entities.workplace.Workplace;
import crm.example.models.entities.workplace.dto.CreateWorkplaceDTO;
import crm.example.models.entities.workplace.dto.ResponseWorkplaceDTO;
import crm.example.repositories.equipment.EquipmentRepository;
import crm.example.repositories.workplace.WorkplaceRepository;
import crm.example.services.RestService;

@Service
public class WorkplaceRestService implements RestService<ResponseWorkplaceDTO, CreateWorkplaceDTO>{

    private final WorkplaceRepository workRepo;
    private final EquipmentRepository equpRepo;
    private final Workplace defWorkplace;

    @Autowired
    public WorkplaceRestService(WorkplaceRepository workRepo, EquipmentRepository equpRepo) {
        this.workRepo = workRepo;
        this.equpRepo = equpRepo;
        this.defWorkplace = workRepo.findByName("STORAGE").orElseThrow();
    }

    @Override
    public ResponseWorkplaceDTO save(CreateWorkplaceDTO dto) throws InvalidUniqueDataException{
        if (workRepo.findByName(dto.getName()).orElse(null) != null){
            throw new InvalidUniqueDataException("Рабочее место с данным именем уже существует! ");
        } 
        Workplace workplace = new Workplace(dto.getId()
        , dto.getName()
        , dto.getDescription()
        , null, null, null, null);
        workRepo.save(workplace);
        return new ResponseWorkplaceDTO(workplace);
    }

    @Override
    public ResponseWorkplaceDTO getById(Long id) {
        return new ResponseWorkplaceDTO(workRepo.findById(id).orElseThrow());
    }

    @Override
    public List<ResponseWorkplaceDTO> getAll() {
        List<ResponseWorkplaceDTO> workplaceDTOs = new ArrayList<>();
        workRepo.findAll().stream().forEach(wp -> workplaceDTOs.add(new ResponseWorkplaceDTO(wp)));
        return workplaceDTOs;
    }

    @Override
    public void update(CreateWorkplaceDTO dto) {
        Workplace workplace = workRepo.findById(dto.getId()).orElseThrow();
        workplace.setName(dto.getName());
        workplace.setDescription(dto.getDescription());
        workplace.getEquipments().forEach(eq -> eq.setWorkplace(defWorkplace));
        List<Equipment> equipments = new ArrayList<>();
        for (String equipmentSN : dto.getEquipments()) {
            equipments.add(equpRepo.findBySerialNumber(equipmentSN).orElseThrow());
        }
        workplace.setEquipments(equipments);
        workRepo.save(workplace);
    }

    @Override
    public void deleteById(Long id) {
        Workplace workplace = workRepo.findById(id).orElseThrow();
        if (workplace.getEquipments() != null) {
            workplace.getEquipments().forEach(eq -> eq.setWorkplace(defWorkplace));
        }
        workRepo.deleteById(id);
    }    
}

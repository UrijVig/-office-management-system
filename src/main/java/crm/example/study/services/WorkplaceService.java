package crm.example.study.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import crm.example.study.exceptions.InvalidWorkplaceNameException;
import crm.example.study.model.equipment.Equipment;
import crm.example.study.model.workplaces.Workplace;
import crm.example.study.model.workplaces.dto.ResponseWorkplaceDTO;
import crm.example.study.model.workplaces.dto.WorkplaceDTO;
import crm.example.study.model.workplaces.dto.WorkplaceDesignerDTO;
import crm.example.study.repositories.equipment.EquipmentRepository;
import crm.example.study.repositories.workplace.WorkplaceRepository;
import jakarta.transaction.Transactional;

@Service
public class WorkplaceService {
    
    private WorkplaceRepository workRepo;
    private EquipmentRepository equpRepo;
    private final Workplace defWorkplace;

    public WorkplaceService(WorkplaceRepository workRepo, EquipmentRepository equpRepo) {
        this.workRepo = workRepo;
        this.equpRepo = equpRepo;
        this.defWorkplace = workRepo.findByName("STORAGE").orElseThrow();
    }


    public List<Workplace> getAllWorkplaces(){
        return workRepo.findAll();
    }

    public Workplace getWorkplaceByName(String name){
        return workRepo.findByName(name).orElseThrow();
    }

    public Workplace getWorkplaceById(Long id){
        return workRepo.findById(id).orElseThrow();
    }

    public WorkplaceDTO getWorkplaceDTOById(Long id){
        return new WorkplaceDTO(workRepo.findById(id).orElseThrow());
    }

    public WorkplaceDesignerDTO getWorkplaceDesignerDTOById(Long id){
        return new WorkplaceDesignerDTO(workRepo.findById(id).orElseThrow());
    }

    @Transactional
    public Workplace saveWorkplace(WorkplaceDTO dto) throws InvalidWorkplaceNameException{
        if (workRepo.findByName(dto.getName()).orElse(null) != null){
            throw new InvalidWorkplaceNameException("Рабочее место с данным именем уже существует! ");
        } 
        Workplace workplace = new Workplace(dto.getId()
        , dto.getName()
        , dto.getDescription()
        , null, null, null, null);
        workRepo.save(workplace);
        return workplace;
    }

    @Transactional
    public void saveWorkplaceFromDesignerForm(WorkplaceDesignerDTO designerDTO) throws InvalidWorkplaceNameException{
        if (workRepo.findByName(designerDTO.getName()).orElse(null) != null){
            throw new InvalidWorkplaceNameException("Рабочее место с данным именем уже существует! ");
        }
        List<Equipment> equipments = new ArrayList<>();
        for (String equipmentSN : designerDTO.getEquipments()) {
            equipments.add(equpRepo.findBySerialNumber(equipmentSN).orElseThrow());
        }
        Workplace workplace = new Workplace(designerDTO.getId()
        , designerDTO.getName()
        , designerDTO.getDescription()
        , null, null, null
        , equipments);
        workRepo.save(workplace);
    }

    public void updateWorkplace(WorkplaceDesignerDTO designerDTO) {
        Workplace workplace = workRepo.findById(designerDTO.getId()).orElseThrow();
        workplace.setName(designerDTO.getName());
        workplace.setDescription(designerDTO.getDescription());
        workplace.getEquipments().forEach(eq -> eq.setWorkplace(defWorkplace));
        List<Equipment> equipments = new ArrayList<>();
        for (String equipmentSN : designerDTO.getEquipments()) {
            equipments.add(equpRepo.findBySerialNumber(equipmentSN).orElseThrow());
        }
        workplace.setEquipments(equipments);
        workRepo.save(workplace);
    }

    @Transactional
    public void updateWorkplaceDescription(WorkplaceDTO dto){
        Workplace workplace = workRepo.findById(dto.getId()).orElseThrow();
        workplace.setName(dto.getName());
        workplace.setDescription(dto.getDescription());
        workRepo.save(workplace);
    }

    @Transactional
    public void updateWorkplaceEquipments(WorkplaceDesignerDTO designerDTO){
        Workplace workplace = workRepo.findById(designerDTO.getId()).orElseThrow();
        workplace.getEquipments().forEach(eq -> eq.setWorkplace(defWorkplace));
        List<Equipment> equipments = new ArrayList<>();
        for (String equipmentSN : designerDTO.getEquipments()) {
            equipments.add(equpRepo.findBySerialNumber(equipmentSN).orElseThrow());
        }
        workplace.setEquipments(equipments);
        workRepo.save(workplace);
    }

    @Transactional
    public void deleteWorkplaceById(Long id){
        Workplace workplace = workRepo.findById(id).orElseThrow();
        if (workplace.getEquipments() != null) {
            workplace.getEquipments().forEach(eq -> eq.setWorkplace(defWorkplace));
        }
        workRepo.deleteById(id);
    }


    public List<ResponseWorkplaceDTO> getAllResponseWorkplaceDTO() {
        List<ResponseWorkplaceDTO> workplaceDTOs = new ArrayList<>();
        workRepo.findAll().stream().forEach(wp -> workplaceDTOs.add(new ResponseWorkplaceDTO(wp)));
        return workplaceDTOs;
    }


    public ResponseWorkplaceDTO getResponseWorkplaceDTOById(Long id) {
        return new ResponseWorkplaceDTO(workRepo.findById(id).orElseThrow());
    }

}

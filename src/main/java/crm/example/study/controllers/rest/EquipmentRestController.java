package crm.example.study.controllers.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import crm.example.study.exceptions.InvalidUniqueDataException;
import crm.example.study.model.equipment.Equipment;
import crm.example.study.model.equipment.DTO.EquipmentDTO;
import crm.example.study.model.equipment.DTO.ResponseEquipmentDTO;
import crm.example.study.services.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/office-api/equipments")
@RequiredArgsConstructor
public class EquipmentRestController {

    private final EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<List<ResponseEquipmentDTO>> getAllEquipments() {
        List<ResponseEquipmentDTO> equipments = equipmentService.getAllResponseEquipmentDTO();
        return ResponseEntity.ok(equipments);
    }

    @PostMapping
    public ResponseEntity<Equipment> saveEquipment(@Valid @RequestBody EquipmentDTO dto,
            UriComponentsBuilder uriComponentsBuilder) throws InvalidUniqueDataException{
        try {
            Equipment equipment = equipmentService.saveEquipment(dto);
            return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("/office-api/Equipments/{EquipmentId}")
                        .build(Map.of("EquipmentId", equipment.getId())))
                .body(equipment);
        } catch (Exception e) {
            throw new InvalidUniqueDataException(e.getMessage());
        }
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEquipmentDTO> getResponseEquipmentDTOById(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.getResponseEquipmentDTOById(id));
    }

    @PutMapping
    public ResponseEntity<?> updateEquipment(@Valid @RequestBody EquipmentDTO dto){
        equipmentService.updateEquipment(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployqqById(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }

}
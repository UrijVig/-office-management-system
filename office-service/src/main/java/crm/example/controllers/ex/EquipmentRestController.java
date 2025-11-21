package crm.example.controllers.ex;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import crm.example.controllers.AbstractRestController;
import crm.example.exceptions.InvalidUniqueDataException;
import crm.example.models.entities.equipment.dto.CreateEquipmentDTO;
import crm.example.models.entities.equipment.dto.ResponseEquipmentDTO;
import crm.example.services.imp.EquipmentRestService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/office-api/equipments")
public class EquipmentRestController extends AbstractRestController<ResponseEquipmentDTO, CreateEquipmentDTO>{


    @Autowired
    public EquipmentRestController(EquipmentRestService restService) {
        super(restService);
    }

    @PostMapping
    @Override
    protected ResponseEntity<ResponseEquipmentDTO> save(@Valid @RequestBody CreateEquipmentDTO dto, UriComponentsBuilder uri)
            throws InvalidUniqueDataException {
        try {
            ResponseEquipmentDTO equipment = restService.save(dto);
            return ResponseEntity
                    .created(uri
                            .replacePath("/office-api/Equipments/{EquipmentId}")
                            .build(Map.of("EquipmentId", equipment.getId())))
                    .body(equipment);
        } catch (Exception e) {
            throw new InvalidUniqueDataException(e.getMessage());
        }
    }
}
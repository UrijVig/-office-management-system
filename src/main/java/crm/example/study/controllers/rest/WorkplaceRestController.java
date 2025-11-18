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
import crm.example.study.model.workplaces.Workplace;
import crm.example.study.model.workplaces.dto.ResponseWorkplaceDTO;
import crm.example.study.model.workplaces.dto.WorkplaceDTO;
import crm.example.study.model.workplaces.dto.WorkplaceDesignerDTO;
import crm.example.study.services.WorkplaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/office-api/workplaces")
@RequiredArgsConstructor
public class WorkplaceRestController {

    private final WorkplaceService workplaceService;

    @GetMapping
    public ResponseEntity<List<ResponseWorkplaceDTO>> getAllWorkplaces() {
        List<ResponseWorkplaceDTO> workplaces = workplaceService.getAllResponseWorkplaceDTO();
        return ResponseEntity.ok(workplaces);
    }

    @PostMapping
    public ResponseEntity<Workplace> saveWorkplace(@Valid @RequestBody WorkplaceDTO dto,
            UriComponentsBuilder uriComponentsBuilder) throws InvalidUniqueDataException{
        try {
            Workplace workplace = workplaceService.saveWorkplace(dto);
            return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("/office-api/Workplaces/{WorkplaceId}")
                        .build(Map.of("WorkplaceId", workplace.getId())))
                .body(workplace);
        } catch (Exception e) {
            throw new InvalidUniqueDataException(e.getMessage());
        }
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWorkplaceDTO> getResponseWorkplaceDTOById(@PathVariable Long id) {
        return ResponseEntity.ok(workplaceService.getResponseWorkplaceDTOById(id));
    }

    @PutMapping
    public ResponseEntity<?> updateWorkplace(@Valid @RequestBody WorkplaceDesignerDTO dto){
        workplaceService.updateWorkplace(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployqqById(@PathVariable Long id) {
        workplaceService.deleteWorkplaceById(id);
        return ResponseEntity.noContent().build();
    }

}

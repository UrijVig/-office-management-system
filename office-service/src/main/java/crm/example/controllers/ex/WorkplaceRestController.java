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
import crm.example.models.entities.workplace.dto.CreateWorkplaceDTO;
import crm.example.models.entities.workplace.dto.ResponseWorkplaceDTO;
import crm.example.services.imp.WorkplaceRestService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/office-api/workplaces")
public class WorkplaceRestController extends AbstractRestController<ResponseWorkplaceDTO, CreateWorkplaceDTO>{

    @Autowired
    public WorkplaceRestController(WorkplaceRestService restService) {
        super(restService);
    }

    @PostMapping
    @Override
    protected ResponseEntity<ResponseWorkplaceDTO> save(@Valid @RequestBody CreateWorkplaceDTO dto, UriComponentsBuilder uri)
            throws InvalidUniqueDataException {
        try {
            ResponseWorkplaceDTO workplace = restService.save(dto);
            return ResponseEntity
                    .created(uri
                            .replacePath("/office-api/Workplaces/{WorkplaceId}")
                            .build(Map.of("WorkplaceId", workplace.getId())))
                    .body(workplace);
        } catch (Exception e) {
            throw new InvalidUniqueDataException(e.getMessage());
        }
    }

}

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
import crm.example.models.entities.emplyee.dto.CreateEmployeeDTO;
import crm.example.models.entities.emplyee.dto.ResponseEmployeeDTO;
import crm.example.services.imp.EmployeeRestService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/office-api/employees")
public class EmployeeRestController extends AbstractRestController<ResponseEmployeeDTO, CreateEmployeeDTO>{


    @Autowired
    public EmployeeRestController(EmployeeRestService restService) {
        super(restService);
    }

    @PostMapping
    @Override
    protected ResponseEntity<ResponseEmployeeDTO> save(@Valid @RequestBody CreateEmployeeDTO dto, UriComponentsBuilder uri)
            throws InvalidUniqueDataException {
        try {
            ResponseEmployeeDTO employee = (ResponseEmployeeDTO) restService.save(dto);
            return ResponseEntity
                    .created(uri
                            .replacePath("/office-api/employees/{employeeId}")
                            .build(Map.of("employeeId", employee.getId())))
                    .body(employee);
        } catch (Exception e) {
            throw new InvalidUniqueDataException(e.getMessage());
        }
    }
}

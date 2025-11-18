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
import crm.example.study.model.employees.Employee;
import crm.example.study.model.employees.DTO.EmployeeDTO;
import crm.example.study.model.employees.DTO.ResponseEmployeeDTO;
import crm.example.study.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/office-api/employees")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<ResponseEmployeeDTO>> getAllEmployees() {
        List<ResponseEmployeeDTO> employees = employeeService.getAllResponseEmployeeDTO();
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody EmployeeDTO dto,
            UriComponentsBuilder uriComponentsBuilder) throws InvalidUniqueDataException{
        try {
            Employee employee = employeeService.saveEmployee(dto);
            return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("/office-api/employees/{employeeId}")
                        .build(Map.of("employeeId", employee.getId())))
                .body(employee);
        } catch (Exception e) {
            throw new InvalidUniqueDataException(e.getMessage());
        }
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEmployeeDTO> getResponseEmployeeDTOById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getResponseEmployeeDTOById(id));
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeDTO dto){
        employeeService.updateEmployee(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployqqById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

}

package crm.example.services.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import crm.example.exceptions.InvalidUniqueDataException;
import crm.example.models.entities.emplyee.Employee;
import crm.example.models.entities.emplyee.dto.CreateEmployeeDTO;
import crm.example.models.entities.emplyee.dto.ResponseEmployeeDTO;
import crm.example.repositories.employee.EmployeeRepository;
import crm.example.repositories.employee.RoleRepository;
import crm.example.services.RestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeRestService implements RestService<ResponseEmployeeDTO, CreateEmployeeDTO>{

    private final EmployeeRepository employeeRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ResponseEmployeeDTO> getAll() {
        List<ResponseEmployeeDTO> employeeDTOs = new ArrayList<>();
        employeeRepo.findAll().stream().forEach(emp -> employeeDTOs.add(new ResponseEmployeeDTO(emp)));
        return employeeDTOs;
    }

    @Override
    @Transactional
    public ResponseEmployeeDTO save(CreateEmployeeDTO dto) throws InvalidUniqueDataException {
        if (employeeRepo.findByUsername(dto.getUsername()) != null) {
            throw new InvalidUniqueDataException("Неверное имя пользователя!");
        }
        if (dto.getRole() == null) {
            dto.setRole("USER");
        }
        Employee employee = new Employee(
                null,
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getName(),
                dto.getSurname(),
                roleRepository.findByRole(dto.getRole()).orElseThrow(),
                null, null, true, null, null, null);
        employeeRepo.save(employee);
        return new ResponseEmployeeDTO(employee);
    }

    @Override
    public ResponseEmployeeDTO getById(Long id) {
        return new ResponseEmployeeDTO(employeeRepo.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void update(CreateEmployeeDTO dto) {
        Employee employee = employeeRepo.findById(dto.getId()).orElseThrow();
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurname());
        employee.setRole(roleRepository.findByRole(dto.getRole()).orElseThrow());
        employeeRepo.save(employee);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        employeeRepo.deleteById(id);
    }
}

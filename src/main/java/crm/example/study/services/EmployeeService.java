package crm.example.study.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import crm.example.study.exceptions.InvalidUsernameException;
import crm.example.study.model.Employee;
import crm.example.study.model.DTO.EmployeeDTO;
import crm.example.study.repositories.EmployeeRepository;
import crm.example.study.repositories.RoleRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository EmployeeRepository, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.employeeRepo = EmployeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveEmployee(EmployeeDTO employeeDTO) throws InvalidUsernameException {
        if (employeeRepo.findByUsername(employeeDTO.getUsername()) != null)
            throw new InvalidUsernameException("Неверное имя пользователя!");
        if (employeeDTO.getRole() == null)
            employeeDTO.setRole("USER");
        Employee employee = new Employee(
                null,
                employeeDTO.getUsername(),
                passwordEncoder.encode(employeeDTO.getPassword()),
                employeeDTO.getName(),
                employeeDTO.getSurname(),
                roleRepository.findByRole(employeeDTO.getRole()),
                null, null, true, null, null);
        employeeRepo.save(employee);
    }

    public void updateEmployee(EmployeeDTO employeeDTO) throws InvalidUsernameException {
        Employee employee = employeeRepo.findEmployeeById(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employeeRepo.save(employee);
    }

    public Iterable<Employee> findAll() {
        return employeeRepo.findAll();
    }

    public EmployeeDTO findEmployeeById(Long id) {
        return new EmployeeDTO(employeeRepo.findEmployeeById(id));
    }

    public void deleteEmployeeById(Long id) {
        employeeRepo.deleteById(id);
    }

}

package crm.example.study.services;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import crm.example.study.exceptions.IlligalPasswordException;
import crm.example.study.exceptions.InvalidUsernameException;
import crm.example.study.model.Employee;
import crm.example.study.model.DTO.ChangePasswordDTO;
import crm.example.study.model.DTO.EmployeeDTO;
import crm.example.study.model.DTO.EmployeePasswordDTO;
import crm.example.study.repositories.EmployeeRepository;
import crm.example.study.repositories.RoleRepository;
import jakarta.transaction.Transactional;

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

    @Transactional
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

    @Transactional
    public void updateEmployee(EmployeeDTO employeeDTO) throws InvalidUsernameException {
        Employee employee = employeeRepo.findEmployeeById(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employee.setRole(roleRepository.findByRole(employeeDTO.getRole()));
        employeeRepo.save(employee);
    }

    @Transactional
    public void resetEmployeePassword(EmployeePasswordDTO dto){
        Employee employee = employeeRepo.findEmployeeById(dto.getId());
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employeeRepo.save(employee);
    }
    @Transactional
    public void changeEmployeePassword(Employee employee, ChangePasswordDTO dto) throws IlligalPasswordException{
        if (!passwordEncoder.matches(dto.getOldPass(), employee.getPassword()))
            throw new IlligalPasswordException("Неверный пароль!");
        if (passwordEncoder.matches(dto.getNewPass(), employee.getPassword()))
            throw new IlligalPasswordException("Пароли не должны совпадать!");
        employee.setPassword(passwordEncoder.encode(dto.getNewPass()));
        employeeRepo.save(employee);
    }

    public Iterable<Employee> findAll() {
        return employeeRepo.findAll();
    }

    public EmployeeDTO findEmployeeById(Long id) {
        return new EmployeeDTO(employeeRepo.findEmployeeById(id));
    }

    @Transactional
    public void deleteEmployeeById(Long id) {
        employeeRepo.deleteById(id);
    }

    public EmployeeDTO getAuthProfile(Employee employee) {
        return new EmployeeDTO(employee);
    }

    @Transactional
    public void setEmployeeActive(Long id) {
        Employee employee = employeeRepo.findEmployeeById(id);
        System.out.println(employee.isActive());
        employee.setActive(!employee.isActive());
        System.out.println(employee.isActive());

        employeeRepo.save(employee);
    }

}

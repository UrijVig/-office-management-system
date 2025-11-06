package crm.example.study.services;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import crm.example.study.exceptions.IllegalPasswordException;
import crm.example.study.exceptions.InvalidUsernameException;
import crm.example.study.model.Employee;
import crm.example.study.model.DTO.ChangePasswordDTO;
import crm.example.study.model.DTO.EmployeeDTO;
import crm.example.study.model.DTO.EmployeePasswordDTO;
import crm.example.study.repositories.EmployeeRepository;
import crm.example.study.repositories.RoleRepository;
import jakarta.transaction.Transactional;

/**
 * Сервис для управления сотрудниками и их учетными данными.
 * Обеспечивает бизнес-логику для операций с сотрудниками, включая создание,
 * обновление, управление паролями и изменение статуса активности.
 */
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор с внедрением зависимостей репозиториев и кодировщика паролей.
     * 
     * @param employeeRepository репозиторий для работы с сотрудниками
     * @param passwordEncoder    кодировщик паролей для безопасного хранения
     * @param roleRepository     репозиторий для работы с ролями
     */
    public EmployeeService(EmployeeRepository EmployeeRepository, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.employeeRepo = EmployeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Сохраняет нового сотрудника в системе.
     * Проверяет уникальность имени пользователя и устанавливает роль по умолчанию.
     * 
     * @param employeeDTO DTO с данными нового сотрудника
     * @throws InvalidUsernameException если имя пользователя уже существует
     */
    @Transactional
    public void saveEmployee(EmployeeDTO employeeDTO) throws InvalidUsernameException {
        if (employeeRepo.findByUsername(employeeDTO.getUsername()) != null) {
            throw new InvalidUsernameException("Неверное имя пользователя!");
        }
        if (employeeDTO.getRole() == null) {
            employeeDTO.setRole("USER");
        }
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

    /**
     * Обновляет данные существующего сотрудника.
     * 
     * @param employeeDTO DTO с обновленными данными сотрудника
     * @throws InvalidUsernameException если возникает ошибка при обновлении
     */
    @Transactional
    public void updateEmployee(EmployeeDTO employeeDTO) throws InvalidUsernameException {
        Employee employee = employeeRepo.findEmployeeById(employeeDTO.getId()).orElseThrow();
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employee.setRole(roleRepository.findByRole(employeeDTO.getRole()));
        employeeRepo.save(employee);
    }

    /**
     * Сбрасывает пароль сотрудника (административная функция).
     * 
     * @param dto DTO с идентификатором сотрудника и новым паролем
     */
    @Transactional
    public void resetEmployeePassword(EmployeePasswordDTO dto) {
        Employee employee = employeeRepo.findEmployeeById(dto.getId()).orElseThrow();
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employee.setPasswordUpdatedAt(LocalDateTime.now());
        employeeRepo.save(employee);
    }

    /**
     * Изменяет пароль сотрудника с проверкой старого пароля.
     * 
     * @param employee текущий аутентифицированный сотрудник
     * @param dto      DTO со старым и новым паролем
     * @throws IllegalPasswordException если старый пароль неверен или новый пароль
     *                                  совпадает со старым
     */
    @Transactional
    public void changeEmployeePassword(Employee employee, ChangePasswordDTO dto) throws IllegalPasswordException {
        if (!passwordEncoder.matches(dto.getOldPass(), employee.getPassword())) {
            throw new IllegalPasswordException("Неверный пароль!");
        }
        if (passwordEncoder.matches(dto.getNewPass(), employee.getPassword())) {
            throw new IllegalPasswordException("Новый пароль, не должен совпадать со старым!");
        }
        employee.setPassword(passwordEncoder.encode(dto.getNewPass()));
        employee.setPasswordUpdatedAt(LocalDateTime.now());
        employeeRepo.save(employee);
    }

    /**
     * Возвращает всех сотрудников системы.
     * 
     * @return итерируемая коллекция сотрудников
     */
    public Iterable<Employee> findAll() {
        return employeeRepo.findAll();
    }

    /**
     * Находит сотрудника по идентификатору и возвращает в виде DTO.
     * 
     * @param id идентификатор сотрудника
     * @return DTO с данными сотрудника
     */
    public EmployeeDTO getEmployeeDTOById(Long id) {
        return new EmployeeDTO(employeeRepo.findEmployeeById(id).orElseThrow());
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepo.findEmployeeById(id).orElseThrow();
    }

    /**
     * Удаляет сотрудника по идентификатору.
     * 
     * @param id идентификатор сотрудника для удаления
     */
    @Transactional
    public void deleteEmployeeById(Long id) {
        employeeRepo.deleteById(id);
    }

    /**
     * Преобразует сущность сотрудника в DTO для аутентифицированного пользователя.
     * 
     * @param employee сущность сотрудника
     * @return DTO с данными сотрудника
     */
    public EmployeeDTO getAuthProfile(Employee employee) {
        return new EmployeeDTO(employee);
    }

    /**
     * Изменяет статус активности учетной записи сотрудника.
     * 
     * @param id идентификатор сотрудника
     */
    @Transactional
    public void setEmployeeActive(Long id) {
        Employee employee = employeeRepo.findEmployeeById(id).orElseThrow();
        employee.setActive(!employee.isActive());
        employeeRepo.save(employee);
    }

}

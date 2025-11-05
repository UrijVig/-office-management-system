package crm.example.study.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.Employee;

/**
 * Репозиторий для работы с сущностями Employee в базе данных.
 * Предоставляет методы для поиска и управления данными сотрудников.
 * Наследует базовые CRUD операции из CrudRepository.
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    /**
     * Находит сотрудника по его идентификатору.
     * 
     * @param id идентификатор сотрудника
     * @return сущность Employee или null, если сотрудник не найден
     */
    public Optional<Employee> findEmployeeById(Long id);

    /**
     * Удаляет сотрудника по его идентификатору.
     * 
     * @param id идентификатор сотрудника для удаления
     */
    public void removeEmployeeById(Long id);

    /**
     * Находит сотрудника по имени пользователя.
     * Используется для аутентификации в Spring Security.
     * 
     * @param username имя пользователя для поиска
     * @return сущность Employee или null, если сотрудник не найден
     */
    public Employee findByUsername(String username);
}

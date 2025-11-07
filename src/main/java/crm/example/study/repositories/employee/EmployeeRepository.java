package crm.example.study.repositories.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.employees.Employee;

/**
 * Репозиторий для работы с сущностями Employee в базе данных.
 * Предоставляет методы для поиска и управления данными сотрудников.
 * Наследует базовые CRUD операции из CrudRepository.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Находит сотрудника по имени пользователя.
     * Используется для аутентификации в Spring Security.
     * 
     * @param username имя пользователя для поиска
     * @return сущность Employee или null, если сотрудник не найден
     */
    public Employee findByUsername(String username);
}

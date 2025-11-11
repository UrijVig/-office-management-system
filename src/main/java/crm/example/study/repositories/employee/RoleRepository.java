package crm.example.study.repositories.employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.employees.Role;

/**
 * Репозиторий для работы с сущностями Role в базе данных.
 * Предоставляет методы для поиска ролей пользователей.
 * Наследует базовые CRUD операции из CrudRepository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Находит роль по ее названию.
     * Используется для назначения ролей сотрудникам при регистрации и обновлении.
     * 
     * @param role название роли для поиска (например, "ADMIN", "USER", "MANAGER")
     * @return сущность Role или null, если роль не найдена
     */
    Optional<Role> findByRole(String role);

}

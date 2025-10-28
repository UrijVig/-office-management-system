package crm.example.study.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import crm.example.study.model.Role;

/**
 * Репозиторий для работы с сущностями Role в базе данных.
 * Предоставляет методы для поиска ролей пользователей.
 * Наследует базовые CRUD операции из CrudRepository.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    /**
     * Находит роль по ее названию.
     * Используется для назначения ролей сотрудникам при регистрации и обновлении.
     * 
     * @param role название роли для поиска (например, "ADMIN", "USER", "MANAGER")
     * @return сущность Role или null, если роль не найдена
     */
    Role findByRole(String role);

}

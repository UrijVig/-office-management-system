package crm.example.models.entities.emplyee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность, представляющая роль сотрудника в системе.
 * Роли используются для определения уровня доступа сотрудников.
 * Каждая роль связана с одним или несколькими сотрудниками через отношение
 * "многие-к-одному".
 */
@Data
@Entity(name = "roles")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
}

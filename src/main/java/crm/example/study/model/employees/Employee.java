package crm.example.study.model.employees;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность, представляющая сотрудника в системе.
 * Реализует интерфейс UserDetails для интеграции со Spring Security.
 * Содержит информацию об учётной записи сотрудника.
 */
@Data
@Entity(name = "employees")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    private LocalDateTime deactivatedAt;
    private LocalDateTime passwordUpdatedAt;
    // private Workplace workplace;


    /**
     * Метод для добавления времени создания УЗ в БД.
     * Вызывается перед сохранением сущности в базе.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Метод для добавления времени редактирования УЗ в БД.
     * Вызывается перед обновлением сущности в базе.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Возвращает уровень доступа пользователя на основе его роли.
     * 
     * @return коллекция authorities, начинающихся с префикса "ROLE_"
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
    }

    /**
     * Возвращает имя пользователя для входа в систему
     * 
     * @return имя пользователя
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Показывает, не истек ли срок действия учетной записи.
     * 
     * @return true если учетная запись активна, false в противном случае
     */
    public boolean isAccountNonExpired() {
        return active;
    }

    /**
     * Показывает, не заблокирована ли учетная запись.
     * 
     * @return true если учетная запись активна, false в противном случае
     */
    public boolean isAccountNonLocked() {
        return active;
    }

    /**
     * Показывает, не истек ли срок действия учетных данных.
     * 
     * @return true если учетная запись активна, false в противном случае
     */
    public boolean isCredentialsNonExpired() {
        return active;
    }

    /**
     * Показывает, активна ли учетная запись.
     * 
     * @return true если учетная запись активна, false в противном случае
     */
    public boolean isEnabled() {
        return active;
    }
}

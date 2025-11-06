package crm.example.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import crm.example.study.model.employees.Employee;
import crm.example.study.repositories.EmployeeRepository;

/**
 * Конфигурационный класс для настройки безопасности приложения Spring Security.
 * Определяет правила аутентификации, авторизации и настройки доступа к
 * ресурсам.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Создает и настраивает кодировщик паролей для приложения.
     * Использует алгоритм BCrypt для безопасного хеширования паролей.
     * 
     * @return экземпляр PasswordEncoder с алгоритмом BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создает сервис для загрузки данных пользователя из базы данных.
     * Использует EmployeeRepository для поиска сотрудников по имени пользователя.
     * 
     * @param employeeRepository репозиторий для работы с сущностями Employee
     * @return реализацию UserDetailsService для аутентификации сотрудников
     */
    @Bean
    public UserDetailsService userDetailsService(EmployeeRepository employeeRepository) {
        return username -> {
            Employee employee = employeeRepository.findByUsername(username);
            if (employee != null)
                return employee;

            throw new UsernameNotFoundException(username + " not found!");
        };
    }

    /**
     * Настраивает цепочку фильтров безопасности HTTP.
     * Определяет правила доступа к URL, настройки формы входа и выхода.
     * 
     * @param http объект HttpSecurity для настройки безопасности
     * @return сконфигурированную цепочку фильтров безопасности
     * @throws Exception если произошла ошибка при настройке безопасности
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/h2" , "/login", "/register", "/css/**", "/js/**", "/error").permitAll()
                .requestMatchers("/employees/**").hasAnyRole("ADMIN", "MANAGER")
                .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .build();
    }
}

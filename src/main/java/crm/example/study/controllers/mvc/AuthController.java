package crm.example.study.controllers.mvc;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import crm.example.study.model.employees.Employee;

/**
 * Контроллер для обработки запросов аутентификации и авторизации.
 * Управляет процессом входа в систему и перенаправлением аутентифицированных
 * пользователей.
 */
@Controller
public class AuthController {

    /**
     * Обрабатывает запрос на страницу входа в систему.
     * Если пользователь уже аутентифицирован, перенаправляет его на домашнюю
     * страницу.
     * В противном случае отображает страницу входа.
     * 
     * @param employee текущий аутентифицированный сотрудник (может быть null)
     * @return имя представления для страницы входа или редирект на домашнюю
     *         страницу
     */
    @GetMapping("/login")
    public String login(@AuthenticationPrincipal Employee employee) {
        if (employee != null) {
            return "redirect:home";
        }
        return "employees/login";
    }

}

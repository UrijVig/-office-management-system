package crm.example.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import crm.example.study.model.employees.Employee;
import crm.example.study.model.employees.DTO.ChangePasswordDTO;
import crm.example.study.services.EmployeeService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

/**
 * Контроллер для управления персональными данными и настройками пользователя.
 * Обеспечивает функциональность для просмотра профиля и изменения пароля.
 * Доступ к методам ограничен аутентифицированными пользователями.
 */
@Controller
@RequestMapping("/personal")
@EnableMethodSecurity(prePostEnabled = true)
public class ProfileController {

    private final EmployeeService employeeService;

    /**
     * Конструктор с внедрением зависимости сервиса сотрудников.
     * 
     * @param employeeService сервис для работы с данными сотрудников
     */
    @Autowired
    public ProfileController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Отображает персональную страницу профиля текущего пользователя.
     * 
     * @param employee текущий аутентифицированный сотрудник
     * @param model    модель для передачи данных в представление
     * @return имя представления персонального профиля
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String getPersonalPage(@AuthenticationPrincipal Employee employee,
            Model model) {
        model.addAttribute("employee", employeeService.getAuthProfile(employee));
        return "personal_profile";
    }

    /**
     * Отображает форму для изменения пароля текущего пользователя.
     * 
     * @param employee текущий аутентифицированный сотрудник
     * @param model    модель для передачи данных в представление
     * @return имя представления формы изменения пароля
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/password")
    public String getPasswordChangeForm(@AuthenticationPrincipal Employee employee,
            Model model) {
        model.addAttribute("password", new ChangePasswordDTO());
        return "employees/pass_change_form";
    }

    /**
     * Обрабатывает запрос на изменение пароля текущего пользователя.
     * Выполняет валидацию данных и проверку старого пароля.
     * 
     * @param employee      текущий аутентифицированный сотрудник
     * @param dto           DTO с данными для изменения пароля
     * @param bindingResult объект для валидации данных формы
     * @param model         модель для передачи данных в представление
     * @return редирект на страницу профиля или возврат к форме при ошибках
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/password")
    public String postMethodName(@AuthenticationPrincipal Employee employee,
            @Valid @ModelAttribute("password") ChangePasswordDTO dto,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("password", dto);
            return "pass_change_form";
        }
        try {
            employeeService.changeEmployeePassword(employee, dto);
        } catch (Exception e) {
            bindingResult.rejectValue("oldPass", "error.password", e.getMessage());
            model.addAttribute("password", dto);
            return "employees/pass_change_form";
        }

        return "redirect:/personal/profile";
    }

}

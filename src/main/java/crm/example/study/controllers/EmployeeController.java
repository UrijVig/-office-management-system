package crm.example.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import crm.example.study.model.employees.DTO.EmployeeDTO;
import crm.example.study.model.employees.DTO.EmployeePasswordDTO;
import crm.example.study.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * Контроллер для управления сотрудниками в системе.
 * Обеспечивает CRUD операции, управление паролями и активностью учетных
 * записей.
 * Доступ к методам ограничен ролями ADMIN и MANAGER.
 */
@Controller
@RequestMapping("/employees")
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     * Конструктор с внедрением зависимости сервиса сотрудников.
     * 
     * @param employeeService сервис для работы с данными сотрудников
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Отображает список всех сотрудников.
     * 
     * @param model модель для передачи данных в представление
     * @return имя представления со списком сотрудников
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public String showAllEmployees(Model model) {
        log.debug("Запрос списка пользователей");
        model.addAttribute("employees", employeeService.findAll());
        return "employees/employees_list";
    }

    /**
     * Отображает форму для создания нового сотрудника.
     * 
     * @param model модель для передачи данных в представление
     * @return имя представления формы создания сотрудника
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/create")
    public String createEmployee(Model model) {
        log.debug("Запрос на создание пользователя");
        model.addAttribute("employee", new EmployeeDTO());
        return "employees/employee_create_form";
    }

    /**
     * Сохраняет нового сотрудника в системе.
     * 
     * @param employeeDTO   DTO с данными сотрудника
     * @param bindingResult объект для валидации данных формы
     * @param model         модель для передачи данных в представление
     * @return редирект на список сотрудников или возврат к форме при ошибках
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employeeDTO);
            log.debug("ошибка заолнения формы при создании сотрудника {}", employeeDTO.getUsername());
            return "employees/employee_create_form";
        }
        try {
            log.info("Попытка сохранить пользователя {}", employeeDTO.getUsername());
            employeeService.saveEmployee(employeeDTO);
        } catch (Exception e) {
            log.error("Ошибка при сохранении пользователя {}", e.getMessage());
            bindingResult.rejectValue("username", "error.employee", e.getMessage());
            model.addAttribute("employee", employeeDTO);
            return "employees/employee_create_form";
        }
        log.info("Пользователь {} сохранён успешно", employeeDTO.getUsername());
        return "redirect:/employees";
    }

    /**
     * Отображает форму для редактирования существующего сотрудника.
     * 
     * @param id    идентификатор сотрудника для редактирования
     * @param model модель для передачи данных в представление
     * @return имя представления формы редактирования сотрудника
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, Model model) {
        log.debug("Запрос на редактирование пользователя {}", id);
        model.addAttribute("employee", employeeService.getEmployeeDTOById(id));
        return "employees/employee_update_form";
    }

    /**
     * Обновляет данные существующего сотрудника.
     * 
     * @param employeeDTO   DTO с обновленными данными сотрудника
     * @param bindingResult объект для валидации данных формы
     * @param model         модель для передачи данных в представление
     * @return редирект на список сотрудников или возврат к форме при ошибках
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/update")
    public String updateEmployee(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employeeDTO);
            log.debug("ошибка заолнения формы при редактировании сотрудника {}", employeeDTO.getId());
            return "employees/employee_update_form";
        }
        try {
            log.info("Попытка сохранить изменения пользователя {}", employeeDTO.getId());
            employeeService.updateEmployee(employeeDTO);
        } catch (Exception e) {
            log.error("Ошибка при сохранении изменений пользователя {}", e.getMessage());
            bindingResult.rejectValue("username", "error.employee", e.getMessage());
            model.addAttribute("employee", employeeDTO);
            return "employees/employee_update_form";
        }
        log.info("Изменения пользователя {} сохранениы успешно", employeeDTO.getId());
        return "redirect:/employees";
    }

    /**
     * Удаляет сотрудника по идентификатору.
     * 
     * @param id    идентификатор сотрудника для удаления
     * @param model модель для передачи данных в представление
     * @return редирект на список сотрудников
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model) {
        log.info("Попытака удалёния пользователя: {}", id);
        employeeService.deleteEmployeeById(id);
        log.info("Пользователь {} удалён.", id);
        return "redirect:/employees";
    }

    /**
     * Отображает форму для сброса пароля сотрудника.
     * 
     * @param id    идентификатор сотрудника для сброса пароля
     * @param model модель для передачи данных в представление
     * @return имя представления формы сброса пароля
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/update/{id}/password")
    public String getPasswordResetForm(@PathVariable Long id, Model model) {
        log.debug("Запрос на изменение пароля пользователя {}", id);
        model.addAttribute("employee", employeeService.getEmployeeDTOById(id));
        return "employees/pass_reset_form";
    }

    /**
     * Сбрасывает пароль сотрудника.
     * 
     * @param id                  идентификатор сотрудника
     * @param employeePasswordDTO DTO с новым паролем
     * @param bindingResult       объект для валидации данных формы
     * @param model               модель для передачи данных в представление
     * @return редирект на список сотрудников или возврат к форме при ошибках
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/update/{id}/password")
    public String resetEmployeePassword(@PathVariable Long id,
            @Valid @ModelAttribute("employee") EmployeePasswordDTO employeePasswordDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            log.debug("Ошибка при заполнении формы изменения пароля для {}", id);
            model.addAttribute("employee", employeePasswordDTO);
            return "employees/pass_reset_form";
        }
        log.info("Попытка изменения пароля для {}", id);
        employeeService.resetEmployeePassword(employeePasswordDTO);
        log.info("Пароль для {} успешно изменён", id);
        return "redirect:/employees";
    }

    /**
     * Изменяет активность учетной записи сотрудника.
     * 
     * @param id идентификатор сотрудника
     * @return редирект на список сотрудников
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/setactive/{id}")
    public String setEmployeeActive(@PathVariable Long id) {
        log.info("Изменение активноусти УЗ пользователя {}", id);
        employeeService.setEmployeeActive(id);
        log.info("Активность УЗ пользователя {} успешно изменена", id);
        return "redirect:/employees";
    }
    /**
     * Отображает страницу профиля сотрудника
     * 
     * @param id идентификатор сотрудника
     * @param model модель для передачи данных в представление
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("{id}")
    public String getEmployeeProfile(@PathVariable Long id, Model model) {
        log.info("Запрос на просмотр профиля пользователя: {}", id);
        model.addAttribute("employee", employeeService.findEmployeeById(id));
        return "employees/employee_profile";
    }
    

}

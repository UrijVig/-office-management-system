package crm.example.study.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки запросов главной страницы приложения.
 * Предоставляет базовые эндпоинты для навигации по сайту.
 */
@Controller
public class HomeController {
    /**
     * Обрабатывает запрос на главную страницу приложения.
     * Возвращает представление домашней страницы.
     * 
     * @return имя представления домашней страницы
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

}

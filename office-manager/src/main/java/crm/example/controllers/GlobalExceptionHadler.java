package crm.example.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;

/**
 * Глобальный обработчик исключений для всего приложения.
 * Перехватывает и обрабатывает все необработанные исключения,
 * обеспечивая единообразное отображение ошибок пользователю.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHadler {

    /**
     * Обрабатывает все исключения типа Exception и его подклассы.
     * Логирует ошибку и возвращает страницу с сообщением об ошибке.
     * 
     * @param e     исключение, которое было выброшено
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения ошибки
     */
    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e, Model model) {
        log.error("Обнаружена ошибка! {}", e.getMessage(), e);
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}

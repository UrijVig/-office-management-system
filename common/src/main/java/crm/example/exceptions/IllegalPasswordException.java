package crm.example.exceptions;

/**
 * Исключение, выбрасываемое при попытке установки некорректного пароля.
 * Используется для валидации паролей, которые не соответствуют требованиям
 * безопасности
 * или бизнес-правилам приложения.
 */
public class IllegalPasswordException  extends Exception {

    /**
     * Создает новое исключение с указанным сообщением об ошибке.
     * 
     * @param message детальное сообщение об ошибке, описывающее причину исключения
     */
    public IllegalPasswordException (String message) {
        super(message);
    }
}

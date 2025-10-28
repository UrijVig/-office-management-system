package crm.example.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения, содержащий точку входа.
 * Использует аннотацию @SpringBootApplication для автоматической настройки
 * Spring Boot приложения, включая компонентное сканирование, автоконфигурацию
 * и возможность определения дополнительных бинов в конфигурации.
 */
@SpringBootApplication
public class StudyApplication {
	/**
	 * Точка входа в приложение Spring Boot.
	 * Запускает встроенный контейнер сервлетов, настраивает контекст приложения
	 * и выполняет автоматическую настройку всех компонентов.
	 * 
	 * @param args аргументы командной строки, переданные при запуске приложения
	 */
	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
	}

}

# Архитектура приложения

    Данный файл описывает структуру приложения, предназначение файлов их базовый функционал. Подробности будут описаны в коментариях методов/классов. 

- [src\main](/src/main)
    - [\java\crm\example\study](/src/main/java/crm/example/study) - хранение основных файлов программы
        - [\config](/src/main/java/crm/example/study/config) - файлы конфигурации контекста spring
            - [securityConfig.java](/src/main/java/crm/example/study/config/securityConfig.java) - конфигурация spring security
        - [\controllers](/src/main/java/crm/example/study/controllers) - контроллеры программы
            - [AuthController.java](/src/main/java/crm/example/study/controllers/AuthController.java) - контроллер аутентификации
            - [EmployeeController.java](/src/main/java/crm/example/study/controllers/EmployeeController.java) - Управление запросами, относящимися к сотрудникам 
            - [HomeController.java](/src/main/java/crm/example/study/controllers/HomeController.java) - базовый контроллер "заглушка"
        - [\model](/src/main/java/crm/example/study/model) - ханение объектов программы (Entity)
            - [\DTO](/src/main/java/crm/example/study/model/DTO) - хранение dto объектов
                - [EmployeeDTO.java](/src/main/java/crm/example/study/model/DTO/EmployeeDTO.java) - dto сотрудник
            - [Employee.java](/src/main/java/crm/example/study/model/Employee.java) - Entity сотрудник
        - [\repositories](/src/main/java/crm/example/study/repositories) - хранение репозиториев объектов
            - [EmployeeRepository.java](/src/main/java/crm/example/study/repositories/EmployeeRepository.java) - репозиторий сотрудника
        - [\services](/src/main/java/crm/example/study/services) - вспомогательные сервисы
            - [EmployeeService.java](/src/main/java/crm/example/study/services/EmployeeService.java) - сервис для объектов сотрудника
        - [StudyApplication.java](/src/main/java/crm/example/study/StudyApplication.java) - точка входа в программу
    - [\resources](/src/main/resources) - ресурсы программы
        - [\db\migration](/src/main/resources/db/migration) - хранение миграций
            - [V1__create_users_table.sql](/src/main/resources/db/migration/V1__create_users_table.sql) - создание таблицы сотрудников
            - [V2__insert_data_in_user.sql](/src/main/resources/db/migration/V2__insert_data_in_user.sql) - заполнение таблицы сотрудников начальными данными
        - [\templates](/src/main/resources/templates) - хранение html страниц (шаблонов)
            - [employee_create_form.html](/src/main/resources/templates/employee_create_form.html) - форма создания сотрудника
            - [employee_update_form.html](/src/main/resources/templates/employee_update_form.html) - форма редактирования УЗ сотрудника
            - [employees_list.html](/src/main/resources/templates/employees_list.html) - список сотрудников
            - [error.html](/src/main/resources/templates/error.html) - страница отображения ошибок
            - [home.html](/src/main/resources/templates/home.html) - домашняя страница приложения
            - [index.html](/src/main/resources/templates/index.html) - страница заглушка
            - [login.html](/src/main/resources/templates/login.html) - страница аутентификации пользователей
        - [application.yaml(/src/main/resources/application.yaml)] - файл настроек зависимостей и старта программы

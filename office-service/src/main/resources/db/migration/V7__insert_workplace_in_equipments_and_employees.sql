-- Рабочее место системного администратора
UPDATE equipments SET workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-ADMIN-001')
WHERE serial_number IN (
    'MON001-DELL-2024',  -- Монитор
    'SYS001-DELL-2024',  -- Системный блок
    'KB001-LOGITECH-2024', -- Клавиатура
    'MSE001-LOGITECH-2024', -- Мышь
    'HP001-SONY-2024',   -- Наушники
    'SPK001-LOGITECH-2024'  -- Колонки
);
update employees set workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-ADMIN-001')
where username = 'admin'; -- закрепить пк за администратором

-- Рабочее место менеджера 1
UPDATE equipments SET workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-MGR-001')
WHERE serial_number IN (
    'MON002-HP-2024',
    'SYS002-HP-2024',
    'KB002-MICROSOFT-2024',
    'MSE002-MICROSOFT-2024',
    'MFP001-CANON-2024'
);
update employees set workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-MGR-001')
where username = 'manager1'; -- закрепить пк за менеджером

-- Рабочее место менеджера 2
UPDATE equipments SET workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-MGR-002')
WHERE serial_number IN (
    'MON003-LG-2023',
    'SYS003-LENOVO-2023',
    'KB003-CHERRY-2023',
    'MSE003-RAZER-2023',
    'SCN001-CANON-2024'
);
update employees set workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-MGR-002')
where username = 'manager2'; -- закрепить пк за менеджером 2

-- Рабочее место разработчика (user1)
UPDATE equipments SET workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-USR-001')
WHERE serial_number IN (
    'MON004-SAMSUNG-2024',
    'KB004-GENIUS-2024',
    'MSE004-GENIUS-2024',
    'HP002-JBL-2024'
);
update employees set workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-USR-001')
where username = 'user1'; -- закрепить пк за пользователем 1

-- Рабочее место тестировщика (user2)
UPDATE equipments SET workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-USR-002')
WHERE serial_number IN (
    'MON005-DELL-2023',
    'KB005-RAZER-2024',
    'MSE005-A4TECH-2024',
    'SPK002-CREATIVE-2024'
);
update employees set workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-USR-002')
where username = 'user2'; -- закрепить пк за пользователем 2

-- Рабочее место дизайнера (user3)
UPDATE equipments SET workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-USR-003')
WHERE serial_number IN (
    'MON006-ASUS-2024',
    'MSE006-LOGITECH-2023',
    'SPK003-EDIFIER-2023',
    'LTP001-LENOVO-2024'
);
update employees set workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-USR-003')
where username = 'user3'; -- закрепить пк за пользователем 3

-- Рабочее место аналитика (user4)
UPDATE equipments SET workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-USR-004')
WHERE serial_number IN (
    'MON007-ACER-2023',
    'PRN001-BROTHER-2023'
);
update employees set workplace_id = (SELECT id FROM workplaces WHERE name = 'WORKPLACE-USR-004')
where username = 'user4'; -- закрепить пк за пользователем 4
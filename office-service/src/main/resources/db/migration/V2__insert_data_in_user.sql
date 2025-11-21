Delete from roles;
insert into roles (role) VALUES ('ADMIN'), ('MANAGER'),('USER');

Delete from employees;

-- Insert employees data
INSERT INTO employees (username, password, name, surname, role_id, created_at, updated_at, active, deactivated_at, password_updated_at) VALUES
-- Active admin user (password: admin123)
(
    'admin',
    '$2a$12$hWiDNGHIUvByjKoHa5Y8hOxhb0T8O3hpP8Nf2g4fHF7bNe6VtAc9S', -- bcrypt hash for 'admin123'
    'Иван',
    'Петров',
    (SELECT id FROM roles WHERE role = 'ADMIN' LIMIT 1),
    '2024-01-10 09:00:00',
    '2024-01-15 14:30:00',
    true,
    NULL,
    '2024-01-15 14:30:00'
),

-- Active manager users (password: manager123)
(
    'manager1',
    '$2a$12$hWiDNGHIUvByjKoHa5Y8hOxhb0T8O3hpP8Nf2g4fHF7bNe6VtAc9S',
    'Мария',
    'Сидорова',
    (SELECT id FROM roles WHERE role = 'MANAGER' LIMIT 1),
    '2024-01-11 10:15:00',
    '2024-01-14 11:20:00',
    true,
    NULL,
    '2024-01-14 11:20:00'
),
(
    'manager2',
    '$2a$12$hWiDNGHIUvByjKoHa5Y8hOxhb0T8O3hpP8Nf2g4fHF7bNe6VtAc9S',
    'Алексей',
    'Козлов',
    (SELECT id FROM roles WHERE role = 'MANAGER' LIMIT 1),
    '2024-01-12 08:45:00',
    NULL,
    true,
    NULL,
    '2024-01-12 08:45:00'
),

-- Active regular users (password: user123)
(
    'user1',
    '$2a$12$hWiDNGHIUvByjKoHa5Y8hOxhb0T8O3hpP8Nf2g4fHF7bNe6VtAc9S',
    'Екатерина',
    'Иванова',
    (SELECT id FROM roles WHERE role = 'USER' LIMIT 1),
    '2024-01-13 13:20:00',
    '2024-01-15 16:45:00',
    true,
    NULL,
    '2024-01-15 16:45:00'
),
(
    'user2',
    '$2a$12$hWiDNGHIUvByjKoHa5Y8hOxhb0T8O3hpP8Nf2g4fHF7bNe6VtAc9S',
    'Дмитрий',
    'Смирнов',
    (SELECT id FROM roles WHERE role = 'USER' LIMIT 1),
    '2024-01-13 14:30:00',
    NULL,
    true,
    NULL,
    '2024-01-13 14:30:00'
),
(
    'user3',
    '$2a$12$hWiDNGHIUvByjKoHa5Y8hOxhb0T8O3hpP8Nf2g4fHF7bNe6VtAc9S',
    'Ольга',
    'Кузнецова',
    (SELECT id FROM roles WHERE role = 'USER' LIMIT 1),
    '2024-01-14 09:15:00',
    '2024-01-15 10:30:00',
    true,
    NULL,
    '2024-01-15 10:30:00'
),
(
    'user4',
    '$2a$12$hWiDNGHIUvByjKoHa5Y8hOxhb0T8O3hpP8Nf2g4fHF7bNe6VtAc9S',
    'Сергей',
    'Попов',
    (SELECT id FROM roles WHERE role = 'USER' LIMIT 1),
    '2024-01-14 11:00:00',
    NULL,
    true,
    NULL,
    '2024-01-14 11:00:00'
),

-- Inactive/deactivated users
(
    'old_user',
    '$2a$12$hWiDNGHIUvByjKoHa5Y8hOxhb0T8O3hpP8Nf2g4fHF7bNe6VtAc9S',
    'Анна',
    'Федорова',
    (SELECT id FROM roles WHERE role = 'USER' LIMIT 1),
    '2023-12-01 10:00:00',
    '2024-01-10 15:00:00',
    false,
    '2024-01-10 15:00:00',
    '2024-01-10 15:00:00'
),

-- User with recently updated password (password: newpass123)
(
    'secure_user',
    '$2a$10$NEWHASHIJKLMNOPQRSTUVWXYZ0123456789ABCDEFGHIJKL',
    'Павел',
    'Николаев',
    (SELECT id FROM roles WHERE role = 'USER' LIMIT 1),
    '2024-01-05 12:00:00',
    '2024-01-16 08:15:00',
    true,
    NULL,
    '2024-01-16 08:15:00'
),

-- User without updates
(
    'simple_user',
    '$2a$12$hWiDNGHIUvByjKoHa5Y8hOxhb0T8O3hpP8Nf2g4fHF7bNe6VtAc9S',
    'Наталья',
    'Орлова',
    (SELECT id FROM roles WHERE role = 'USER' LIMIT 1),
    '2024-01-15 16:00:00',
    NULL,
    true,
    NULL,
    '2024-01-15 16:00:00'
);
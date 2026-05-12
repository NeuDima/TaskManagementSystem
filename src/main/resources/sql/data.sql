insert into roles(name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users(email, password, role_id)
values ('test@mail.ru', '{noop}123', 1),
       ('user@gmail.com', '{noop}123', 1),
       ('admin@mail.ru', '{noop}123', 2);

insert into task(title, description, status, priority, author_id, executor_id)
values ('task 1', 'description task 1', 'PENDING', 'MEDIUM', 1, 2),
       ('task 2', 'description task 2', 'IN_PROGRESS', 'MEDIUM', 2, 2),
       ('task 3', 'description task 3', 'COMPLETED', 'HIGH', 1, 1),
       ('task 4', 'description task 4', 'COMPLETED', 'HIGH', 2, 1),
       ('task 5', 'description task 5', 'IN_PROGRESS', 'LOW', 3, 1);

insert into comment(user_id, task_id, text)
values (1, 1, 'comment 1'),
       (1, 1, 'comment 2'),
       (2, 1, 'comment 3'),
       (2, 1, 'comment 4'),
       (3, 1, 'comment 5'),
       (1, 2, 'comment 6'),
       (1, 2, 'comment 7'),
       (2, 2, 'comment 8'),
       (2, 3, 'comment 9'),
       (3, 3, 'comment 10'),
       (1, 3, 'comment 11');


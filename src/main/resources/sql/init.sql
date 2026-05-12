create schema task_management;

create table if not exists task_management.roles
(
    id   serial primary key,
    name text not null unique
);

create table if not exists task_management.users
(
    id       serial primary key,
    email    text not null unique,
    password text not null,
    role_id  int references roles (id) on delete cascade
);

create table if not exists task_management.task
(
    id          serial primary key,
    title       text not null,
    description text not null,
    status      text not null CHECK (status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED')),
    priority    text not null CHECK (priority IN ('HIGH', 'MEDIUM', 'LOW')),
    author_id   int references users (id) on delete cascade,
    executor_id int references users (id) on delete cascade
);

create table if not exists task_management.comment
(
    id      serial primary key,
    user_id int references users (id) on delete cascade,
    task_id int references task (id) on delete cascade,
    text    text not null
);

create table deactivated_token
(
    id         uuid primary key,
    keep_until timestamp not null check ( keep_until > now() )
);
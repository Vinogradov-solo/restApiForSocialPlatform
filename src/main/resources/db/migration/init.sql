create table "user"
(
    id         bigserial primary key unique,
    username   varchar(36) not null unique,
    email      varchar(36) not null unique,
    password   varchar(80) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    CONSTRAINT id UNIQUE (id)
);

create table role
(
    id         bigserial primary key unique,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table user_role
(
    user_id bigserial not null references "user" (id),
    role_id bigserial not null references role (id),
    primary key (user_id, role_id)
);

create table post
(
    id         bigserial primary key unique,
    user_id    bigserial,
    title      varchar not null,
    text       varchar not null,
    image_path varchar not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table subscriptions
(
    id         bigserial primary key unique,
    user_id    bigserial references "user" (id),
    friend_id  bigserial references "user" (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table chat
(
    id           bigserial primary key unique,
    chat_path    varchar not null,
    sender_id    bigserial references "user" (id),
    recipient_id bigserial references "user" (id),
    created_at   timestamp default current_timestamp,
    updated_at   timestamp default current_timestamp
);

create table friendship
(
    id           bigserial primary key unique,
    sender_id    bigserial references "user" (id),
    recipient_id bigserial references "user" (id),
    created_at   timestamp default current_timestamp,
    updated_at   timestamp default current_timestamp
);

ALTER TABLE post
    ADD CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE subscriptions
    ADD CONSTRAINT FK_USER_ID_ON_SUB FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE subscriptions
    ADD CONSTRAINT FK_FRIEND_ID_ON_SUB FOREIGN KEY (friend_id) REFERENCES "user" (id);

ALTER TABLE chat
    ADD CONSTRAINT FK_SENDER_ID_ON_CHAT FOREIGN KEY (sender_id) REFERENCES "user" (id);

ALTER TABLE chat
    ADD CONSTRAINT FK_RECIPIENT_ID_ON_CHAT FOREIGN KEY (recipient_id) REFERENCES "user" (id);

ALTER TABLE friendship
    ADD CONSTRAINT FK_SENDER_ID_ON_FSH FOREIGN KEY (sender_id) REFERENCES "user" (id);

ALTER TABLE friendship
    ADD CONSTRAINT FK_RECIPIENT_ID_ON_FSH FOREIGN KEY (recipient_id) REFERENCES "user" (id);

insert into role (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into "user"(id, username, email, password)values (111, 'user', 'user', 'user'),(222, 'friend', 'friend', 'friend');
insert into user_role(user_id, role_id) values (111,1),(222,2);
insert into subscriptions (id, user_id, friend_id) values (1,111,222);
insert into subscriptions (id, user_id, friend_id) values (2,222,111);
insert into friendship (id, sender_id, recipient_id) values (1,111,222);
insert into post (id, user_id, title, text, image_path) VALUES (1,222,'title','text','resources\3A\00\1A6F4F95E5B9EEAE4B2F626C313C01D3\image.txt');
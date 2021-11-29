create extension if not exists "uuid-ossp";

create table accounts
(
    id        uuid      default uuid_generate_v4() not null,
    createdAt timestamp default now(),
    isDeleted boolean   default false,
    updatedAt timestamp default now(),
    domainId  uuid                                 not null,
    isActive  boolean   default true,
    password  text                                 not null,
    roles     text[],
    username  text                                 not null,
    primary key (id)
);

create table contacts
(
    id        uuid      default uuid_generate_v4() not null,
    createdAt timestamp default now(),
    isDeleted boolean   default false,
    updatedAt timestamp default now(),
    partnerId uuid,
    yourId    uuid,
    primary key (id)
);

create table domains
(
    id        uuid      default uuid_generate_v4() not null,
    createdAt timestamp default now(),
    isDeleted boolean   default false,
    updatedAt timestamp default now(),
    name      text                                 not null,
    primary key (id)
);

create table emails
(
    id        uuid      default uuid_generate_v4() not null,
    createdAt timestamp default now(),
    isDeleted boolean   default false,
    updatedAt timestamp default now(),
    accountId uuid                                 not null,
    fileName  text                                 not null,
    flag      text,
    folderId  uuid                                 not null,
    primary key (id)
);

create table folders
(
    id        uuid      default uuid_generate_v4() not null,
    createdAt timestamp default now(),
    isDeleted boolean   default false,
    updatedAt timestamp default now(),
    accountId uuid                                 not null,
    name      text,
    parentId  uuid,
    primary key (id)
);

create table messages
(
    id        uuid      default uuid_generate_v4() not null,
    createdAt timestamp default now(),
    isDeleted boolean   default false,
    updatedAt timestamp default now(),
    accountId uuid                                 not null,
    content   text,
    roomId    uuid                                 not null,
    threadId  uuid,
    type      text,
    primary key (id)
);

create table profiles
(
    id        uuid      default uuid_generate_v4() not null,
    createdAt timestamp default now(),
    isDeleted boolean   default false,
    updatedAt timestamp default now(),
    accountId uuid                                 not null,
    birthday  date,
    firstName text,
    lastName  text,
    location  text      default 'Danang/Vietnam',
    mobile    text,
    title     text,
    primary key (id)
);

create table reactions
(
    id        uuid      default uuid_generate_v4() not null,
    createdAt timestamp default now(),
    isDeleted boolean   default false,
    updatedAt timestamp default now(),
    accountId uuid                                 not null,
    code      text                                 not null,
    messageId uuid                                 not null,
    primary key (id)
);

create table room_accounts
(
    id        uuid      default uuid_generate_v4() not null,
    createdAt timestamp default now(),
    isDeleted boolean   default false,
    updatedAt timestamp default now(),
    accountId uuid                                 not null,
    roomId    uuid                                 not null,
    primary key (id)
);

create table rooms
(
    id          uuid      default uuid_generate_v4() not null,
    createdAt   timestamp default now(),
    isDeleted   boolean   default false,
    updatedAt   timestamp default now(),
    avatar      text,
    description text,
    name        text,
    type        text,
    primary key (id)
);

create table sessions
(
    id        uuid      default uuid_generate_v4() not null,
    createdAt timestamp default now(),
    isDeleted boolean   default false,
    updatedAt timestamp default now(),
    accountId uuid                                 not null,
    token     text                                 not null,
    primary key (id)
);

alter table accounts
    add constraint UK_k8h1bgqoplx0rkngj01pm1rgp unique (username);

alter table domains
    add constraint UK_tk9464twe96cda0qy7xej44bj unique (name);

alter table sessions
    add constraint UK_nr21vuswfbmr91q57xdbnou5f unique (token);
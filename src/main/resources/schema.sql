drop table IF EXISTS MPA CASCADE;

create table IF NOT EXISTS MPA
(
    MPA_MPA_ID INTEGER auto_increment,
    MPA_NAME   CHARACTER VARYING(10),
    constraint MPA_PK
        primary key (MPA_MPA_ID)
);

drop table IF EXISTS FILMS CASCADE;

create table FILMS
(
    FILM_ID           INTEGER auto_increment,
    FILM_NAME         CHARACTER VARYING(50)  not null,
    FILM_DESCRIPTION  CHARACTER VARYING(200) not null,
    FILM_RELEASE_DATE DATE                   not null,
    FILM_DURATION     INTEGER                not null,
    FILM_MPA          INTEGER                not null,
    constraint FILMS_PK
        primary key (FILM_ID),
    constraint FILMS_MPA_MPA_MPA_ID_FK
        foreign key (FILM_MPA) references MPA
);

drop table IF EXISTS GENRES CASCADE;

create table IF NOT EXISTS GENRES
(
    GENRES_GENRES_ID INTEGER auto_increment,
    GENRES_NAME      CHARACTER VARYING(15) not null,
    constraint GENRES_PK
        primary key (GENRES_GENRES_ID)
);

drop table IF EXISTS FILM_GENRES CASCADE;

create table IF NOT EXISTS FILM_GENRES
(
    FILM_GENRES_FILM_ID   INTEGER not null,
    FILM_GENRES_GENRES_ID INTEGER not null,
    constraint FILM_GENRES_FILMS_FILM_ID_FK
        foreign key (FILM_GENRES_FILM_ID) references FILMS,
    constraint FILM_GENRES_GENRES_GENRES_GENRES_ID_FK
        foreign key (FILM_GENRES_GENRES_ID) references GENRES
);

drop table IF EXISTS USERS CASCADE;

create table IF NOT EXISTS USERS
(
    USER_ID       INTEGER auto_increment,
    USER_EMAIL    CHARACTER VARYING(100) not null,
    USER_LOGIN    CHARACTER VARYING(30)  not null,
    USER_NAME     CHARACTER VARYING(30)  not null,
    USER_BIRTHDAY DATE                   not null,
    constraint USERS_PK
        primary key (USER_ID)
);

drop table IF EXISTS FILMS_LIKES CASCADE;

create table IF NOT EXISTS FILMS_LIKES
(
    FILMS_LIKES_ID                   INTEGER not null,
    FILMS_LIKES_USER_ID_WHO_LIKE_FILM INTEGER not null,
    constraint FILMS_LIKES_FILMS_FILM_ID_FK
        foreign key (FILMS_LIKES_ID) references FILMS,
    constraint FILMS_LIKES_USERS_USER_ID_FK
        foreign key (FILMS_LIKES_USER_ID_WHO_LIKE_FILM) references USERS
);


drop table IF EXISTS FRIENDSHIP CASCADE;

create table FRIENDSHIP
(
    FRIENDSHIP_USER_ID   INTEGER not null,
    FRIENDSHIP_FRIEND_ID INTEGER not null,
    constraint FRIENDSHIP_USERS_USER_ID_FK
        foreign key (FRIENDSHIP_USER_ID) references USERS,
    constraint FRIENDSHIP_USERS_USER_ID_FK_2
        foreign key (FRIENDSHIP_FRIEND_ID) references USERS
);
CREATE TABLE Users(
    id int,
    first_name varchar(255),
    last_name varchar(255),
    email varchar(255),
    password varchar(255),
    role varchar(255),
    PRIMARY KEY (id),
    UNIQUE (email)
);
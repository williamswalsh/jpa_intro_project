create table book_uuid (
    id          binary(16) not null,
    title       varchar(255),
    isbn        varchar(255),
    publisher   varchar(255),
    primary key (id)
) engine = InnoDB;
create table if not exists App (
    id identity primary key,
    name varchar(50) not null,
    description varchar(255) not null
);

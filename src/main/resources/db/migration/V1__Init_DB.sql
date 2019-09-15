create sequence hibernate_sequence start 2 increment 1;

create table folder_entity(
id bigint not null,
name_folder varchar(255),
parent_id bigint,
is_active boolean,
primary key(id)
);

alter table if exists folder_entity
add constraint folder_id_fk
foreign key (parent_id) references folder_entity

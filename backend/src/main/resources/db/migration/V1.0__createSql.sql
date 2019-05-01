drop table if exists booking;
drop table if exists location;
drop table if exists trip;
drop table if exists user_entity;
drop table if exists user_entity_roles;

drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table booking (id bigint not null, time_of_purchase date not null, trip_id bigint not null, user_email varchar(255) not null, primary key (id));
create table location (id bigint not null, description varchar(350), name varchar(55), primary key (id));
create table trip (id bigint not null, cost integer not null check (cost>=0), date date, description varchar(150), season varchar(255), title varchar(55), location_id bigint not null, primary key (id));
create table user_entity (email varchar(255) not null, enabled boolean not null, family_name varchar(55), given_name varchar(55), password varchar(340), primary key (email));
create table user_entity_roles (user_entity_email varchar(255) not null, roles varchar(255));
alter table booking add constraint FKkp5ujmgvd2pmsehwpu2vyjkwb foreign key (trip_id) references trip;
alter table booking add constraint FKkg0vym1gnlr8ipuy63qkqp108 foreign key (user_email) references user_entity;
alter table trip add constraint FKbp4lhiixwgb8wwqhr1ipp1ril foreign key (location_id) references location;
alter table user_entity_roles add constraint FKn6hs2xulrxb06oc101wxtihwv foreign key (user_entity_email) references user_entity;

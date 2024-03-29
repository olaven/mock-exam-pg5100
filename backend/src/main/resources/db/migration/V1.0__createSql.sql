drop table booking if exists;
drop table location if exists;
drop table shopping_cart if exists;
drop table shopping_cart_trips if exists;
drop table trip if exists;
drop table user_entity if exists;
drop table user_entity_roles if exists;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table booking (id bigint not null, time_of_purchase date not null, trip_id bigint not null, user_email varchar(255) not null, primary key (id));
create table location (id bigint not null, description varchar(350), name varchar(55), primary key (id));
create table shopping_cart (id bigint not null, primary key (id));
create table shopping_cart_trips (shopping_cart_id bigint not null, trips_id bigint not null);
create table trip (id bigint not null, cost integer not null check (cost>=0), date date, description varchar(150), season varchar(255), title varchar(55), location_id bigint not null, primary key (id));
create table user_entity (email varchar(255) not null, enabled boolean not null, family_name varchar(55), given_name varchar(55), password varchar(340), shopping_cart_id bigint, primary key (email));
create table user_entity_roles (user_entity_email varchar(255) not null, roles varchar(255));
alter table shopping_cart_trips add constraint UK_n6rgo9gidpp7xs9dyh1kxq1cn unique (trips_id);
alter table booking add constraint FKkp5ujmgvd2pmsehwpu2vyjkwb foreign key (trip_id) references trip;
alter table booking add constraint FKkg0vym1gnlr8ipuy63qkqp108 foreign key (user_email) references user_entity;
alter table shopping_cart_trips add constraint FKrd99qwyycx73qf4x1jbdw4f50 foreign key (trips_id) references trip;
alter table shopping_cart_trips add constraint FK5o7qk45vdi3iou0dpqi01ablj foreign key (shopping_cart_id) references shopping_cart;
alter table trip add constraint FKbp4lhiixwgb8wwqhr1ipp1ril foreign key (location_id) references location;
alter table user_entity add constraint FKdmfa05m681k51ww7jk9gk4ikk foreign key (shopping_cart_id) references shopping_cart;
alter table user_entity_roles add constraint FKn6hs2xulrxb06oc101wxtihwv foreign key (user_entity_email) references user_entity;

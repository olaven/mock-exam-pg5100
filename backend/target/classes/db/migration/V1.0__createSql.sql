create sequence hibernate_sequence start with 1 increment by 1;
create table purchase_entity (id bigint not null, time_of_purchase date not null, trip_id bigint not null, user_email varchar(255) not null, primary key (id));
create table trip_entity (id bigint not null, date date, description varchar(150), location varchar(150), season varchar(255), title varchar(55), primary key (id));
create table user_entity (email varchar(255) not null, family_name varchar(55), given_name varchar(55), password varchar(340), primary key (email));
alter table purchase_entity add constraint FKhe1w26o15e5wfxqflx7pca4gl foreign key (trip_id) references trip_entity;
alter table purchase_entity add constraint FKs5kk6k5uhoj5fho0ayotya9x2 foreign key (user_email) references user_entity;
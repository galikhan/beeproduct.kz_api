create sequence beeproduct_seq;
create table product(
	id_ bigint primary key,
	title_ varchar(255),
	description text,
	avatar_ varchar(255),
	path_ varchar(255),
	price_ integer
);


create table users(
	login_ varchar(255) primary key,
	password_ varchar(255),
	phone_ varchar(15) ,
	address_ text ,
	entrance_ integer,
    floor_ integer,
    flat_ integer
);

create table orders(
	id_ bigint primary key,
	user_ varchar(255) references users(login_),
	order_time_ timestamp,
	amount_ integer
);


create table product_in_orders(
    product_ bigint references product(id_),
    order_ bigint references orders(id_)
);

create table file(
    id_ bigint primary key,
    container_ bigint,
    filename_ text,
    filepath_ text,
    removed_ boolean default false,
    modified_ timestamp,
    created_ timestamp default now()
);

--alter table users drop column session_ ;
alter table users add column session_ varchar(255) unique;
alter table product_in_orders add column amount_ integer;
alter table product_in_orders add column removed_ boolean default false;
alter table users add column removed_ boolean default false;
alter table users add column fullname_ varchar(255);
alter table orders drop column amount_;
create type status as ENUM('started', 'confirmed', 'finished');
alter table orders add column status_ status;
alter table product add column removed_ boolean default false;

insert into product(id_, title_, description, avatar_, path_, price_) values(2, 'Натуральный', 'Оне мморе натурал мед', '', '', 4000);
insert into product(id_, title_, description, avatar_, path_, price_) values(3, '222Натуральный222', 'Оне мморе натурал мед222', '', '', 4700);

alter table users add column payment_type_ varchar(255);

create table tg_user (
    id_ bigint primary key,
    chat_id_ integer unique,
    username_ varchar(255),
    first_name_ varchar(255),
    role_ varchar(20)
);


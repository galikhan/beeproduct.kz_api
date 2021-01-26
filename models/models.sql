create sequence honeybee_seq;
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
	phone_ varchar(15) not null,
	address_ text not null,
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


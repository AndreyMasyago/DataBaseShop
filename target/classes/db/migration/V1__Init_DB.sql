create table catalog (
                         detail_id int4 not null,
                         goods_name varchar(255),
                         primary key (detail_id)
);

create table delivery (
                          delivery_id int4 not null,
                          arriving_date_on_storage date,
                          primary key (delivery_id)
);

create table delivery_content (
                                  delivery_content_id int4 not null,
                                  amount int4 not null,
                                  delivery_id int4,
                                  goods_id int4,
                                  primary key (delivery_content_id)
);

create table goods (
                       goods_id int4 not null,
                       delivery_time int4 not null,
                       producer varchar(255),
                       purchase_price int4 not null,
                       selling_price int4 not null,
                       size int4 not null,
                       detail_id int4,
                       provider_id int4,
                       primary key (goods_id)
);

create table order_content (
                               order_content_id int4 not null,
                               amount int4 not null,
                               goods_id int4,
                               order_id int4,
                               primary key (order_content_id)
);

create table order_entity (
                              order_id int4 not null,
                              order_date date,
                              primary key (order_id)
);

create table provider (
                          provider_id int4 not null,
                          category varchar(255),
                          provider_name varchar(255),
                          primary key (provider_id)
);

create table reject (
                        reject_id int4 not null,
                        amount int4 not null,
                        goods_id int4,
                        order_id int4,
                        primary key (reject_id)
);

create table storage (
                         cells_id int4 not null,
                         cells_size int4 not null,
                         primary key (cells_id)
);

create table storage_transactions (
                                      storage_transaction_id int4 not null,
                                      amount int4 not null,
                                      transaction_date date,
                                      goods_id int4,
                                      cells_id int4,
                                      primary key (storage_transaction_id)
);

alter table if exists delivery_content
    add constraint FK2xu1hyykqjaha9ymxtuhlxr63
        foreign key (delivery_id) references delivery;

alter table if exists delivery_content
    add constraint FK9r76xq5smdwgqm4jh2nbn5ipn
        foreign key (goods_id) references goods;

alter table if exists goods
    add constraint FK2t06eu1i3umg5oj6e842t9uwo
        foreign key (detail_id) references catalog;

alter table if exists goods
    add constraint FKas3pyf4c2fwbheqwsmfl4xy1q
        foreign key (provider_id) references provider;

alter table if exists order_content
    add constraint FKt1rxxhgeuctqg64gowkufqo9t
        foreign key (goods_id) references goods;

alter table if exists order_content
    add constraint FKcofx75si6g0gw0u9nf24yrakn
        foreign key (order_id) references order_entity;

alter table if exists reject
    add constraint FKisqpwpkue89953kd2t2hyg5ks
        foreign key (goods_id) references goods;

alter table if exists reject
    add constraint FKe6p31t2v772oqdk2s9ulqfuc8
        foreign key (order_id) references order_entity;

alter table if exists storage_transactions
    add constraint FKh1tv7nv12ucnm3abe4vi0oiro
        foreign key (goods_id) references goods;

alter table if exists storage_transactions
    add constraint FKa5yfsgpapdkewetj6sqox747r
        foreign key (cells_id) references storage;
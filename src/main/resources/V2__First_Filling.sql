INSERT INTO delivery (arriving_date_on_storage) VALUES ('1.05.2020');






INSERT INTO delivery (arriving_date_on_storage) VALUES
('3.05.2020'),
('7.05.2020'),
('11.05.2020'),
('13.05.2020'),
('15.05.2020'),
('17.05.2020'),
('19.05.2020'),
('25.05.2020'),
('30.05.2020'),
('5.06.2020'),
('15.06.2020'),
('7.06.2020'),
('21.06.2020');

INSERT INTO catalog (goods_name) VALUES
('болт на 16'),
('гайка на 16'),
('двигатель 5a-FE'),
('двигатель 1JZ-GTE'),
('топливный фильтр камри 40'),
('топливный фильтр аккорд 7'),
('шина 185/65/15 лето'),
('шина 185/65/15 шипы'),
('глушитель приора'),
('глушитель москвич 2141');

INSERT INTO provider (category, provider_name) VALUES
('official', 'TOYOTA NSK'),
('official', 'JapanParts'),
('official', 'TOYOTA 54'),
('official', 'JapanImport'),
('small production', 'ИП Петров'),
('small production', 'ИП Иванов'),
('small production', 'ИП Сидоров'),
('shop', 'маГАЗин'),
('shop', 'магазин на углу'),
('shop', 'Запчасти у Ашота');

INSERT INTO order_entity (order_date) VALUES
('17.05.2020'),
('18.05.2020'),
('19.05.2020'),
('20.05.2020'),
('23.05.2020'),
('25.05.2020'),
('27.05.2020'),
('30.05.2020'),
('01.06.2020'),
('04.06.2020');

INSERT INTO storage (cells_size) VALUES
(10000),
(10000),
(10000),
(20000),
(20000),
(30000),
(40000),
(50000),
(50000),
(50000);

/*INSERT INTO goods
    (goods_id, delivery_time, producer, purchase_price, selling_price, size, detail_id, provider_id) VALUES

 */
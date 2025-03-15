create table orders(
    id serial primary key,
    order_number varchar,
    sku_code varchar,
    price decimal(19,2),
    quantity int
)
CREATE TABLE account (
    id int8 not null,
    account_owner varchar(255),
    balance float8,
    primary key (id)
);
CREATE TABLE transaction (
    id int8 not null,
    amount float8,
    operation int4,
    source_account_id int8,
    source_account_owner varchar(255),
    target_account_id int8,
    transaction_time timestamp,
    primary key (id)
);
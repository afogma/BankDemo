CREATE TABLE account (
    id uuid not null,
    account_owner varchar(255),
    balance float8,
    version int4,
    primary key (id)
);

CREATE TABLE transaction (
    id uuid not null,
    amount float8,
    operation int4,
    source_account_id uuid,
    source_account_owner varchar(255),
    target_account_id uuid,
    transaction_time timestamp,
    primary key (id)
);
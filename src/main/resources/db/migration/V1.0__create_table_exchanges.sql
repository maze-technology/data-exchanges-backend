create table public.exchanges
(
  id          varchar(255) primary key,
  created_at  timestamp with time zone not null default now()
);

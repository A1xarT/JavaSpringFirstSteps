create table if not exists public.book
(
    id integer not null GENERATED ALWAYS AS IDENTITY,
    name character varying NOT NULL,
    author character varying NOT NULL,
    year integer NOT NULL,
    owner_id integer,
    CONSTRAINT book_pkey PRIMARY KEY (id),
    CONSTRAINT owner_id_fkey FOREIGN KEY (owner_id)
        REFERENCES public.person (id)
        ON delete CASCADE
)

select * from book

create table if not exists public.person
(
    id integer not null GENERATED ALWAYS AS IDENTITY,
    name character varying(100) NOT NULL,
    birthday date NOT NULL,
    CONSTRAINT person_pkey PRIMARY KEY (id),
    CONSTRAINT person_name_key UNIQUE (name),
    CONSTRAINT birthday_check CHECK (birthday <= CURRENT_DATE)
)

select * from person
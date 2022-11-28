CREATE TABLE IF NOT EXISTS public.book
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    author character varying COLLATE pg_catalog."default" NOT NULL,
    year integer NOT NULL,
    owner_id integer,
    taken_at timestamp without time zone,
    CONSTRAINT book_pkey PRIMARY KEY (id),
    CONSTRAINT owner_id_fkey FOREIGN KEY (owner_id)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

select * from book

CREATE TABLE IF NOT EXISTS public.person
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    birthday date NOT NULL,
    created_at timestamp without time zone,
    mood character varying(155) COLLATE pg_catalog."default",
    CONSTRAINT person_pkey PRIMARY KEY (id),
    CONSTRAINT person_name_key UNIQUE (name),
    CONSTRAINT birthday_check CHECK (birthday <= CURRENT_DATE) NOT VALID
)

select * from person

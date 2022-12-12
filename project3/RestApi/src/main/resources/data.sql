-- Table: public.sensor

-- DROP TABLE IF EXISTS public.sensor;

CREATE TABLE IF NOT EXISTS public.sensor
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(32) COLLATE pg_catalog."default",
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT sensor_pkey PRIMARY KEY (id),
    CONSTRAINT name_unique_check UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sensor
    OWNER to postgres;

-- Table: public.measurement

-- DROP TABLE IF EXISTS public.measurement;

CREATE TABLE IF NOT EXISTS public.measurement
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    value double precision NOT NULL,
    raining boolean NOT NULL,
    sensor_id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT measurement_pkey PRIMARY KEY (id),
    CONSTRAINT measurement_sensor_id_fkey FOREIGN KEY (sensor_id)
        REFERENCES public.sensor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT measurement_value_check CHECK (value >= '-100'::integer::double precision AND value <= 100::double precision)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.measurement
    OWNER to postgres;
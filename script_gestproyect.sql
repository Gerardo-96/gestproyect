CREATE USER GESTPRO WITH PASSWORD 'GestPro2021';

CREATE USER gestproadmin WITH password 'AdminGestPr0#';

CREATE DATABASE GESTPRODB WITH OWNER GESTPRO;

GRANT TEMPORARY, CONNECT ON DATABASE gestprodb TO PUBLIC;

GRANT ALL ON DATABASE gestprodb TO gestproadmin;

CREATE TABLE public.usuario
(
    id_usuario integer NOT NULL,
    username character varying(10) NOT NULL COLLATE pg_catalog."default",
    nombre character varying(30) NOT NULL COLLATE pg_catalog."default",
    apellido character varying(30) NOT NULL COLLATE pg_catalog."default",
    fecha_creacion timestamp without time zone NOT NULL default CURRENT_TIMESTAMP,
    password character varying(50) NOT NULL COLLATE pg_catalog."default",
    id_perfil integer NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario)
)

CREATE SEQUENCE public.sc_id_usuario
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
	
 GRANT ALL PRIVILEGES ON TABLE usuario TO gestpro;
 GRANT ALL PRIVILEGES ON SEQUENCE sc_id_usuario TO gestpro;


CREATE FUNCTION public.id_usuario()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
NEW.id_usuario := NEXTVAL('sc_id_usuario');
RETURN NEW;
END;
$BODY$;

	
CREATE TRIGGER trig_id_usuario
    BEFORE INSERT
    ON public.usuario
    FOR EACH ROW
    EXECUTE PROCEDURE public.id_usuario();
	

INSERT INTO usuario (username, nombre, apellido, password, id_perfil)
VALUES ('admin', 'Administrador', ' ', 'is2021', 1);
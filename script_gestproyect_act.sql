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
    CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario)
);

ALTER TABLE usuario
	ADD CONSTRAINT uq_usuario_username
	UNIQUE (username);

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
	

INSERT INTO usuario (username, nombre, apellido, password)
VALUES ('ADMIN', 'Administrador', ' ', 'is2021');


CREATE TABLE public.rol
(
    id_rol integer NOT NULL,
    codigo_rol character varying(20) NOT NULL COLLATE pg_catalog."default",
    descripcion character varying(100) NOT NULL COLLATE pg_catalog."default",
    fecha_creacion timestamp without time zone NOT NULL default CURRENT_TIMESTAMP,
    CONSTRAINT rol_pkey PRIMARY KEY (id_rol)
);


CREATE TABLE public.rol_usuario
(
    id_rol integer NOT NULL,
    id_usuario integer NOT NULL,
	CONSTRAINT rol_usuario_pkey PRIMARY KEY (id_rol,id_usuario),
	CONSTRAINT fk_rol_usuario_rol
     FOREIGN KEY(id_rol) 
	  REFERENCES rol(id_rol),
	CONSTRAINT fk_rol_usuario_us
     FOREIGN KEY(id_usuario) 
	  REFERENCES usuario(id_usuario)
);


CREATE SEQUENCE public.sc_id_rol
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

 GRANT ALL PRIVILEGES ON TABLE rol TO gestpro;
 GRANT ALL PRIVILEGES ON TABLE rol_usuario TO gestpro;
 GRANT ALL PRIVILEGES ON SEQUENCE sc_id_rol TO gestpro;

 GRANT ALL PRIVILEGES ON TABLE rol TO gestproadmin;
 GRANT ALL PRIVILEGES ON TABLE rol_usuario TO gestproadmin;
 GRANT ALL PRIVILEGES ON SEQUENCE sc_id_rol TO gestproadmin;


CREATE FUNCTION public.id_rol()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
NEW.id_rol := NEXTVAL('sc_id_rol');
RETURN NEW;
END;
$BODY$;

	
CREATE TRIGGER trig_id_rol
    BEFORE INSERT
    ON public.rol
    FOR EACH ROW
    EXECUTE PROCEDURE public.id_rol();
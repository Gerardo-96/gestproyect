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
	
	
CREATE TABLE public.proyecto
(
    id_proyecto integer NOT NULL,
    nombre character varying(50) NOT NULL,
	id_lider integer NOT NULL,
	descripicon character varying(300) NOT NULL,
	CONSTRAINT proyecto_pkey PRIMARY KEY (id_proyecto),
	CONSTRAINT fk_proyecto_lider
     FOREIGN KEY(id_lider) 
	  REFERENCES usuario(id_usuario)
);


CREATE SEQUENCE public.sc_id_proyecto
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


CREATE FUNCTION public.id_proyecto()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
NEW.id_proyecto := NEXTVAL('sc_id_proyecto');
RETURN NEW;
END;
$BODY$;

	
CREATE TRIGGER trig_id_proyecto
    BEFORE INSERT
    ON public.proyecto
    FOR EACH ROW
    EXECUTE PROCEDURE public.id_proyecto();
	
	
CREATE TABLE public.proyecto_usuario
(
    id_proyecto integer NOT NULL,
    id_usuario integer NOT NULL,
	CONSTRAINT proyecto_usuario_pkey PRIMARY KEY (id_proyecto,id_usuario),
	CONSTRAINT fk_proyecto_usuario_py
     FOREIGN KEY(id_proyecto) 
	  REFERENCES proyecto(id_proyecto),
	CONSTRAINT fk_proyecto_usuario_us
     FOREIGN KEY(id_usuario) 
	  REFERENCES usuario(id_usuario)
);


CREATE TABLE public.tarea
(
    id_tarea integer NOT NULL,
    estado char(1) NOT NULL,
	nombre character varying(50) NOT NULL,
	descripcion character varying(200) NOT NULL,
	id_padre integer,
	version character varying(20) NOT NULL,
	prioridad character varying(10) NOT NULL,
	observacion character varying(200) NOT NULL,
	editable boolean not null default true,
	CONSTRAINT tarea_pkey PRIMARY KEY (id_tarea)
);


ALTER TABLE public.tarea
	ADD CONSTRAINT fk_tarea
     FOREIGN KEY(id_padre) 
	  REFERENCES tarea(id_tarea);


CREATE SEQUENCE public.sc_id_tarea
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


CREATE FUNCTION public.id_tarea()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
NEW.id_tarea := NEXTVAL('sc_id_tarea');
RETURN NEW;
END;
$BODY$;

	
CREATE TRIGGER trig_id_tarea
    BEFORE INSERT
    ON public.tarea
    FOR EACH ROW
    EXECUTE PROCEDURE public.id_tarea();
	

CREATE TABLE public.proyecto_tarea
(
    id_proyecto integer NOT NULL,
    id_tarea integer NOT NULL,
	CONSTRAINT proyecto_tarea_pkey PRIMARY KEY (id_proyecto,id_tarea),
	CONSTRAINT fk_proyecto_tarea_py
     FOREIGN KEY(id_proyecto) 
	  REFERENCES proyecto(id_proyecto),
	CONSTRAINT fk_proyecto_tarea_tr
     FOREIGN KEY(id_tarea) 
	  REFERENCES tarea(id_tarea)
);


CREATE TABLE public.linea_base
(
    id_linea_base integer NOT NULL PRIMARY KEY,
    nombre char(20) NOT NULL
);


CREATE TABLE public.linea_base_tarea
(
    id_tarea integer NOT NULL,
    id_linea_base integer NOT NULL,
	CONSTRAINT linea_base_tarea_pkey PRIMARY KEY (id_tarea, id_linea_base),
	CONSTRAINT fk_linea_base_tarea_tr
	 FOREIGN KEY(id_tarea) 
	  REFERENCES tarea(id_tarea),
	CONSTRAINT fk_linea_base_tarea_lb
     FOREIGN KEY(id_linea_base) 
	  REFERENCES linea_base(id_linea_base)
);


CREATE SEQUENCE public.sc_id_linea_base
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


CREATE FUNCTION public.id_linea_base()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
NEW.id_linea_base := NEXTVAL('sc_id_linea_base');
RETURN NEW;
END;
$BODY$;

	
CREATE TRIGGER trig_id_linea_base
    BEFORE INSERT
    ON public.linea_base
    FOR EACH ROW
    EXECUTE PROCEDURE public.id_linea_base();
	

CREATE TABLE public.fase
(
    id_fase integer NOT NULL,
	nombre character varying(100) NOT NULL,
	id_tarea integer NOT NULL,
	id_proyecto integer NOT NULL,
	CONSTRAINT fase_pkey PRIMARY KEY (id_fase),
	CONSTRAINT fk_fase_tr
	 FOREIGN KEY(id_tarea) 
	  REFERENCES tarea(id_tarea),
	CONSTRAINT fk_fase_py
     FOREIGN KEY(id_proyecto) 
	  REFERENCES proyecto(id_proyecto)
);

CREATE SEQUENCE public.sc_id_fase
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


CREATE FUNCTION public.id_fase()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN
NEW.id_fase := NEXTVAL('sc_id_fase');
RETURN NEW;
END;
$BODY$;

	
CREATE TRIGGER trig_id_fase
    BEFORE INSERT
    ON public.fase
    FOR EACH ROW
    EXECUTE PROCEDURE public.id_fase();

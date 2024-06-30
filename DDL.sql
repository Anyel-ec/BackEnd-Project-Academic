-- CREATE DATABASE proyecto_peru_academico;
use proyecto_peru_academico;


-- Registro e inicio de sesion

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT true
);

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    contraseña VARCHAR(255) NOT NULL ,
    id_rol INT NOT NULL,
    fecha_nacimiento DATE ,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_rol) REFERENCES roles(id)
);

-- Actores Principales del Sistema

create table Universidad (
    id_universidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT true
);

CREATE TABLE carrera (
    id_carrera INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    id_universidad INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_universidad) REFERENCES Universidad(id_universidad)
);

CREATE TABLE tesis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    fecha_publicacion DATE,
    activo BOOLEAN DEFAULT true
);

CREATE TABLE estudiantes (
    codigo_estudiante INT AUTO_INCREMENT PRIMARY KEY,
    DNI INT NOT NULL,
    nombres varchar (150) not null,
    apellido_paterno varchar (50) not null,
    apellido_materno varchar (50) not null,
    fecha_nacimiento DATE,
    correo varchar (100) not null,
    telefono varchar (9) not null,
    direccion varchar(255),
    id_tesis not null,
    id_carrera INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_carrera) REFERENCES carrera(id_carrera),
);

CREATE TABLE docentes (
    codigo_docente INT AUTO_INCREMENT PRIMARY KEY,
    DNI INT NOT NULL,
    nombres varchar (150) not null,
    apellido_paterno varchar (50) not null,
    apellido_materno varchar (50) not null,
    fecha_nacimiento DATE,
    correo varchar (100) not null,
    telefono varchar (9) not null,
    direccion varchar(255),
    id_carrera INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_carrera) REFERENCES carrera(id_carrera)
);

create table secretaria (
    codigo_secretaria INT AUTO_INCREMENT PRIMARY KEY,
    DNI INT NOT NULL,
    nombres varchar (150) not null,
    apellido_paterno varchar (50) not null,
    apellido_materno varchar (50) not null,
    fecha_nacimiento DATE,
    correo varchar (100) not null,
    telefono varchar (9) not null,
    direccion varchar(255),
    activo BOOLEAN DEFAULT true,
    id_universidad INT NOT NULL,
    FOREIGN KEY (id_universidad) REFERENCES Universidad(id_universidad)
);

create table administrador (
    codigo_administrador INT AUTO_INCREMENT PRIMARY KEY,
    DNI INT NOT NULL,
    nombres varchar (150) not null,
    apellido_paterno varchar (50) not null,
    apellido_materno varchar (50) not null,
    fecha_nacimiento DATE,
    correo varchar (100) not null,
    telefono varchar (9) not null,
    direccion varchar(255),
    id_universidad INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_universidad) REFERENCES Universidad(id_universidad)
);


-- SEGUIMIENTO (APP)

CREATE table reserva_titulo_paso_uno (
    id_reserva_titulo_paso_uno INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante INT NOT NULL,
    fut varchar(255) not null comment 'Documento FUT',
    historial_academico varchar(255) not null,
    pdf_tesis varchar(255) null comment 'Tesis en Formato PDF',
    fecha_entrega date not null,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

create table constancia_filtro_paso_dos(
    id_constancia INT AUTO_INCREMENT PRIMARY KEY,
    fut varchar(255) not null comment 'Documento FUT',
    carta_aceptacion varchar(255) not null,
    copia_reporte varchar(255) not null,
    bv_emision_filtro varchar(255) not null 'Emision de Constancia de Filtro de Similitud',
    pdf_tesis varchar(255) null comment 'Tesis en Formato PDF',
    id_estudiante INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

create table aprobacion_tesis_paso_tres(
    id_aprobacion INT AUTO_INCREMENT PRIMARY KEY,
    fut varchar(255) not null comment 'Documento FUT',
    constancia_filtro varchar(255) not null,
    anexo_cuatro varchar(255) not null,
    contancia_aprobacion_metodologia varchar(255) not null,
    bv_aprobacion varchar(255) not null comment 'B/V aprobacion de proyecto en caja-tesoreria',
    cti_vitae varchar(255) not null comment 'CTI Vitae de CONCYTEC de asesor',
    id_estudiante INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

create table designacion_jurados_paso_cuatro(
    id_designacion INT AUTO_INCREMENT PRIMARY KEY,
    fut varchar(255) not null comment 'Documento FUT',
    informe_asesor varchar(255) not null,
    comprobante_pago varchar(255) not null,
    resolucion_aprobacion varchar(255) not null,
    informe_tesis varchar(255) not null,
    id_estudiante INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);
create table recomposicion_jurados_paso_cinco(
    id_recomposicion INT AUTO_INCREMENT PRIMARY KEY,
    solicitud_recomposicion varchar(255) not null,
    informe_asesor_motivos varchar(255) not null comment 'Informe del asesor con los motivos de la recomposicion',
    resolucion_recomposicion varchar(255) not null,
    resolucion_designacion varchar(255) not null,
    id_estudiante INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

create table primera_revision_paso_seis(
    id_primera_revision INT AUTO_INCREMENT PRIMARY KEY,
    fut varchar(255) not null comment 'Documento FUT',
    copia_resolucion_designacion varchar(255) not null,
    bv_revision_proyecto varchar(255) not null,
    id_estudiante INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);


create table ultima_revision_paso_siete(
    id_ultima_revision INT AUTO_INCREMENT PRIMARY KEY,
    copia_primera_revision varchar(255) not null,
    copia_resolucion_designacion varchar(255) not null,
    informe_borrador_tesis varchar(255) not null,
    id_estudiante INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

create table ampliacion_plazo_paso_ocho(
    id_ampliacion INT AUTO_INCREMENT PRIMARY KEY,
    solicitud_ampliacion varchar(255) not null,
    informe_asesor_ampliacion varchar(255) not null,
    resolucion_ampliacion varchar(255) not null,
    id_estudiante INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

create table constancia_sustentacion_paso_nueve(
    id_constancia_sustentacion INT AUTO_INCREMENT PRIMARY KEY,
    fut varchar(255) not null comment 'Documento FUT',
    acta_informe_final varchar(255) not null,
    bd_filtro_similitud varchar(255) not null,
    pdf_tesis varchar(255) null comment 'Tesis en Formato PDF',
    id_estudiante INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

create table emision_fecha_sustentacion_paso_diez(
    id_emision_fecha_sustentacion INT AUTO_INCREMENT PRIMARY KEY,
    fut varchar(255) not null comment 'Documento FUT',
    carta_dictamen varchar(255) not null,
    caratula_informe_final varchar(255) not null,
    resolucion_aprobacion varchar(255) not null,
    resolucion_designacion varchar(255) not null,
    copia_constancia_sustentacion varchar(255) not null,
    bv_derecho_sustentacion varchar(255) not null,
    copia_grado_bachiller varchar(255) not null,
    id_estudiante INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

create table notificacion_jurados_paso_once(
    id_notificacion_jurados INT AUTO_INCREMENT PRIMARY KEY,
    solicitud_notificacion varchar(255) not null,
    resolucion_fecha_sustentacion varchar(255) not null,
    resolucion_designacion_jurados varchar(255) not null,
    informe_final_anillado varchar(255) not null,
    id_estudiante INT NOT NULL,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

create table aprobacion_sustentacion_paso_doce(
    id_aprobacion_sustentacion INT AUTO_INCREMENT PRIMARY KEY,
    fut varchar(255) not null comment 'Documento FUT',
    acta_sustentacion varchar(255) not null,
    resolucion_aprobacion varchar(255) not null,
    caratura_informe_final varchar(255) not null,
    id_estudiante INT NOT NULL,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

create table constancia_entrega_empastados_paso_trece(
    id_constancia_empastado INT AUTO_INCREMENT PRIMARY KEY,
    fut varchar(255) not null comment 'Documento FUT',
    copia_acta_sustentacion varchar(255) not null,
    dictamen_individual_sustentacion varchar(255) not null,
    copia_resolucion_consejo varchar(255) not null comment 'Aprobacion del acta de Sust. de tesis',
    portada_firmada_jurados varchar(255) not null ,
    comprobante_pago varchar(255) not null,
    ejemplar_tesis varchar(255) not null,
    acta_sustentacion varchar(255) not null,
    resolucion_aprobacion varchar(255) not null,
    caratura_informe_final varchar(255) not null,
    id_estudiante INT NOT NULL,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);





drop database sistema_investigacion;
create database sistema_investigacion;
use sistema_investigacion;

-- Registro e inicio de sesion

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT true
);

INSERT INTO roles (nombre, descripcion, activo) VALUES
('secretaria', 'Responsable de la administración y gestión de documentos.', true),
('docente', 'Encargado de impartir clases y evaluar a los estudiantes.', true),
('estudiante', 'Persona que recibe educación o formación en la institución.', true);


CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL unique,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL unique,
    contraseña VARCHAR(255) NOT NULL ,
    id_rol INT NOT NULL,
    fecha_nacimiento DATE ,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_rol) REFERENCES roles(id)
);

-- Inserción de usuarios en la tabla 'usuarios'
INSERT INTO usuarios (usuario, nombre, email, contraseña, id_rol, fecha_nacimiento, activo) VALUES
('sec_jgarcia', 'Juana Garcia', 'juana.garcia@correo.com', 'contrasena123', 1, '1985-06-15', true),
('doc_mlopez', 'Mario Lopez', 'mario.lopez@correo.com', 'contrasena456', 2, '1978-02-20', true),
('est_ahernandez', 'Ana Hernandez', 'ana.hernandez@correo.com', 'contrasena789', 3, '2000-09-05', true);

-- Actores Principales del Sistema

CREATE TABLE Universidad (
    id_universidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT true
);
-- Inserción de un registro en la tabla 'Universidad'
INSERT INTO Universidad (nombre, activo) VALUES
('Universidad Nacional de Educación', true);


CREATE TABLE Carrera (
    id_carrera INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    id_universidad INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_universidad) REFERENCES Universidad(id_universidad)
);

-- Inserción de registros en la tabla 'Carrera'
INSERT INTO Carrera (nombre, descripcion, id_universidad, activo) VALUES
('Ingeniería Civil', 'Carrera enfocada en el diseño, construcción y mantenimiento de infraestructuras.', 1, true),
('Ingeniería Informática', 'Carrera centrada en el desarrollo y gestión de sistemas informáticos.', 1, true),
('Ingeniería de Software', 'Carrera especializada en la creación y mantenimiento de software de calidad.', 1, true);


CREATE TABLE Docentes (
    id_docente INT AUTO_INCREMENT PRIMARY KEY,
    DNI INT NOT NULL,
    nombres VARCHAR(150) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    direccion VARCHAR(255),
    activo BOOLEAN DEFAULT true
);

-- Inserción de registros en la tabla 'Docentes'
INSERT INTO Docentes (DNI, nombres, apellido_paterno, apellido_materno, fecha_nacimiento, correo, telefono, direccion, activo) VALUES
(12345678, 'Carlos', 'Perez', 'Lopez', '1980-01-15', 'carlos.perez@correo.com', '098765432', 'Av. Siempre Viva 123', true),
(23456789, 'Maria', 'Gonzalez', 'Martinez', '1975-05-22', 'maria.gonzalez@correo.com', '097654321', 'Calle Falsa 456', true),
(34567890, 'Jose', 'Ramirez', 'Hernandez', '1983-08-30', 'jose.ramirez@correo.com', '096543210', 'Boulevard Central 789', true);


CREATE TABLE Tesis (
    id_tesis INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    fecha_publicacion DATE,
    id_carrera INT NOT NULL,
    id_docente INT,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera),
    FOREIGN KEY (id_docente) REFERENCES Docentes(id_docente)
);
-- Inserción de registros en la tabla 'Tesis'
INSERT INTO Tesis (titulo, fecha_publicacion, id_carrera, id_docente, activo) VALUES
('Diseño y Construcción de Puentes Modernos', '2022-06-15', 1, 1, true),
('Implementación de Sistemas de Información en la Era Digital', '2023-01-20', 2, 2, true),
('Desarrollo de Software Seguro y Eficiente', '2023-03-10', 3, 3, true);


CREATE TABLE Co_Asesores_Tesis (
    id_co_asesor INT AUTO_INCREMENT PRIMARY KEY,
    id_tesis INT NOT NULL,
    id_docente INT NOT NULL,
    FOREIGN KEY (id_tesis) REFERENCES Tesis(id_tesis),
    FOREIGN KEY (id_docente) REFERENCES Docentes(id_docente)
);

-- Inserción de registros en la tabla 'Co_Asesores_Tesis'
INSERT INTO Co_Asesores_Tesis (id_tesis, id_docente) VALUES
(1, 2),
(2, 3),
(3, 1);


CREATE TABLE Estudiantes (
    codigo_estudiante INT AUTO_INCREMENT PRIMARY KEY,
    DNI INT NOT NULL,
    nombres VARCHAR(150) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    direccion VARCHAR(255),
    id_tesis INT,
    id_carrera INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_tesis) REFERENCES Tesis(id_tesis),
    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera)
);

-- Inserción de estudiantes en la tabla 'Estudiantes'
INSERT INTO Estudiantes (DNI, nombres, apellido_paterno, apellido_materno, fecha_nacimiento, correo, telefono, direccion, id_tesis, id_carrera, activo) VALUES
(23456789, 'Ana', 'Gomez', 'Martinez', '2001-08-15', 'ana.gomez@correo.com', '987654321', 'Calle Arequipa 456', 2, 2, true),
(34567890, 'Pedro', 'Ramirez', 'Hernandez', '1999-03-20', 'pedro.ramirez@correo.com', '987654321', 'Jr. Tacna 789', 3, 3, true),
(45678901, 'Luis', 'Garcia', 'Lopez', '2002-11-25', 'luis.garcia@correo.com', '987654321', 'Av. Pizarro 321', 1, 1, true);

CREATE TABLE Docentes_Carrera (
    id_docente INT,
    id_carrera INT,
    PRIMARY KEY (id_docente, id_carrera),
    FOREIGN KEY (id_docente) REFERENCES Docentes(id_docente),
    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera)
);

-- Inserción de registros en la tabla 'Docentes_Carrera'
INSERT INTO Docentes_Carrera (id_docente, id_carrera) VALUES
(1, 1),
(2, 2),
(3, 3);


CREATE TABLE Jurados_Tesis (
    id_jurado INT AUTO_INCREMENT PRIMARY KEY,
    id_tesis INT NOT NULL,
    id_docente INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_tesis) REFERENCES Tesis(id_tesis),
    FOREIGN KEY (id_docente) REFERENCES Docentes(id_docente)
);

-- Inserción de registros en la tabla 'Jurados_Tesis'
INSERT INTO Jurados_Tesis (id_tesis, id_docente, activo) VALUES
(1, 2, true),
(2, 3, true),
(3, 1, true);


CREATE TABLE Secretaria (
    id_secretaria INT AUTO_INCREMENT PRIMARY KEY,
    DNI INT NOT NULL,
    nombres VARCHAR(150) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    direccion VARCHAR(255),
    activo BOOLEAN DEFAULT true,
    id_universidad INT NOT NULL,
    FOREIGN KEY (id_universidad) REFERENCES Universidad(id_universidad)
);

-- Inserción de una secretaria en la tabla 'Secretaria'
INSERT INTO Secretaria (DNI, nombres, apellido_paterno, apellido_materno, fecha_nacimiento, correo, telefono, direccion, activo, id_universidad) VALUES
(12345678, 'María', 'González', 'López', '1990-03-12', 'maria.gonzalez@correo.com', '987654321', 'Av. Universitaria 123', true, 1);



CREATE TABLE Administrador (
    id_administrador INT AUTO_INCREMENT PRIMARY KEY,
    DNI INT NOT NULL,
    nombres VARCHAR(150) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    direccion VARCHAR(255),
    id_universidad INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_universidad) REFERENCES Universidad(id_universidad)
);

-- Inserción de un administrador en la tabla 'Administrador'
INSERT INTO Administrador (DNI, nombres, apellido_paterno, apellido_materno, fecha_nacimiento, correo, telefono, direccion, id_universidad, activo) VALUES
(23456789, 'Juan', 'López', 'Gómez', '1985-08-20', 'juan.lopez@correo.com', '987654321', 'Calle Principal 456', 1, true);

-- SEGUIMIENTO (APP)

CREATE TABLE paso_uno_reserva_titulo (
    id_reserva_titulo_paso_uno INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante INT NOT NULL,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    historial_academico VARCHAR(255) NOT NULL,
    pdf_tesis VARCHAR(255) NULL COMMENT 'Tesis en Formato PDF',
    comprobante_pago VARCHAR(255) NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

-- Inserción de registros en la tabla 'paso_uno_reserva_titulo'
INSERT INTO paso_uno_reserva_titulo (id_estudiante, fut, historial_academico, pdf_tesis, comprobante_pago, id_usuario, created_at, updated_at, activo)
VALUES
(3, 'si', 'no', '/ruta/a/tesis_estudiante_3.pdf', 'si', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(3, 'no', 'si', '/ruta/a/tesis_estudiante_3.pdf', 'no', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(3, 'texto de observacion', 'si', '/ruta/a/tesis_estudiante_3.pdf', 'si', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


CREATE TABLE paso_dos_constancia_filtro (
    id_constancia INT AUTO_INCREMENT PRIMARY KEY,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    carta_aceptacion VARCHAR(255) NOT NULL,
    copia_reporte VARCHAR(255) NOT NULL,
    bv_emision_filtro VARCHAR(255) NOT NULL COMMENT 'Emision de Constancia de Filtro de Similitud',
    pdf_tesis VARCHAR(255) NULL COMMENT 'Tesis en Formato PDF',
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);
-- Inserción de registros en la tabla 'paso_dos_constancia_filtro'
INSERT INTO paso_dos_constancia_filtro (fut, carta_aceptacion, copia_reporte, bv_emision_filtro, pdf_tesis, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'no', 'si', 'si', '/ruta/a/tesis_estudiante_3.pdf', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'si', 'no', 'texto de observacion', '/ruta/a/tesis_estudiante_3.pdf', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'si', 'si', 'no', '/ruta/a/tesis_estudiante_3.pdf', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


CREATE TABLE paso_tres_aprobacion_tesis (
    id_aprobacion INT AUTO_INCREMENT PRIMARY KEY,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    constancia_filtro VARCHAR(255) NOT NULL,
    anexo_cuatro VARCHAR(255) NOT NULL,
    constancia_aprobacion_metodologia VARCHAR(255) NOT NULL,
    bv_aprobacion VARCHAR(255) NOT NULL COMMENT 'B/V aprobacion de proyecto en caja-tesoreria',
    cti_vitae VARCHAR(255) NOT NULL COMMENT 'CTI Vitae de CONCYTEC de asesor',
    anillado_proyecto_tesis VARCHAR(255) NOT NULL comment 'Tesis en Fisico',
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

-- Inserción de registros en la tabla 'paso_tres_aprobacion_tesis'
INSERT INTO paso_tres_aprobacion_tesis (fut, constancia_filtro, anexo_cuatro, constancia_aprobacion_metodologia, bv_aprobacion, cti_vitae, anillado_proyecto_tesis, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'no', 'si', 'si', 'si', 'cti_vitae.pdf', 'tesis_fisica.pdf', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'si', 'no', 'si', 'no', 'cti_vitae.pdf', 'tesis_fisica.pdf', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'si', 'si', 'si', 'no', 'cti_vitae.pdf', 'tesis_fisica.pdf', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


CREATE TABLE paso_cuatro_designacion_jurados (
    id_designacion INT AUTO_INCREMENT PRIMARY KEY,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    informe_asesor VARCHAR(255) NOT NULL,
    comprobante_pago VARCHAR(255) NOT NULL,
    resolucion_aprobacion VARCHAR(255) NOT NULL,
    informe_tesis VARCHAR(255) NOT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);
-- Inserción de registros en la tabla 'paso_cuatro_designacion_jurados'
INSERT INTO paso_cuatro_designacion_jurados (fut, informe_asesor, comprobante_pago, resolucion_aprobacion, informe_tesis, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'no, texto observacion ', 'si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'si', 'no', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'si', 'si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

CREATE TABLE paso_cinco_recomposicion_jurados (
    id_recomposicion INT AUTO_INCREMENT PRIMARY KEY,
    solicitud_recomposicion VARCHAR(255) NOT NULL,
    informe_asesor_motivos VARCHAR(255) NOT NULL COMMENT 'Informe del asesor con los motivos de la recomposicion',
    resolucion_aprobacion VARCHAR(255) NOT NULL,
    resolucion_designacion VARCHAR(255) NOT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

-- Inserción de registros en la tabla 'paso_cinco_recomposicion_jurados'
INSERT INTO paso_cinco_recomposicion_jurados (solicitud_recomposicion, informe_asesor_motivos, resolucion_aprobacion, resolucion_designacion, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


CREATE TABLE paso_seis_primera_revision (
    id_primera_revision INT AUTO_INCREMENT PRIMARY KEY,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    copia_resolucion_designacion VARCHAR(255) NOT NULL,
    bv_revision_proyecto VARCHAR(255) NOT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

-- Inserción de registros en la tabla 'paso_seis_primera_revision'
INSERT INTO paso_seis_primera_revision (fut, copia_resolucion_designacion, bv_revision_proyecto, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'no', 'no', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'observacion', 'observacion', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


CREATE TABLE paso_siete_ultima_revision (
    id_ultima_revision INT AUTO_INCREMENT PRIMARY KEY,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    copia_primera_revision VARCHAR(255) NOT NULL,
    copia_resolucion_designacion VARCHAR(255) NOT NULL,
    informe_borrador_tesis VARCHAR(255) NOT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);
-- Inserción de registros en la tabla 'paso_siete_ultima_revision'
INSERT INTO paso_siete_ultima_revision (fut, copia_primera_revision, copia_resolucion_designacion, informe_borrador_tesis, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'no', 'no', 'no', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'observacion', 'observacion', 'observacion', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


CREATE TABLE paso_ocho_ampliacion_plazo (
    id_ampliacion INT AUTO_INCREMENT PRIMARY KEY,
    solicitud_ampliacion VARCHAR(255) NULL DEFAULT NULL,
    informe_asesor_ampliacion VARCHAR(255) NULL DEFAULT NULL,
    resolucion_aprobacion VARCHAR(255) NULL DEFAULT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);
-- Inserción de registros en la tabla 'paso_ocho_ampliacion_plazo'
INSERT INTO paso_ocho_ampliacion_plazo (solicitud_ampliacion, informe_asesor_ampliacion, resolucion_aprobacion, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
(NULL, NULL, NULL, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(NULL, NULL, NULL, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(NULL, NULL, NULL, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


CREATE TABLE paso_nueve_constancia_sustentacion (
    id_constancia_sustentacion INT AUTO_INCREMENT PRIMARY KEY,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    acta_informe_final VARCHAR(255) NOT NULL,
    bd_filtro_similitud VARCHAR(255) NOT NULL,
    pdf_tesis VARCHAR(255) NULL COMMENT 'Tesis en Formato PDF',
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

-- Inserción de registros en la tabla 'paso_nueve_constancia_sustentacion'
INSERT INTO paso_nueve_constancia_sustentacion (fut, acta_informe_final, bd_filtro_similitud, pdf_tesis, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'si', 'si', 'ruta1.pdf', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'no', 'no', NULL, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'observacion', 'observacion', 'ruta2.pdf', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


CREATE TABLE paso_diez_emision_fecha_sustentacion (
    id_emision_fecha_sustentacion INT AUTO_INCREMENT PRIMARY KEY,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    carta_dictamen VARCHAR(255) NOT NULL,
    caratula_informe_final VARCHAR(255) NOT NULL,
    resolucion_aprobacion VARCHAR(255) NOT NULL,
    resolucion_ampliacion VARCHAR(255)  NULL,
    resolucion_designacion VARCHAR(255) NOT NULL,
    copia_constancia_sustentacion VARCHAR(255) NOT NULL,
    bv_derecho_sustentacion VARCHAR(255) NOT NULL,
    copia_grado_bachiller VARCHAR(255) NOT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

-- Inserción de registros en la tabla 'paso_diez_emision_fecha_sustentacion'
INSERT INTO paso_diez_emision_fecha_sustentacion
(fut, carta_dictamen, caratula_informe_final, resolucion_aprobacion, resolucion_ampliacion, resolucion_designacion, copia_constancia_sustentacion, bv_derecho_sustentacion, copia_grado_bachiller, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'si', 'si', 'si', NULL, 'si', 'si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'no', 'no', 'no', 'no', 'no', 'no', 'no', 'no', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'observacion', 'observacion', 'observacion', NULL, 'observacion', 'observacion', 'observacion', 'observacion', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);



CREATE TABLE paso_once_notificacion_jurados (
    id_notificacion_jurados INT AUTO_INCREMENT PRIMARY KEY,
    solicitud_notificacion VARCHAR(255) NOT NULL,
    resolucion_fecha_sustentacion VARCHAR(255) NOT NULL,
    resolucion_designacion_jurados VARCHAR(255) NOT NULL,
    informe_final_anillado VARCHAR(255) NOT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);
-- Inserción de registros en la tabla paso_once_notificacion_jurados
INSERT INTO paso_once_notificacion_jurados
(solicitud_notificacion, resolucion_fecha_sustentacion, resolucion_designacion_jurados, informe_final_anillado, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'no', 'no', 'no', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'observacion', 'observacion', 'observacion', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


CREATE TABLE paso_doce_aprobacion_sustentacion (
    id_aprobacion_sustentacion INT AUTO_INCREMENT PRIMARY KEY,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    acta_sustentacion VARCHAR(255) NOT NULL,
    resolucion_aprobacion VARCHAR(255) NOT NULL,
    caratura_informe_final VARCHAR(255) NOT NULL,
    informe_final_anillado VARCHAR(255) NOT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

INSERT INTO paso_doce_aprobacion_sustentacion
(fut, acta_sustentacion, resolucion_aprobacion, caratura_informe_final, informe_final_anillado, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'si', 'si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'no', 'no', 'no', 'no', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'observacion', 'observacion', 'observacion', 'observacion', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


CREATE TABLE paso_trece_constancia_entrega_empastados (
    id_constancia_empastado INT AUTO_INCREMENT PRIMARY KEY,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    copia_acta_sustentacion VARCHAR(255) NOT NULL,
    dictamen_individual_sustentacion VARCHAR(255) NOT NULL,
    copia_resolucion_consejo VARCHAR(255) NOT NULL COMMENT 'Aprobacion del acta de Sust. de tesis',
    portada_firmada_jurados VARCHAR(255) NOT NULL,
    comprobante_pago VARCHAR(255) NOT NULL,
    ejemplar_tesis VARCHAR(255) NOT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

INSERT INTO paso_trece_constancia_entrega_empastados
(fut, copia_acta_sustentacion, dictamen_individual_sustentacion, copia_resolucion_consejo, portada_firmada_jurados, comprobante_pago, ejemplar_tesis, id_estudiante, id_usuario, created_at, updated_at, activo)
VALUES
('si', 'si', 'si', 'si', 'si', 'si', 'si', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('no', 'no', 'no', 'no', 'no', 'no', 'no', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('texto de observacion', 'observacion', 'observacion', 'observacion', 'observacion', 'observacion', 'observacion', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

drop database proyecto_peru_academico;
create database proyecto_peru_academico;
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
    usuario VARCHAR(255) NOT NULL unique,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL unique,
    contraseña VARCHAR(255) NOT NULL ,
    id_rol INT NOT NULL,
    fecha_nacimiento DATE ,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_rol) REFERENCES roles(id)
);

-- Actores Principales del Sistema

CREATE TABLE Universidad (
    id_universidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT true
);

CREATE TABLE Carrera (
    id_carrera INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    id_universidad INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_universidad) REFERENCES Universidad(id_universidad)
);




CREATE TABLE Docentes (
    codigo_docente INT AUTO_INCREMENT PRIMARY KEY,
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
CREATE TABLE Tesis (
    id_tesis INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    fecha_publicacion DATE,
    id_carrera INT NOT NULL,
    id_asesor INT,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera),
    FOREIGN KEY (id_asesor) REFERENCES Docentes(codigo_docente)
);
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

CREATE TABLE Docentes_Carrera (
    id_docente INT,
    id_carrera INT,
    PRIMARY KEY (id_docente, id_carrera),
    FOREIGN KEY (id_docente) REFERENCES Docentes(codigo_docente),
    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera)
);

CREATE TABLE Jurados_Tesis (
    id_jurado INT AUTO_INCREMENT PRIMARY KEY,
    id_tesis INT NOT NULL,
    id_docente INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    FOREIGN KEY (id_tesis) REFERENCES Tesis(id_tesis),
    FOREIGN KEY (id_docente) REFERENCES Docentes(codigo_docente)
);

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

CREATE TABLE paso_tres_aprobacion_tesis (
    id_aprobacion INT AUTO_INCREMENT PRIMARY KEY,
    fut VARCHAR(255) NOT NULL COMMENT 'Documento FUT',
    constancia_filtro VARCHAR(255) NOT NULL,
    anexo_cuatro VARCHAR(255) NOT NULL,
    constancia_aprobacion_metodologia VARCHAR(255) NOT NULL,
    bv_aprobacion VARCHAR(255) NOT NULL COMMENT 'B/V aprobacion de proyecto en caja-tesoreria',
    cti_vitae VARCHAR(255) NOT NULL COMMENT 'CTI Vitae de CONCYTEC de asesor',
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

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

CREATE TABLE paso_cinco_recomposicion_jurados (
    id_recomposicion INT AUTO_INCREMENT PRIMARY KEY,
    solicitud_recomposicion VARCHAR(255) NOT NULL,
    informe_asesor_motivos VARCHAR(255) NOT NULL COMMENT 'Informe del asesor con los motivos de la recomposicion',
    resolucion_recomposicion VARCHAR(255) NOT NULL,
    resolucion_designacion VARCHAR(255) NOT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

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

CREATE TABLE paso_ocho_ampliacion_plazo (
    id_ampliacion INT AUTO_INCREMENT PRIMARY KEY,
    solicitud_ampliacion VARCHAR(255) NULL DEFAULT NULL,
    informe_asesor_ampliacion VARCHAR(255) NULL DEFAULT NULL,
    resolucion_ampliacion VARCHAR(255) NULL DEFAULT NULL,
    id_estudiante INT NOT NULL,
    id_usuario INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(codigo_estudiante)
);

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

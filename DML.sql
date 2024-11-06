USE spring_sistema_academico;

INSERT INTO roles (id, nombre)
VALUES
    (1, 'admin'),
    (2, 'estudiante');

-- Insertar roles

-- Insertar facultades
INSERT INTO facultades (id, nombre_facultad)
VALUES
    (1, 'Ingenieria'),
    (2, 'Licenciatura');

-- Insertar unidades de investigación
INSERT INTO unidad_investigacion (id, nombre_unidad_investigacion, id_facultad)
VALUES
    (1, 'test', 1);

-- Insertar carreras
INSERT INTO carreras (id, nombre_carrera, id_facultad)
VALUES
    (1, 'Ingeniería de Sistemas', 1),
    (2, 'Administración de Empresas', 1),
    (3, 'Contabilidad', 1),
    (4, 'Ingeniería Civil', 1),
    (5, 'Arquitectura', 1),
    (6, 'Derecho', 1),
    (7, 'Medicina', 1),
    (8, 'Enfermería', 1);

-- Insertar líneas de investigación para cada carrera

-- Ingeniería de Sistemas
INSERT INTO linea_investigacion (nombre_linea_investigacion, id_carrera) VALUES
                                                                             ('Ingeniería informática, industria y sociedad', 1),
                                                                             ('Ingeniería de software e innovación tecnológica', 1);

-- Administración de Empresas
INSERT INTO linea_investigacion (nombre_linea_investigacion, id_carrera) VALUES
                                                                             ('Gestión empresarial', 2),
                                                                             ('Gestión pública', 2);

-- Contabilidad
INSERT INTO linea_investigacion (nombre_linea_investigacion, id_carrera) VALUES
    ('Auditoría y control financiero', 3);

-- Ingeniería Civil
INSERT INTO linea_investigacion (nombre_linea_investigacion, id_carrera) VALUES
                                                                             ('Ingeniería de la construcción', 4),
                                                                             ('Ingeniería de materiales', 4);

-- Arquitectura
INSERT INTO linea_investigacion (nombre_linea_investigacion, id_carrera) VALUES
                                                                             ('Diseño urbano y rural', 5),
                                                                             ('Patrimonio arquitectónico y conservación', 5);

-- Medicina
INSERT INTO linea_investigacion (nombre_linea_investigacion, id_carrera) VALUES
                                                                             ('Salud pública y epidemiología', 7),
                                                                             ('Tecnología médica y salud', 7);

-- Insertar usuario admin
INSERT INTO usuarios (
    id_user, correo_usuario, primer_login, nombre_usuario, apellido_usuario,
    contrasena_usuario, estado, username, id_unidad_investigacion, id_rol
)
VALUES
    (1, 'admin@admin.admin', 1, 'admin', 'admin', '$2a$12$NTbtNXDXvYGE/BQCDmWqIOSQLoobZQ5HBYE/E.WZEwx.tMWqJlmsG', 1, 'admin', 1, 1);

-- Insertar alumnos
INSERT INTO alumnos (
    id, direccion, fecha_nacimiento, dni, correo, nombres, sexo, apellido_paterno, apellido_materno, telefono, codigo_alumno, id_carrera
)
VALUES
    (1, 'Av. Siempre Viva 123', '1995-05-12', '12345678', 'carlos.perez@fakemail.com', 'Carlos', TRUE, 'Pérez', 'González', '987654321', '100001', 1),
    (2, 'Calle Falsa 456', '1996-07-23', '23456789', 'laura.gomez@fakemail.com', 'Laura', FALSE, 'Gómez', 'Sánchez', '987654322', '100002', 2),
    (3, 'Av. Los Olivos 789', '1994-09-15', '34567890', 'juan.martinez@fakemail.com', 'Juan', TRUE, 'Martínez', 'López', '987654323', '100003', 3),
    (4, 'Jr. Las Flores 321', '1997-03-10', '45678901', 'ana.rodriguez@fakemail.com', 'Ana', FALSE, 'Rodríguez', 'Salazar', '987654324', '100004', 4),
    (5, 'Calle Primavera 987', '1993-11-28', '56789012', 'maria.fernandez@fakemail.com', 'María', FALSE, 'Fernández', 'Ramírez', '987654325', '100005', 5),
    (6, 'Av. Los Cedros 654', '1998-01-19', '67890123', 'jose.lopez@fakemail.com', 'José', TRUE, 'López', 'Cruz', '987654326', '100006', 6),
    (7, 'Jr. San Martín 432', '1999-04-30', '78901234', 'pedro.diaz@fakemail.com', 'Pedro', TRUE, 'Díaz', 'Rojas', '987654327', '100007', 7),
    (8, 'Av. Independencia 852', '1995-08-11', '89012345', 'jorge.garcia@fakemail.com', 'Jorge', TRUE, 'García', 'Pérez', '987654328', '100008', 1),
    (9, 'Calle Libertad 159', '1996-02-05', '90123456', 'luis.sanchez@fakemail.com', 'Luis', TRUE, 'Sánchez', 'García', '987654329', '100009', 2),
    (10, 'Jr. Los Ángeles 357', '1994-12-22', '01234567', 'beatriz.morales@fakemail.com', 'Beatriz', FALSE, 'Morales', 'Torres', '987654330', '100010', 3),
    (11, 'Av. Los Héroes 951', '1995-09-13', '12345679', 'lucia.romero@fakemail.com', 'Lucía', FALSE, 'Romero', 'Vargas', '987654331', '100011', 4),
    (12, 'Calle Las Palmeras 357', '1997-10-09', '23456780', 'carmen.vargas@fakemail.com', 'Carmen', FALSE, 'Vargas', 'Ortega', '987654332', '100012', 5),
    (13, 'Jr. Belgrano 753', '1996-06-17', '34567891', 'raul.ortega@fakemail.com', 'Raúl', TRUE, 'Ortega', 'Suárez', '987654333', '100013', 6),
    (14, 'Av. San José 468', '1994-03-01', '45678902', 'diana.suarez@fakemail.com', 'Diana', FALSE, 'Suárez', 'Mendoza', '987654334', '100014', 7),
    (15, 'Calle Las Lomas 963', '1998-07-25', '56789013', 'alberto.rivas@fakemail.com', 'Alberto', TRUE, 'Rivas', 'Navarro', '987654335', '100015', 1),
    (16, 'Jr. Los Olmos 654', '1999-05-15', '67890124', 'andrea.castro@fakemail.com', 'Andrea', FALSE, 'Castro', 'Paredes', '987654336', '100016', 2),
    (17, 'Av. Las Américas 123', '1995-02-27', '78901235', 'gabriela.paredes@fakemail.com', 'Gabriela', FALSE, 'Paredes', 'Soto', '987654337', '100017', 3),
    (18, 'Calle Los Ángeles 456', '1997-11-30', '89012346', 'francisco.navarro@fakemail.com', 'Francisco', TRUE, 'Navarro', 'Ruiz', '987654338', '100018', 4),
    (19, 'Jr. Las Magnolias 753', '1994-04-10', '90123457', 'carla.mendoza@fakemail.com', 'Carla', FALSE, 'Mendoza', 'Gutiérrez', '987654339', '100019', 5),
    (20, 'Av. Los Pinos 852', '1996-08-22', '01234568', 'sergio.hernandez@fakemail.com', 'Sergio', TRUE, 'Hernández', 'Carrillo', '987654340', '100020', 6),
    (21, 'Calle Los Rosales 159', '1999-09-17', '12345670', 'valentina.salinas@fakemail.com', 'Valentina', FALSE, 'Salinas', 'Zambrano', '987654341', '100021', 7);

-- Insertar docentes
INSERT INTO docentes (
    id_docente, direccion, fecha_nacimiento, dni, nombres, correo_institucional, apellido_paterno, apellido_materno, telefono, id_carrera
)
VALUES
    (1, 'Av. Siempre Viva 123', '1995-05-12', '12345678', 'Carlos', 'carlos.perez@fakemail.com', 'Pérez', 'González', '987654321', 1),
    (2, 'Calle Falsa 456', '1996-07-23', '23456789', 'Laura', 'laura.gomez@fakemail.com', 'Gómez', 'Sánchez', '987654322', 2),
    (3, 'Av. Los Olivos 789', '1994-09-15', '34567890', 'Juan', 'juan.martinez@fakemail.com', 'Martínez', 'López', '987654323', 3),
    (4, 'Jr. Las Flores 321', '1997-03-10', '45678901', 'Ana', 'ana.rodriguez@fakemail.com', 'Rodríguez', 'Salazar', '987654324', 4),
    (5, 'Calle Primavera 987', '1993-11-28', '56789012', 'María', 'maria.fernandez@fakemail.com', 'Fernández', 'Ramírez', '987654325', 5),
    (6, 'Av. Los Cedros 654', '1998-01-19', '67890123', 'José', 'jose.lopez@fakemail.com', 'López', 'Cruz', '987654326', 6),
    (7, 'Jr. San Martín 432', '1999-04-30', '78901234', 'Pedro', 'pedro.diaz@fakemail.com', 'Díaz', 'Rojas', '987654327', 7),
    (8, 'Av. Independencia 852', '1995-08-11', '89012345', 'Jorge', 'jorge.garcia@fakemail.com', 'García', 'Pérez', '987654328', 1),
    (9, 'Calle Libertad 159', '1996-02-05', '90123456', 'Luis', 'luis.sanchez@fakemail.com', 'Sánchez', 'García', '987654329', 2),
    (10, 'Jr. Los Ángeles 357', '1994-12-22', '01234567', 'Beatriz', 'beatriz.morales@fakemail.com', 'Morales', 'Torres', '987654330', 3),
    (11, 'Av. Los Héroes 951', '1995-09-13', '12345679', 'Lucía', 'lucia.romero@fakemail.com', 'Romero', 'Vargas', '987654331', 4),
    (12, 'Calle Las Palmeras 357', '1997-10-09', '23456780', 'Carmen', 'carmen.vargas@fakemail.com', 'Vargas', 'Ortega', '987654332', 5),
    (13, 'Jr. Belgrano 753', '1996-06-17', '34567891', 'Raúl', 'raul.ortega@fakemail.com', 'Ortega', 'Suárez', '987654333', 6),
    (14, 'Av. San José 468', '1994-03-01', '45678902', 'Diana', 'diana.suarez@fakemail.com', 'Suárez', 'Mendoza', '987654334', 7),
    (15, 'Calle Las Lomas 963', '1998-07-25', '56789013', 'Alberto', 'alberto.rivas@fakemail.com', 'Rivas', 'Navarro', '987654335', 1),
    (16, 'Jr. Los Olmos 654', '1999-05-15', '67890124', 'Andrea', 'andrea.castro@fakemail.com', 'Castro', 'Paredes', '987654336', 2),
    (17, 'Av. Las Américas 123', '1995-02-27', '78901235', 'Gabriela', 'gabriela.paredes@fakemail.com', 'Paredes', 'Soto', '987654337', 3),
    (18, 'Calle Los Ángeles 456', '1997-11-30', '89012346', 'Francisco', 'francisco.navarro@fakemail.com', 'Navarro', 'Ruiz', '987654338', 4),
    (19, 'Jr. Las Magnolias 753', '1994-04-10', '90123457', 'Carla', 'carla.mendoza@fakemail.com', 'Mendoza', 'Gutiérrez', '987654339', 5),
    (20, 'Av. Los Pinos 852', '1996-08-22', '01234568', 'Sergio', 'sergio.hernandez@fakemail.com', 'Hernández', 'Carrillo', '987654340', 6),
    (21, 'Calle Los Rosales 159', '1999-09-17', '12345670', 'Valentina', 'valentina.salinas@fakemail.com', 'Salinas', 'Zambrano', '987654341', 7);
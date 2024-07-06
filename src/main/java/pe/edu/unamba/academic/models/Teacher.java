package pe.edu.unamba.academic.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "docentes")
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Long id;

    @Column(name = "DNI", nullable = false, unique = true, length = 10)
    private int dni;

    @Column(name = "nombres", nullable = false, length = 150)
    private String firstNames;

    @Column(name = "apellido_paterno", nullable = false, length = 50)
    private String lastName;

    @Column(name = "apellido_materno", nullable = false, length = 50)
    private String middleName;

    @Column(name = "fecha_nacimiento")
    private LocalDate birthDate;

    @Column(name = "correo_institucional", nullable = false, length = 100)
    private String institutionalEmail;

    @Column(name = "telefono", nullable = false, length = 9)
    private String phone;

    @Column(name = "direccion", length = 255)
    private String address;
}
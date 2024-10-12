package pe.edu.unamba.academic.models.actors;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Entity
@Table(name = "alumnos")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "codigo_alumno", length = 6, unique = true)
    @Length(min = 6, max = 6)
    private String studentCode;

    @NotBlank
    @Length(min = 8, max = 8)
    @Column(name = "DNI", nullable = false, unique = true, length = 8)
    private String dni;

    @NotBlank
    @Length(min = 2, max = 150)
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo puede contener letras latinoamericanas y espacios.")
    @Column(name = "nombres", nullable = false, length = 150)
    private String firstNames;

    @NotBlank
    @Length(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El apellido paterno solo puede contener letras latinoamericanas y espacios.")
    @Column(name = "apellido_paterno", nullable = false, length = 50)
    private String lastName;

    @NotBlank
    @Length(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El apellido materno solo puede contener letras latinoamericanas y espacios.")
    @Column(name = "apellido_materno", nullable = false, length = 50)
    private String middleName;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate birthDate;

    @NotBlank
    @Length(min = 8, max = 100)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El correo electrónico no es válido.")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank
    @Length(min = 9, max = 9)
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo puede contener números.")
    @Column(name = "telefono", nullable = false, length = 9)
    private String phone;

    @NotBlank
    @Column(name = "direccion", length = 255)
    private String address;

    @Column(name = "sexo", nullable = false, columnDefinition = "boolean default false")
    private boolean gender = false;
    
    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Career career;
}

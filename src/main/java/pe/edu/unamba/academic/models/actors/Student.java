package pe.edu.unamba.academic.models.actors;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 6, max = 6, message = "El código de estudiante debe tener exactamente 6 números")
    private String studentCode;

    @NotBlank(message = "DNI es requerido")
    @Size(min = 8, max = 8, message = "DNI debe tener exactamente 8 números")
    @Column(name = "DNI", nullable = false, unique = true, length = 8)
    private String dni;

    @NotBlank(message = "Nombre es requerido")
    @Length(min = 2, max = 150)
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo puede contener letras latinoamericanas y espacios.")
    @Column(name = "nombres", nullable = false, length = 150)
    private String firstNames;

    public String getFullName() {
        return String.format("%s %s", firstNames, lastName).trim();
    }
    @NotBlank(message = "Apellido Paterno es requerido")
    @Size(max = 50,min = 1, message = "Apellido Paterno debe tener menos de 50 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El apellido paterno solo puede contener letras latinoamericanas y espacios.")
    @Column(name = "apellido_paterno", nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Apellido Materno es requerido")
    @Size(max = 50,min=1,  message = "Apellido Materno debe tener menos de 50 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El apellido materno solo puede contener letras latinoamericanas y espacios.")
    @Column(name = "apellido_materno", nullable = false, length = 50)
    private String middleName;

    @NotNull(message = "Fecha de Nacimiento es requerida")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate birthDate;

    @NotBlank
    @Size(max = 100,min= 5, message = "Correo debe tener menos de 100 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El correo electrónico no es válido.")
    @Column(name = "correo", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Celular es requerido")
    @Size(min = 9, max = 9, message = "Celular debe tener 9 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo puede contener números.")
    @Column(name = "telefono", nullable = false, length = 9)
    private String phone;

    @NotBlank(message = "Dirección es requerida")
    @Size(max = 255, message = "Dirección debe tener menos de 255 caracteres")
    @NotBlank
    @Column(name = "direccion", length = 255)
    private String address;

    @Column(name = "sexo", nullable = false, columnDefinition = "boolean default false")
    private boolean gender = false;
    
    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Career career;
}

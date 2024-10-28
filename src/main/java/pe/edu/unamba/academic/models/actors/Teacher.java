package pe.edu.unamba.academic.models.actors;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;

import java.time.LocalDate;

@Entity
@Table(name = "docentes")
@Data
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Long id;

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
    @Column(name = "fecha_nacimiento")
    private LocalDate birthDate;

    @NotBlank
    @Length(min = 8, max = 100)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El correo electrónico no es válido.")
    @Column(name = "correo_institucional", nullable = false, length = 100)
    private String institutionalEmail;

    @NotBlank
    @Length(min = 9, max = 9)
    @Column(name = "telefono", nullable = false, length = 9)
    private String phone;

    @NotBlank
    @Length(min = 2, max = 255)
    @Column(name = "direccion", length = 255)
    private String address;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Career career;

    @ManyToOne
    @JoinColumn(name = "id_aprobacion_proyecto")
    private ProjectApprovalStepTwo projectApprovalStepTwo;

}
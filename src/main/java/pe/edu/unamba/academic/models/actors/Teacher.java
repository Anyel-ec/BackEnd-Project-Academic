package pe.edu.unamba.academic.models.actors;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
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

    @NotBlank(message = "DNI es requerido")
    @Size(min = 8, max = 8, message = "DNI debe tener exactamente 8 números")
    @Column(name = "DNI", nullable = false, unique = true)
    private String dni;

    @NotBlank(message = "Nombre es requerido")
    @Size(max = 150, min = 2, message = "Nombre debe tener menos de 150 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "Nombre solo puede contener letras y espacios")
    @Column(name = "nombres", nullable = false)
    private String firstNames;

    @NotBlank(message = "Apellido Paterno es requerido")
    @Size(max = 50,min = 1, message = "Apellido Paterno debe tener menos de 50 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "Apellido Paterno solo puede contener letras y espacios")
    @Column(name = "apellido_paterno", nullable = false)
    private String lastName;

    @NotBlank(message = "Apellido Materno es requerido")
    @Size(max = 50,min=1,  message = "Apellido Materno debe tener menos de 50 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "Apellido Materno solo puede contener letras y espacios")
    @Column(name = "apellido_materno", nullable = false)
    private String middleName;

    @NotNull(message = "Fecha de Nacimiento es requerida")
    @Column(name = "fecha_nacimiento")
    private LocalDate birthDate;

    @NotBlank(message = "Correo Institucional es requerido")
    @Size(max = 100,min= 5, message = "Correo debe tener menos de 100 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Correo no es válido")
    @Column(name = "correo_institucional", nullable = false,unique = true)
    private String institutionalEmail;

    @NotBlank(message = "Celular es requerido")
    @Size(min = 9, max = 9, message = "Celular debe tener 9 dígitos")
    @Column(name = "telefono", nullable = false)
    private String phone;

    @NotBlank(message = "Dirección es requerida")
    @Size(max = 255, message = "Dirección debe tener menos de 255 caracteres")
    @Column(name = "direccion")
    private String address;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Career career;

    @ManyToOne
    @JoinColumn(name = "id_aprobacion_proyecto")
    private ProjectApprovalStepTwo projectApprovalStepTwo;

}

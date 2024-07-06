package pe.edu.unamba.academic.models;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "usuarios")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_usuario", nullable = false, length = 50)
    private String firstName;

    @Column(name = "apellido_usuario", nullable = false, length = 50)
    private String lastName;

    @Column(name = "correo_usuario", nullable = false, length = 50)
    private String email;

    @Column(name = "contrasena_usuario", nullable = false, length = 50)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "id_unidad_investigacion")
    private ResearchUnit researchUnit;
}
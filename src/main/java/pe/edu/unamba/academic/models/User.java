package pe.edu.unamba.academic.models;
import jakarta.persistence.*;
import lombok.Data;
import pe.edu.unamba.academic.models.ResearchUnit;
import pe.edu.unamba.academic.models.actors.Rol;

@Entity
@Table(name = "usuarios")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "username", nullable = false, length = 150, unique = true)
    private String username;

    @Column(name = "nombre_usuario", nullable = false, length = 50)
    private String firstName;

    @Column(name = "apellido_usuario", nullable = false, length = 50)
    private String lastName;

    @Column(name = "correo_usuario", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "contrasena_usuario", nullable = false, length = 150)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;


    // 1: activo, 0: inactivo
    @Column(name = "estado", nullable = false, columnDefinition = "boolean default true")
    private Boolean state;

    @Column(name = "primer_login", nullable = true, columnDefinition = "boolean default false")
    private Boolean firstLogin;


    @ManyToOne
    @JoinColumn(name = "id_unidad_investigacion")
    private ResearchUnit researchUnit;
}
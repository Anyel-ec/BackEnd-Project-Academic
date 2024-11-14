package pe.edu.unamba.academic.models.steps;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.edu.unamba.academic.models.actors.Teacher;
import java.sql.Timestamp;

@Entity
@Table(name = "p2_aprobacion_proyecto")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Slf4j
public class ProjectApprovalStepTwo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aprobacion_proyecto")
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservacion_titulo", referencedColumnName = "id_reservacion_titulo")
    private TitleReservationStepOne titleReservationStepOne;

    @ManyToOne
    @JoinColumn(name = "asesor")
    private Teacher adviser;

    @ManyToOne
    @JoinColumn(name = "coasesor")
    private Teacher coadviser;

    @Column(name = "eliminado")
    private Boolean isDisable = false;

    @Column(name="proyecto_aprobado")
    private boolean approvedProject = false;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT", nullable = true)
    private String observations;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}

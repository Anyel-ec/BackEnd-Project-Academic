package pe.edu.unamba.academic.models.steps;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.edu.unamba.academic.models.actors.Teacher;
import java.sql.Timestamp;


@Entity
@Table(name = "p3_desigacion_titulo")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Slf4j
public class JuryAppointmentStepThree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_desigacion_titulo")
    private Long id;

    @OneToOne
    @JoinColumn(name = "aprobacion_titulo", referencedColumnName = "id_aprobacion_proyecto")
    private ProjectApprovalStepTwo projectApprovalStepTwo;

    @ManyToOne
    @JoinColumn(name = "presidente")
    private Teacher president;

    @ManyToOne
    @JoinColumn(name = "primer_miembro")
    private Teacher firstMember;

    @ManyToOne
    @JoinColumn(name = "segundo_miembro")
    private Teacher SecondMember;

    @ManyToOne
    @JoinColumn(name = "accesitario")
    private Teacher accessory;

    @Column(name = "eliminado")
    private Boolean isDisable = false;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT", nullable = true)
    private String observations;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}

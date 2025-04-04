package pe.edu.unamba.academic.models.steps;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.edu.unamba.academic.models.HasUpdatedAt;
import pe.edu.unamba.academic.models.actors.Teacher;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "p2_aprobacion_proyecto")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Slf4j
public class ProjectApprovalStepTwo implements HasUpdatedAt {
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

    @Column(name = "cumple_requerimientos")
    private Boolean meetRequirements = false;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT", nullable = true)
    private String observations;

    @Column(name = "numero_registro")
    private Integer registrationNumber;

    @Column(name = "fecha_referencia")
    private java.sql.Date referenceDate;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
    @Override
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
}

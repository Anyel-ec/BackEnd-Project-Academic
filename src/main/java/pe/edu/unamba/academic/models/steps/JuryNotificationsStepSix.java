package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Table(name="p6_notificacion_jurados")
public class JuryNotificationsStepSix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion_jurados")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_constancia_tesis", referencedColumnName = "id_constancia_tesis")
    private ConstancyThesisStepFive constancyThesisStepFive;

    @Column(name = "eliminado")
    private Boolean isDisable = false;

    @Column(name = "cumple_requerimientos")
    private boolean meetRequirements = false;

    @Column(name = "fecha_tesis")
    private LocalDate thesisDate;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT")
    private String observations;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}

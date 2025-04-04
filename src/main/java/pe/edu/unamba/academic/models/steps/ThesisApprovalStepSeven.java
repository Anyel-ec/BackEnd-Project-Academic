package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import pe.edu.unamba.academic.models.HasUpdatedAt;

import java.sql.Timestamp;

@Data
@Entity
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Table(name="p7_aprobacion_tesis")
public class ThesisApprovalStepSeven implements HasUpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aprobacion_tesis")
    private Long id;

    @Column(name = "numero_registro")
    private Integer registrationNumber;

    @Column(name = "numero_informe")
    private Integer reportNumber;

    @Column(name = "numero_memorandum_multiple")
    private String multipleMemorandumNumber ;

    @OneToOne
    @JoinColumn(name = "notificacion_jurados", referencedColumnName = "id_notificacion_jurados")
    private JuryNotificationsStepSix juryNotificationsStepSix;

    @Column(name = "eliminado")
    private Boolean isDisable = false;

    @Column(name = "cumple_requerimientos")
    private boolean meetRequirements = false;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT")
    private String observations;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
    @Override
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
}

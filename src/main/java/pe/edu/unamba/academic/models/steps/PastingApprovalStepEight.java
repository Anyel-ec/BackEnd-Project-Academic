package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import pe.edu.unamba.academic.models.HasUpdatedAt;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Table(name = "p8_aprobacion_empastados")
public class PastingApprovalStepEight implements HasUpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_aprobacion_tesis" , referencedColumnName= "id_aprobacion_tesis")
    private ThesisApprovalStepSeven thesisApprovalStepSeven;

    @Column(name = "cumple_requerimientos")
    private boolean meetRequirements = false;

    @Column(name = "eliminado")
    private boolean isDisable = false;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT")
    private String observations;

    @Column(name = "resolucion_decanal")
    private Integer deanResolution;

    @Column(name = "numero_registro")
    private Integer registrationNumber;

    @Column(name = "numero_articulo")
    private Integer articleNumber;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
    @Override
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
}

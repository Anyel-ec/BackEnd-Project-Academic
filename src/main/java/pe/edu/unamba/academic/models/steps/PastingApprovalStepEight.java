package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Table(name = "p8_aprobacion_empastados")
public class PastingApprovalStepEight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_aprobacion_tesis" , referencedColumnName= "id_aprobacion_tesis")
    private ThesisApprovalStepSeven thesisApprovalStepSeven;

    @Column(name = "cumple_requisitos")
    private boolean meetRequirements = false;

    @Column(name = "eliminado")
    private boolean isDisable = false;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT")
    private String observations;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}

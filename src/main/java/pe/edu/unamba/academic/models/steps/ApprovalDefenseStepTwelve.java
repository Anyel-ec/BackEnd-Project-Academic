package pe.edu.unamba.academic.models.steps;

import jakarta.persistence.*;
import lombok.Data;
import pe.edu.unamba.academic.models.ThesisAdvisory;

import java.sql.Timestamp;
@Data
@Entity
@Table(name = "p12_aprobacion_sustentacion")
public class ApprovalDefenseStepTwelve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aprobacion_sustentacion")
    private Long id;

    @Column(name = "cumple_requerimientos", nullable = false)
    private boolean meetsRequirements;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observations;

}

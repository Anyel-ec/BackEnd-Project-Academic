package pe.edu.unamba.academic.models.steps;

import jakarta.persistence.*;
import lombok.Data;
import pe.edu.unamba.academic.models.ThesisAdvisory;

import java.sql.Timestamp;
@Data
@Entity
@Table(name = "p7_ultima_revision")
public class LastRevisionStepSeven {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ultima_revision")
    private Long id;

    @Column(name = "cumple_requerimientos", nullable = false)
    private boolean meetsRequirements;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observations;

    @OneToOne
    @JoinColumn(name = "id_asesoria_tesis")
    private ThesisAdvisory thesisAdvisory;

}

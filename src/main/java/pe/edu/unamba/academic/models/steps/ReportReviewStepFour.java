package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.edu.unamba.academic.models.HasUpdatedAt;

import java.sql.Timestamp;

@Entity
@Table(name = "p4_revision_reporte")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Slf4j
public class ReportReviewStepFour implements HasUpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_revision_reporte")
    private Long id;

    @OneToOne
    @JoinColumn(name="designacion_jurado", referencedColumnName = "id_designacion_jurado")
    private JuryAppointmentStepThree juryAppointmentStepThree;

    @Column(name = "cumple_requerimientos")
    private boolean meetRequirements;

    @Column(name = "eliminado", columnDefinition = "boolean default false")
    private boolean isDisable = false;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT", nullable = true)
    private String observations;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
    @Override
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
}

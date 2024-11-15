package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Entity
@Table(name = "p5_constancia_tesis")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Slf4j
public class ConstancyThesisStepFive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_constancia_tesis")
    private Long id;

    @OneToOne
    @JoinColumn(name = "pdf_constancia_tesis", referencedColumnName = "id_pdf_constancia_tesis")
    private PdfDocumentStepFive pdfDocumentStepFive;

    @OneToOne
    @JoinColumn(name = "revision_reporte", referencedColumnName = "id_revision_reporte")
    private ReportReviewStepFour reportReviewStepFour;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT", nullable = true)
    private String observations;

    @Column(name = "cumple_requerimientos", columnDefinition = "boolean default false")
    private boolean meetsRequirements = false;

    @Column(name = "eliminado", columnDefinition = "boolean default false")
    private boolean isDisable = false;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;
}

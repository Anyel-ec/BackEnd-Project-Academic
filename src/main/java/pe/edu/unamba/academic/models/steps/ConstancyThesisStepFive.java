package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.edu.unamba.academic.models.HasUpdatedAt;

import java.sql.Timestamp;

@Entity
@Table(name = "p5_constancia_tesis")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Slf4j
public class ConstancyThesisStepFive implements HasUpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_constancia_tesis")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pdf_document_id", referencedColumnName = "id_pdf_constancia_tesis")
    private PdfDocumentStepFive pdfDocument;

    @OneToOne
    @JoinColumn(name = "revision_reporte", referencedColumnName = "id_revision_reporte")
    private ReportReviewStepFour reportReviewStepFour;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT")
    private String observations;

    @Column(name = "cumple_requerimientos")
    private boolean meetsRequirements = false;

    @Column(name = "eliminado", columnDefinition = "boolean default false")
    private boolean isDisable;
    @Column(name = "numero_registro")
    private Integer registrationNumber;    @Column(name = "numero_solicitud")
    private Integer aplicationNumber;
    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;
    @Override
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
}

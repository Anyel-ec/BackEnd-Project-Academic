    package pe.edu.unamba.academic.models.steps;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import jakarta.persistence.*;
    import lombok.Data;
    import lombok.extern.slf4j.Slf4j;

    import java.sql.Timestamp;

    @Entity
    @Data
    @JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
    @Slf4j
    @Table(name = "documentos_paso_1")
    public class PdfDocumentStepOne {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "pdf_document_id")
        private Long id;

        // Almacena el PDF en formato Base64 (texto)
        @Column(name = "pdf_data", columnDefinition = "LONGTEXT")
        private String pdfData;

        // Creado en, con marca de tiempo automática
        @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        private Timestamp createdAt;

        // Actualizado en, con actualización automática
        @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
        private Timestamp updatedAt;
    }

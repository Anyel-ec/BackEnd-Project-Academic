package pe.edu.unamba.academic.models.steps;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class PdfDocumentStepFive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pdf_constancia_tesis")
    private Long id;

    // Almacena el PDF en formato Base64 (texto)
    @Column(name = "pdf_informacion", columnDefinition = "LONGTEXT")
    private String pdfData;

    // Creado en, con marca de tiempo automática
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    // Actualizado en, con actualización automática
    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}

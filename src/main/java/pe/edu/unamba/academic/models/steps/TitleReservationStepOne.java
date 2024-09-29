package pe.edu.unamba.academic.models.steps;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Text;
import pe.edu.unamba.academic.models.actors.Student;
import java.sql.Timestamp;

@Entity
@Table(name = "p1_reserva_titulo")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
@Slf4j
public class TitleReservationStepOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva_titulo")
    private Long id;


    @Column(name = "cumple_requerimientos", columnDefinition = "boolean default false")
    private boolean meetsRequirements = false;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "id_student")
    private Student student;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "id_student_two")
    private Student studentTwo;

    @Column(name = "proyecto_tesis")
    private boolean project;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT", nullable = true)
    private String observations;

    @ManyToOne
    @JoinColumn(name = "pdf_document_id")
    private PDFDocumentStepOne pdfDocument;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;
}

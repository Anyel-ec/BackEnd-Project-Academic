package pe.edu.unamba.academic.models.steps;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.edu.unamba.academic.models.HasUpdatedAt;
import pe.edu.unamba.academic.models.actors.LineResearch;
import pe.edu.unamba.academic.models.actors.Student;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "p1_reservacion_titulo")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Slf4j
public class TitleReservationStepOne implements HasUpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservacion_titulo")
    private Long id;

    @Column(name = "cumple_requerimientos", columnDefinition = "boolean default false")
    private boolean meetsRequirements = false;

    @Column(name = "numero_registro")
    private Integer registrationNumber;

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

    @Column(name = "titulo", nullable = true)
    private String title;

    @OneToOne
    @JoinColumn(name = "id_documento_pdf")
    private PdfDocumentStepOne pdfDocument;

    @ManyToOne
    @JoinColumn(name = "id_linea_de_reserva", nullable = true)
    private LineResearch lineOfResearch;

    @DecimalMin(value = "0.00", inclusive = true, message = "La similitud no puede ser menor que 0")
    @DecimalMax(value = "25.00", inclusive = true, message = "La similitud no puede ser mayor que 25")
    @Column(name = "similitud_de_proyecto", precision = 10, scale = 2)
    private BigDecimal projectSimilarity = BigDecimal.ZERO;

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

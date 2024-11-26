package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "step_status")
@JsonIgnoreProperties(value = "createdAt")
public class StepStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_alumno", nullable = false)
    private String studentCode;

    @Column(name = "step_number", nullable = false)
    private int stepNumber;

    @Column(name = "completado", nullable = false)
    private boolean completed;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

}

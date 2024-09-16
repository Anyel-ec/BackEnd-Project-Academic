package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import pe.edu.unamba.academic.models.actors.Student;

import java.sql.Timestamp;

@Entity
@Table(name = "p1_reserva_titulo")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class TitleReservationStepOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva_titulo")
    private Long id;

    @Column(name = "cumple_requerimientos", columnDefinition = "boolean default false")
    private boolean meetsRequirements = false;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = false)
    private Student student;

    @Column(name = "proyecto_tesis", nullable = false)
    private boolean project;

    @Column(name = "observaciones", columnDefinition = "TEXT", nullable = true)
    private String observations;


    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;

}

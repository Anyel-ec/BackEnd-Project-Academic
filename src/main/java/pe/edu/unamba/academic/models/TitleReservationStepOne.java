package pe.edu.unamba.academic.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "p1_reserva_titulo")
@Data
public class TitleReservationStepOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva_titulo")
    private Long id;

    @Column(name = "cumple_requerimientos", nullable = false)
    private boolean meetsRequirements;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observations;
}

package pe.edu.unamba.academic.models;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Table(name = "anio_planteamiento_tesis")
@Data
public class YearThesisProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anio_planteamiento_tesis")
    private Long id;

    @Column(name = "anio", nullable = false)
    private LocalDate year;

    @Column(name = "estado", nullable = false, columnDefinition = "BOOLEAN COMMENT '0: cerrado, 1: abierto'")
    private boolean isOpen;
}
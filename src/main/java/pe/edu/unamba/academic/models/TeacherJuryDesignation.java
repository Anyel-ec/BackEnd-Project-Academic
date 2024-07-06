package pe.edu.unamba.academic.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "docente_jurado")
public class TeacherJuryDesignation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente_designacion_jurados")
    private Long id;

    @Column(name = "id_cargo", nullable = false)
    private Long cargoId;

    @Column(name = "id_docente", nullable = false)
    private Long docenteId;

    @Column(name = "id_designacion_jurados", nullable = false)
    private Long designacionJuradosId;

}

package pe.edu.unamba.academic.models.actors;

import jakarta.persistence.*;
import lombok.Data;
import pe.edu.unamba.academic.models.Position;
import pe.edu.unamba.academic.models.actors.Teacher;
import pe.edu.unamba.academic.models.steps.JuryDesignationStepFour;

@Data
@Entity
@Table(name = "docente_jurado")
public class TeacherJuryDesignation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente_designacion_jurados")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Position positionId;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Teacher teacherId;

    @ManyToOne
    @JoinColumn(name = "id_designacion_jurados")
    private JuryDesignationStepFour juryDesignationStepFourId;

}

package pe.edu.unamba.academic.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "alumno_carrera")
@Data
public class StudentCareer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Career career;
}
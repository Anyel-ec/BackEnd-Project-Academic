package pe.edu.unamba.academic.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "docente_carrera")
@Data
public class TeacherCareer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Career career;
}
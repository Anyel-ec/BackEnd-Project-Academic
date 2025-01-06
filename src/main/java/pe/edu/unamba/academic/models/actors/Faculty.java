package pe.edu.unamba.academic.models.actors;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "facultades")
@Data
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_facultad", nullable = false, length = 50)
    private String nameFaculty;

    @Column(name = "decano_facultad")
    private String deanFacultyName;
}
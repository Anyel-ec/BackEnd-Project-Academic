package pe.edu.unamba.academic.models;
import jakarta.persistence.*;
import lombok.Data;
import pe.edu.unamba.academic.models.actors.Faculty;

@Entity
@Table(name = "unidad_investigacion")
@Data
public class ResearchUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_unidad_investigacion", nullable = false, length = 50)
    private String name;

    @OneToOne
    @JoinColumn(name = "id_facultad")
    private Faculty faculty;
}
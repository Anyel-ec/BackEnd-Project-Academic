package pe.edu.unamba.academic.models.actors;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carreras")
@Data
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_carrera", nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_facultad")
    private Faculty faculty;
}
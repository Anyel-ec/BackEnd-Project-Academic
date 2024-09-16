package pe.edu.unamba.academic.models.actors;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "linea_investigacion")
public class LineResearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_linea_investigacion", nullable = false, length = 50)
    private String name;

}

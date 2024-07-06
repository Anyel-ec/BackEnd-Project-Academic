package pe.edu.unamba.academic.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "facultades")
@Data
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_facultad", nullable = false, length = 50)
    private String nombreFacultad;
}

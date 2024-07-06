package pe.edu.unamba.academic.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String name;

    public static class Faculty {
    }

    @Entity
    @Table(name = "cargos")
    @Data
    public static class Position {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nombre_cargo", nullable = false, length = 50)
        private String name;
    }
}

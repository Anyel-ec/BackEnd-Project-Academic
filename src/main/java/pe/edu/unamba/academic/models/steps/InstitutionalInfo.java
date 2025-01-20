package pe.edu.unamba.academic.models.steps;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "informacion_institucional")
public class InstitutionalInfo {
    @Id
    private Long id = 1L;

    @Column(name = "nombre_decano", nullable = false)
    private String deanName;

    @Column(name = "texto_conmemorativo", nullable = false)
    private String commemorativeText;

}

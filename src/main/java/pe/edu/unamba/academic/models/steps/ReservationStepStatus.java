package pe.edu.unamba.academic.models.steps;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "progreso")
public class ReservationStepStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code")
    private String studentCode;

    @Column(name = "step_number")
    private Integer stepNumber;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Transient  // Este campo no será persistido en la base de datos
    private Integer percentage;  // Agregado para el cálculo de progreso
}

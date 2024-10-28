package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.edu.unamba.academic.models.actors.Teacher;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "p1_reserva_titulo")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Slf4j
public class ProjectApprovalStepTwo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aprobacion_proyecto")
    private Long id;

    @OneToOne
    @JoinColumn(name = "reserva_titulo", referencedColumnName = "id_reserva_titulo")
    private TitleReservationStepOne titleReservationStepOne;

    @OneToMany(mappedBy = "projectApprovalStepTwo") // Nombre del campo en Teacher
    private List<Teacher> advisers;

    @Column(name = "eliminado")
    private boolean isDisable;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT", nullable = true)
    private String observations;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}

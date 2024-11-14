package pe.edu.unamba.academic.models.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "p4_revision_reporte")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Slf4j
public class ReportReviewStepFour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_revision_reporte")
    private Long id;

    @OneToOne
    @JoinColumn(name="designacion_jurado", referencedColumnName = "id_designacion_jurado")
    private JuryAppointmentStepThree juryAppointmentStepThree;

    @Column(name = "cumple_requerimientos")
    private boolean meetRequirements;

}

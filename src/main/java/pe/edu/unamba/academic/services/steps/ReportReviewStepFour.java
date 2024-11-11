package pe.edu.unamba.academic.services.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;

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

}

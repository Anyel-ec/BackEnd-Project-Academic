package pe.edu.unamba.academic.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pe.edu.unamba.academic.models.actors.TeacherCareer;
import pe.edu.unamba.academic.models.steps.*;

import java.util.Date;
@Data
@Entity
@Table(name = "asesoria_tesis")
public class ThesisAdvisory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asesoria_tesis")
    private Long id;

    @NotBlank
    @Column(name = "co_asesor", nullable = true, length = 200)
    private String coAdviser;

    @NotBlank
    @Column(name = "titulo_tesis", nullable = false, length = 250)
    private String thesisTitle;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private Date startDate;

    @Column(name = "fecha_ampliacion")
    private Date extensionDate;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "id_reserva_titulo", nullable = true)
    private TitleReservationStepOne reservationTitle;

    @OneToOne
    @JoinColumn(name = "id_constancia_filtro", nullable = true)
    private CertificateFilterStepTwo filterCertificate;

    @OneToOne
    @JoinColumn(name = "id_aprobacion_tesis", nullable = true)
    private ThesisApprovalStepThree thesisApproval;

    @OneToOne
    @JoinColumn(name = "id_designacion_jurados", nullable = true)
    private JuryDesignationStepFour juryDesignation;

    @OneToOne
    @JoinColumn(name = "id_recomposicion_jurados", nullable = true)
    private JuryRecompositionStepFive juryRecomposition;

    @OneToOne
    @JoinColumn(name = "id_primera_revision", nullable = true)
    private FirstRevisionStepSix firstReview;

    @OneToOne
    @JoinColumn(name = "id_ultima_revision", nullable = true)
    private LastRevisionStepSeven lastReview;

    @OneToOne
    @JoinColumn(name = "id_ampliacion_plazo", nullable = true)
    private ExtensionPeriodStepEight extensionPeriod;

    @OneToOne
    @JoinColumn(name = "id_constancia_sustentacion", nullable = true)
    private DefenseCertificateStepNine presentationCertificate;

    @OneToOne
    @JoinColumn(name = "id_fecha_sustentacion", nullable = true)
    private DefenseDateStepTen presentationDate;

    @OneToOne
    @JoinColumn(name = "id_notificacion_jurados", nullable = true)
    private NotificationJurorsStepEleven juryNotification;

    @OneToOne
    @JoinColumn(name = "id_aprobacion_sustentacion", nullable = true)
    private ApprovalDefenseStepTwelve presentationApproval;

    @OneToOne
    @JoinColumn(name = "id_constancia_entrega_empastados", nullable = true)
    private DeliveryCertificatesStepThirteen bindingDeliveryCertificate;

    @ManyToOne
    @JoinColumn(name = "id_docente_carrera", nullable = false)
    private TeacherCareer teacherCareer;


}

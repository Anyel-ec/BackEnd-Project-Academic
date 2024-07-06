package pe.edu.unamba.academic.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "asesoria_tesis")
public class ThesisAdvisory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asesoria_tesis")
    private Long id;

    @Column(name = "co_asesor", nullable = false, length = 200)
    private String coAdviser;

    @Column(name = "titulo_tesis", nullable = false, length = 250)
    private String thesisTitle;

    @Column(name = "fecha_inicio", nullable = false)
    private Date startDate;

    @Column(name = "fecha_ampliacion")
    private Date extensionDate;

    @Column(name = "fecha_fin", nullable = false)
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "id_reserva_titulo")
    private TitleReservationStepOne reservationTitle;

    @OneToOne
    @JoinColumn(name = "id_constancia_filtro")
    private CertificateFilterStepTwo filterCertificate;

    @OneToOne
    @JoinColumn(name = "id_aprobacion_tesis")
    private ThesisApprovalStepThree thesisApproval;

    @OneToOne
    @JoinColumn(name = "id_designacion_jurados")
    private JuryDesignationStepFour juryDesignation;

    @OneToOne
    @JoinColumn(name = "id_recomposicion_jurados")
    private JuryRecompositionStepFive juryRecomposition;

    @OneToOne
    @JoinColumn(name = "id_primera_revision")
    private FirstRevisionStepSix firstReview;

    @OneToOne
    @JoinColumn(name = "id_ultima_revision")
    private LastRevisionStepSeven lastReview;

    @OneToOne
    @JoinColumn(name = "id_ampliacion_plazo")
    private ExtensionPeriodStepEight extensionPeriod;

    @OneToOne
    @JoinColumn(name = "id_constancia_sustentacion")
    private DefenseCertificateStepNine presentationCertificate;

    @OneToOne
    @JoinColumn(name = "id_fecha_sustentacion")
    private DefenseDateStepTen presentationDate;

    @OneToOne
    @JoinColumn(name = "id_notificacion_jurados")
    private NotificationJurorsStepEleven juryNotification;

    @OneToOne
    @JoinColumn(name = "id_aprobacion_sustentacion")
    private ApprovalDefenseStepTwelve presentationApproval;

    @OneToOne
    @JoinColumn(name = "id_constancia_entrega_empastados")
    private DeliveryCertificatesStepThirteen bindingDeliveryCertificate;

    @ManyToOne
    @JoinColumn(name = "id_docente_carrera")
    private TeacherCareer teacherCareer;

    @ManyToOne
    @JoinColumn(name = "id_alumno_carrera")
    private StudentCareer studentCareer;

}

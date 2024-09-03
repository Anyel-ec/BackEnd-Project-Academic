package pe.edu.unamba.academic.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import pe.edu.unamba.academic.models.steps.*;


@Data
@Entity
@Table(name = "informe_progreso")
public class ProgressReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informe_progreso")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_anio_planteamiento_tesis")
    private YearThesisProposal yearProposal;

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
}

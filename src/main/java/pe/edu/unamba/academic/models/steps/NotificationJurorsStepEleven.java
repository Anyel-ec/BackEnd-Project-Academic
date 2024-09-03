package pe.edu.unamba.academic.models.steps;

import jakarta.persistence.*;
import lombok.Data;
import pe.edu.unamba.academic.models.ThesisAdvisory;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "p11_notificacion_jurados")
public class NotificationJurorsStepEleven {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion_jurados")
    private Long id;

    @Column(name = "fecha", nullable = false)
    private Date date;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observations;

    @OneToOne
    @JoinColumn(name = "id_asesoria_tesis")
    private ThesisAdvisory thesisAdvisory;

}

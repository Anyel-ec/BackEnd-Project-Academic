package pe.edu.unamba.academic.models.steps;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Entity
@Table(name = "pe3_cambio_asesor")
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Slf4j
public class JuryRecomposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cambio_asesor")
    private int id;

    @OneToOne
    @JoinColumn(name = "aprobacion_proyecto", referencedColumnName = "id_aprobacion_proyecto")
    private ProjectApprovalStepTwo projectApprovalStepTwo;

    @Column(name = "observaciones", columnDefinition = "LONGTEXT")
    private String observations;

    @Column(name = "cumple_requerimientos")
    private Boolean meetsRequirements = false;

    @Column(name = "eliminado", columnDefinition = "boolean default false")
    private boolean isDisable;

    @Column(name = "creado_en", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @Column(name = "actualizado_en", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;
}
package pe.edu.unamba.academic.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "constancia_filtro_numero")
@Data
public class FilterCertificateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_constancia_filtro_numero")
    private Long id;

    @Column(name = "numero", nullable = false)
    private int number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_constancia_filtro")
    private CertificateFilterStepTwo certificateFilter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anio_planteamiento_tesis")
    private YearThesisProposal yearThesisProposal;
}

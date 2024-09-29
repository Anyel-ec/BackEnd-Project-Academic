package pe.edu.unamba.academic.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
@Data

public class StepThesis {
    @Id
    private Long id;


    private String codigo_estudiante;
    private boolean paso1;
    private boolean paso2;
    private boolean paso3;
    private boolean paso4;
    private boolean paso5;
    private boolean paso6;
    private boolean paso7;
    private boolean paso8;
    @CreationTimestamp
    private LocalDateTime creation_date;
    private String comentaros;


}

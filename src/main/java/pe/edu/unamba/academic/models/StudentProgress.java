package pe.edu.unamba.academic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProgress {
    private String stepName;
    private boolean isCompleted;
    private double completionPercentage; // Rango de 0.0 a 100.0
    private Object stepObject; // Aquí se almacena el objeto completo del paso

}

package pe.edu.unamba.academic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProgress {
    private String stepNumber;
    private boolean isCompleted;
    private double completionPercentage; // Rango de 0.0 a 100.0
    private Object stepObject; // Solo incluye datos necesarios
}


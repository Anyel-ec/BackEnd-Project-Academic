package pe.edu.unamba.academic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProgress {
    private String stepNumber;
    private boolean isCompleted;
    private double completionPercentage;
    private HasUpdatedAt stepObject; // Solo tiene acceso a updatedAt
}



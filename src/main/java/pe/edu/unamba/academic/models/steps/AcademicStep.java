package pe.edu.unamba.academic.models.steps;

import java.sql.Timestamp;

public interface AcademicStep {
    Boolean isMeetsRequirements(); // Asegúrate de que esto esté definido
    Timestamp getUpdatedAt();      // Suponiendo que también puedas necesitar esto
}
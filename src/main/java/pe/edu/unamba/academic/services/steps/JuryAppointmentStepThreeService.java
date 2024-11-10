package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;
import pe.edu.unamba.academic.repositories.steps.JuryAppointmentStepThreeRepository;
import pe.edu.unamba.academic.repositories.steps.ProjectApprovalStepTwoRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class JuryAppointmentStepThreeService {
    private final JuryAppointmentStepThreeRepository juryAppointmentStepThreeRepository;
    private final ProjectApprovalStepTwoRepository projectApprovalStepTwoRepository;

    public List<JuryAppointmentStepThree> getAllJuryAppointment() {
        return juryAppointmentStepThreeRepository.findAll();
    }

    public Optional<JuryAppointmentStepThree> getJuryAppointmentById(Long id) {
        return juryAppointmentStepThreeRepository.findById(id);
    }
    public JuryAppointmentStepThree createJuryAppointment(JuryAppointmentStepThree juryAppointmentStepThree) {
        return juryAppointmentStepThreeRepository.save(juryAppointmentStepThree);
    }

    public Optional<JuryAppointmentStepThree> updateJuryAppointment(Long id, JuryAppointmentStepThree updatedJuryAppointment) {
        return juryAppointmentStepThreeRepository.findById(id).map(jury -> {
            if (updatedJuryAppointment.getProjectApprovalStepTwo() != null) {
                jury.setProjectApprovalStepTwo(updatedJuryAppointment.getProjectApprovalStepTwo());
            }
            if (updatedJuryAppointment.getPresident() != null) {
                jury.setPresident(updatedJuryAppointment.getPresident());
            }
            if (updatedJuryAppointment.getFirstMember() != null) {
                jury.setFirstMember(updatedJuryAppointment.getFirstMember());
            }
            if (updatedJuryAppointment.getSecondMember() != null) {
                jury.setSecondMember(updatedJuryAppointment.getSecondMember());
            }
            if (updatedJuryAppointment.getAccessory() != null) {
                jury.setAccessory(updatedJuryAppointment.getAccessory());
            }
            if (updatedJuryAppointment.getIsDisable() != null) {
                jury.setIsDisable(updatedJuryAppointment.getIsDisable());
            }
            if (updatedJuryAppointment.getObservations() != null) {
                jury.setObservations(updatedJuryAppointment.getObservations());
            }
            return juryAppointmentStepThreeRepository.save(jury);
        });
    }
    public boolean deleteJuryAppointment(Long id) {
        if(juryAppointmentStepThreeRepository.existsById(id)) {
            juryAppointmentStepThreeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

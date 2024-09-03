package pe.edu.unamba.academic.services.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.NotificationJurorsStepEleven;
import pe.edu.unamba.academic.repositories.steps.NotificationJurorsStepElevenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationJurorsStepElevenService {
    @Autowired
    private NotificationJurorsStepElevenRepository notificationJurorsStepElevenRepository;

    public List<NotificationJurorsStepEleven> getAllNotifications() {
        return notificationJurorsStepElevenRepository.findAll();
    }

    public Optional<NotificationJurorsStepEleven> getNotificationById(Long id) {
        return notificationJurorsStepElevenRepository.findById(id);
    }

    public NotificationJurorsStepEleven saveNotification(NotificationJurorsStepEleven notification) {
        return notificationJurorsStepElevenRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationJurorsStepElevenRepository.deleteById(id);
    }

    public NotificationJurorsStepEleven updateNotification(Long id, NotificationJurorsStepEleven notificationDetails) {
        NotificationJurorsStepEleven notification = notificationJurorsStepElevenRepository.findById(id).get();
        notification.setDate(notificationDetails.getDate());
        notification.setObservations(notificationDetails.getObservations());
        return notificationJurorsStepElevenRepository.save(notification);
    }
}

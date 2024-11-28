package pe.edu.unamba.academic.models;

import lombok.Data;

import java.util.Date;


@Data
public class StudentProgressUpdates {
    private Date titleReservationUpdated;
    private Date projectApprovalUpdated;
    private Date juryAppointmentUpdated;
    private Date reportReviewUpdated;
    private Date constancyThesisUpdated;
    private Date juryNotificationsUpdated;
    private Date thesisApprovalUpdated;
    private Date pastingApprovalUpdated;

}

package pe.edu.unamba.academic.services;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.actors.Teacher;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    private static final Logger log = (Logger) LoggerFactory.getLogger(EmailService.class);

    public void sendPassword(String to, String subject, String firstName, String username, String password) {
        try {
            String htmlContent = new String(Files.readAllBytes(Paths.get(new ClassPathResource("templates/emailTemplate.html").getURI())));
            htmlContent = htmlContent.replace("{{firstName}}", firstName);
            htmlContent = htmlContent.replace("{{username}}", username);
            htmlContent = htmlContent.replace("{{password}}", password);

            var mimeMessage = ((JavaMailSenderImpl) emailSender).createMimeMessage();
            var mimeMessageHelper = new org.springframework.mail.javamail.MimeMessageHelper(mimeMessage, "utf-8");

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(htmlContent, true);

            emailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendJurys(String to, String subject, String firstName, String username, Teacher president, Teacher firstMember, Teacher secondMember, Teacher accessory) {
        try {
            String htmlContent = new String(Files.readAllBytes(Paths.get(new ClassPathResource("templates/juryTemplate.html").getURI())));

            // Asegúrate de que los valores no sean null, asignando un valor predeterminado en caso de que lo sean y agregando logs para depuración
            if (firstName == null) {
                firstName = "Estudiante";
                log.warn("firstName es null, se asigna el valor predeterminado.");
            }
            if (username == null) {
                username = "No asignado";
                log.warn("username es null, se asigna el valor predeterminado.");
            }
            String presidentName = (president != null && president.getFirstNames() != null) ? president.getFirstNames() : "No asignado";
            if (presidentName.equals("No asignado")) {
                log.warn("president o president.getFirstNames() es null, se asigna el valor predeterminado.");
            }
            String firstMemberName = (firstMember != null && firstMember.getFirstNames() != null) ? firstMember.getFirstNames() : "No asignado";
            if (firstMemberName.equals("No asignado")) {
                log.warn("firstMember o firstMember.getFirstNames() es null, se asigna el valor predeterminado.");
            }
            String secondMemberName = (secondMember != null && secondMember.getFirstNames() != null) ? secondMember.getFirstNames() : "No asignado";
            if (secondMemberName.equals("No asignado")) {
                log.warn("secondMember o secondMember.getFirstNames() es null, se asigna el valor predeterminado.");
            }
            String accessoryName = (accessory != null && accessory.getFirstNames() != null) ? accessory.getFirstNames() : "No asignado";
            if (accessoryName.equals("No asignado")) {
                log.warn("accessory o accessory.getFirstNames() es null, se asigna el valor predeterminado.");
            }

            // Realiza los reemplazos usando los valores predeterminados
            htmlContent = htmlContent.replace("{{firstName}}", firstName);
            htmlContent = htmlContent.replace("{{username}}", username);
            htmlContent = htmlContent.replace("{{president}}", presidentName);
            htmlContent = htmlContent.replace("{{firstMember}}", firstMemberName);
            htmlContent = htmlContent.replace("{{secondMember}}", secondMemberName);
            htmlContent = htmlContent.replace("{{accessory}}", accessoryName);

            var mimeMessage = ((JavaMailSenderImpl) emailSender).createMimeMessage();
            var mimeMessageHelper = new org.springframework.mail.javamail.MimeMessageHelper(mimeMessage, "utf-8");

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(htmlContent, true);

            emailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Error al enviar el correo: ", e);
            e.printStackTrace();
        }
    }

}
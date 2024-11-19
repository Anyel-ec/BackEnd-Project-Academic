package pe.edu.unamba.academic.services;

import ch.qos.logback.classic.Logger;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
@RequiredArgsConstructor
@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    private static final Logger log = (Logger) LoggerFactory.getLogger(EmailService.class);

    // Enviar contraseña
    public void sendPassword(String to, String subject, String firstName, String username, String password) {
        try {
            // Cargar plantilla de correo
            String htmlContent = loadEmailTemplate("templates/emailTemplate.html");

            // Reemplazar valores en la plantilla
            htmlContent = htmlContent.replace("{{firstName}}", defaultIfNull(firstName, "Usuario", "firstName"));
            htmlContent = htmlContent.replace("{{username}}", defaultIfNull(username, "Usuario", "username"));
            htmlContent = htmlContent.replace("{{password}}", defaultIfNull(password, "Contraseña", "password"));

            // Configurar y enviar el correo
            sendEmail(to, subject, htmlContent);
        } catch (Exception e) {
            log.error("Error al enviar correo de contraseña a: {}", to, e);
        }
    }

    // Enviar información de jurados
    public void sendJurys(String to, String subject, String firstName, String lastName,
                          String namePresident, String nameFirstMember, String nameSecondMember, String nameAccessory) {
        try {
            // Cargar plantilla de correo (si tienes una específica para jurados, úsala aquí)
            String htmlContent = loadEmailTemplate("templates/juryTemplate.html");

            // Reemplazar valores en la plantilla o construir el HTML
            htmlContent = htmlContent.replace("{{firstName}}", defaultIfNull(firstName, "Usuario", "firstName"));
            htmlContent = htmlContent.replace("{{lastName}}", defaultIfNull(lastName, "Apellido", "lastName"));
            htmlContent = htmlContent.replace("{{namePresident}}", defaultIfNull(namePresident, "No asignado", "namePresident"));
            htmlContent = htmlContent.replace("{{nameFirstMember}}", defaultIfNull(nameFirstMember, "No asignado", "nameFirstMember"));
            htmlContent = htmlContent.replace("{{nameSecondMember}}", defaultIfNull(nameSecondMember, "No asignado", "nameSecondMember"));
            htmlContent = htmlContent.replace("{{nameAccessory}}", defaultIfNull(nameAccessory, "No asignado", "nameAccessory"));

            // Configurar y enviar el correo
            sendEmail(to, subject, htmlContent);
        } catch (Exception e) {
            log.error("Error al enviar correo de jurados a: {}", to, e);
        }
    }

    // Método genérico para enviar correos
    private void sendEmail(String to, String subject, String htmlContent) throws Exception {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true para contenido HTML
            emailSender.send(mimeMessage);

            log.info("Correo enviado exitosamente a: {}", to);
        } catch (Exception e) {
            log.error("Error al enviar el correo a: {}", to, e);
            throw e;
        }
    }

    // Método para cargar plantillas HTML
    private String loadEmailTemplate(String templatePath) throws IOException {
        try {
            return new String(Files.readAllBytes(Paths.get(new ClassPathResource(templatePath).getURI())));
        } catch (Exception e) {
            log.error("Error al cargar la plantilla de correo: {}", templatePath, e);
            throw new IOException("No se pudo cargar la plantilla de correo: " + templatePath, e);
        }
    }

    // Método auxiliar para valores predeterminados
    private String defaultIfNull(String value, String defaultValue, String fieldName) {
        if (value == null) {
            log.warn("{} es null, se asigna el valor predeterminado: {}", fieldName, defaultValue);
            return defaultValue;
        }
        return value;
    }
}

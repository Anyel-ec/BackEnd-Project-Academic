package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;


    public void sendEmail(String to, String subject, String firstName, String username, String password) {
        try {
            // Leer el template HTML desde el archivo
            String htmlContent = new String(Files.readAllBytes(Paths.get(new ClassPathResource("templates/emailTemplate.html").getURI())));

            // Reemplazar los placeholders
            htmlContent = htmlContent.replace("{{firstName}}", firstName);
            htmlContent = htmlContent.replace("{{username}}", username);
            htmlContent = htmlContent.replace("{{password}}", password);

            var mimeMessage = ((JavaMailSenderImpl) emailSender).createMimeMessage();
            var mimeMessageHelper = new org.springframework.mail.javamail.MimeMessageHelper(mimeMessage, "utf-8");

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(htmlContent, true); // true indica que es contenido HTML

            emailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar excepción
        }
    }

}
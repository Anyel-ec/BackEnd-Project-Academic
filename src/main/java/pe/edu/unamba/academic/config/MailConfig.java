package pe.edu.unamba.academic.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        Dotenv dotenv = Dotenv.load(); // Carga las variables desde el archivo .env

        // Configuración del JavaMailSender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(dotenv.get("SPRING_MAIL_HOST")); // Host del servidor SMTP
        mailSender.setPort(Integer.parseInt(dotenv.get("SPRING_MAIL_PORT"))); // Puerto del servidor SMTP
        mailSender.setUsername(dotenv.get("SPRING_MAIL_USERNAME")); // Correo del remitente
        mailSender.setPassword(dotenv.get("SPRING_MAIL_PASSWORD")); // Contraseña del remitente

        // Propiedades adicionales para el envío de correos
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_TRANSPORT_PROTOCOL"));
        properties.put("mail.smtp.auth", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH"));
        properties.put("mail.smtp.starttls.enable", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE"));
        properties.put("mail.smtp.ssl.trust", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST"));

        return mailSender;
    }


}

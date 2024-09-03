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
        Dotenv dotenv = Dotenv.load();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(dotenv.get("SPRING_MAIL_HOST"));
        mailSender.setPort(Integer.parseInt(dotenv.get("SPRING_MAIL_PORT")));
        mailSender.setUsername(dotenv.get("SPRING_MAIL_USERNAME"));
        mailSender.setPassword(dotenv.get("SPRING_MAIL_PASSWORD"));
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_TRANSPORT_PROTOCOL"));
        properties.put("mail.smtp.auth", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH"));
        properties.put("mail.smtp.starttls.enable", dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE"));

        return mailSender;
    }
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(dotenv.get("SPRING_MAIL_HOST"));
        mailSender.setPort(Integer.parseInt(dotenv.get("SPRING_MAIL_PORT")));
        mailSender.setUsername(dotenv.get("SPRING_MAIL_USERNAME"));
        mailSender.setPassword(dotenv.get("SPRING_MAIL_PASSWORD"));
        System.out.println(mailSender.getHost());
        System.out.println(mailSender.getPort());
        System.out.println(mailSender.getUsername());
        System.out.println(mailSender.getPassword());
    }
}


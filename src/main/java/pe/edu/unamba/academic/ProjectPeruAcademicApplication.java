package pe.edu.unamba.academic;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectPeruAcademicApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();

        String databaseUrl = dotenv.get("DATABASE_URL");
        String databaseUsername = dotenv.get("DATABASE_USERNAME");
        String databasePassword = dotenv.get("DATABASE_PASSWORD");

        System.setProperty("DATABASE_URL", databaseUrl);
        System.setProperty("DATABASE_USERNAME", databaseUsername);
        System.setProperty("DATABASE_PASSWORD", databasePassword);

        SpringApplication.run(ProjectPeruAcademicApplication.class, args);
    }

}

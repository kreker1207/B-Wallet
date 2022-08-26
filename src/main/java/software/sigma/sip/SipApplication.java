package software.sigma.sip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import software.sigma.sip.domain.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SipApplication {
    public static void main(String[] args) {
        SpringApplication.run(SipApplication.class, args);
    }
}

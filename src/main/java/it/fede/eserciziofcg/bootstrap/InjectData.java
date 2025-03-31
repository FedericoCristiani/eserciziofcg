package it.fede.eserciziofcg.bootstrap;

import it.fede.eserciziofcg.models.entities.UserEntity;
import it.fede.eserciziofcg.repositories.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InjectData implements ApplicationRunner {
    private final UserRepository userRepository;

    public InjectData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args)  {

        UserEntity user = new UserEntity();
        user.setFirstname("Federico");
        user.setLastname("Federico");
        user.setEmail("federico.cristiani@gmail.com");
        user.setAddress("Via dei Magagna,5");

        userRepository.save(user);


    }
}

package kristiania.enterprise.exam.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializer {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize() {

        insertDefaultUsers();
    }

    private void insertDefaultUsers() {

        attempt(() -> userService.createUser("dev@mail.com", "dev-given", "dev-family", "dev"));
        attempt(() -> userService.createUser("iron@man.com", "Tony", "Stark", "suit"));
        attempt(() -> userService.createUser("spider@man.com", "Peter", "Parker", "nyc"));
    }

    private<T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}
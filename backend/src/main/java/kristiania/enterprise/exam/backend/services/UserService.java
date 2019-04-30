package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;

/*
 * NOTE: This file is coped from:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/18f764c3123f60339ab98167790aa223641e7559/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/db/UserService.java
 */

/**
 * Created by arcuri82 on 13-Dec-17.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    public boolean createUser(String email, String givenName, String familyName, String password) {

        boolean present = entityManager.find(UserEntity.class, email) != null;
        if (present) {

            return false;
        }

        String hashed = passwordEncoder.encode(password);

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setGivenName(givenName);
        user.setFamilyName(familyName);
        user.setPassword(hashed);

        entityManager.persist(user);

        return true;
    }
}

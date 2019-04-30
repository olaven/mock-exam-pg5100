package kristiania.enterprise.exam.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/*
* NOTE: This file is copied from:
* https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/18f764c3123f60339ab98167790aa223641e7559/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/WebSecurityConfig.java
*/

/**
 * Created by arcuri82 on 13-Dec-17.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Primary
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) {
        try {
            /*
                Spring does the right thing, and activate CSRF protections.
                However, to avoid having to deal with them in JSF, and also
                because anyway we will see them in details in the "advanced"
                Enterprise 2 course, we just deactivate them
             */
            http.csrf().disable();

            http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/assets/**").permitAll()
                    .antMatchers("/index.*").permitAll()
                    .antMatchers("/signup.*").permitAll()
                    .antMatchers("/javax.faces.resource/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login.jsf")
                    .permitAll()
                    .failureUrl("/login.jsf?error=true")
                    .defaultSuccessUrl("/index.jsf?faces-redirect=true")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/index.jsf?faces-redirect=true");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(
                            "SELECT username, password, enabled " +
                                    "FROM user_entity " +
                                    "WHERE username = ?"
                    )
                    .authoritiesByUsernameQuery(
                            "SELECT x.username, y.roles " +
                                    "FROM user_entity x, user_entity_roles y " +
                                    "WHERE x.username = ? and y.user_entity_username = x.username "
                    )
                    /*
                        Note: in BCrypt, the "password" field also contains the salt
                     */
                    .passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
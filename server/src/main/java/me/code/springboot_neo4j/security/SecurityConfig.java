package me.code.springboot_neo4j.security;

import me.code.springboot_neo4j.repositories.UserRepository;
import me.code.springboot_neo4j.services.UserAccountService;
import me.code.springboot_neo4j.utils.CredentialsValidatorUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security, UserAccountService userAccountService, JwtTokenUtil jwtTokenUtil) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new JwtValidationFilter(jwtTokenUtil, userAccountService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorize -> authorize
                        .anyRequest().permitAll());
        return security.build();
    }

    @Bean
    public AuthenticationProvider authProvider(UserDetailsService userAccountService, PasswordEncoder encoder) {
        var dao = new DaoAuthenticationProvider();

        dao.setUserDetailsService(userAccountService);
        dao.setPasswordEncoder(encoder);

        return dao;
    }

    @Bean
    public UserDetailsService userDetailsService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CredentialsValidatorUtil credentialsValidatorUtil) {
        return new UserAccountService(userRepository, passwordEncoder, credentialsValidatorUtil);
    }

    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

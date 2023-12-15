package me.code.springboot_neo4j.security;

import me.code.springboot_neo4j.models.nodes.User;
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

    public static final String[] PUBLIC_URLS = {
            "/api/account/register",
            "/api/account/login",
            "/api/products/all",
            "/api/products/featured",
            "/api/products/{productId}",
            "/api/orders/ongoing"
    };

    public static final String[] ADMIN_URLS = {
            "/api/products/insert",
            "/api/products/edit/{productId}",
            "/api/products/delete/{productId}"
    };

    public static final String ADMIN_ROLE = User.Role.ADMIN.toString();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security, UserAccountService userAccountService, JwtTokenUtil jwtTokenUtil) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new JwtValidationFilter(jwtTokenUtil, userAccountService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(ADMIN_URLS).hasAuthority(ADMIN_ROLE)
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated());
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

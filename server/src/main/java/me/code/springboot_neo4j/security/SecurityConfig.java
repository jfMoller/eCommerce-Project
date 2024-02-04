package me.code.springboot_neo4j.security;

import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.repositories.UserRepository;
import me.code.springboot_neo4j.services.UserAccountService;
import me.code.springboot_neo4j.services.RegistrationValidationService;
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

    private static final String API_PATH = "/api";
    private static final String ACCOUNT_PATH = API_PATH + "/account";
    private static final String PRODUCTS_PATH = API_PATH + "/products";
    private static final String ORDERS_PATH = API_PATH + "/orders";

    private static final String[] PUBLIC_URLS = {
            ACCOUNT_PATH + "/register",
            ACCOUNT_PATH + "/login",
            PRODUCTS_PATH + "/all",
            PRODUCTS_PATH + "/featured",
            PRODUCTS_PATH + "/{productId}",
            PRODUCTS_PATH + "/search/**",
            ORDERS_PATH + "/ongoing",
            ORDERS_PATH + "/delivery/methods",
            ORDERS_PATH + "/payment/methods",
    };

    private static final String[] ADMIN_URLS = {
            API_PATH + "/admin_tools/**"
    };

    private static final String ADMIN_ROLE = User.Role.ADMIN.toString();

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
            RegistrationValidationService registrationValidationService) {
        return new UserAccountService(userRepository, passwordEncoder, registrationValidationService);
    }

    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

package me.code.springboot_neo4j.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.services.UserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtValidationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtTokenUtil jwtTokenUtil;
    private final UserAccountService userAccountService;


    public JwtValidationFilter(JwtTokenUtil jwtTokenUtil, UserAccountService userAccountService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userAccountService = userAccountService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain securityFilterChain)
            throws ServletException, IOException {

        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (isTokenMissing(token)) {
            continueFilterChain(securityFilterChain, request, response);

        } else if (isValidToken(token)) {
            continueFilterChainWithAuthentication(token, securityFilterChain, request, response);

        } else {
            throw new CustomRuntimeException(HttpStatus.UNAUTHORIZED, "The provided token is not valid.");
        }
    }

    private boolean isTokenMissing(String token) {
        return token == null || token.isBlank();
    }

    private boolean isValidToken(String token) {
        return jwtTokenUtil.isValidToken(token);
    }

    private void continueFilterChain(
            FilterChain filterChain,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleFilterChainException(e);
        }
    }

    private void setAuthenticationContext(String token) {
        User user = getUser(token);
        var authToken = getAuthToken(user);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private void continueFilterChainWithAuthentication(
            String token,
            FilterChain filterChain,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        setAuthenticationContext(token);
        continueFilterChain(filterChain, request, response);
    }

    private User getUser(String token) {
        String userId = jwtTokenUtil.getTokenId(token);
        return this.userAccountService.loadUserById(userId);
    }

    private UsernamePasswordAuthenticationToken getAuthToken(User principal) {
        // setting user as the first arg makes the User object available through @AuthenticationPrincipal in controllers
        return new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
    }

    private void handleFilterChainException(Exception exception) throws ServletException, IOException {
        if (exception instanceof ServletException) {
            throw new ServletException("Servlet exception: " + exception.getMessage());
        } else if (exception instanceof IOException) {
            throw new IOException("IO exception: " + exception.getMessage());
        }
    }
}

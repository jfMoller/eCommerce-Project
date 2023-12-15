package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dto.request.ChangeEmailDTO;
import me.code.springboot_neo4j.dto.request.ChangePasswordDTO;
import me.code.springboot_neo4j.dto.request.ChangeUsernameDTO;
import me.code.springboot_neo4j.dto.request.CreateUserDTO;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.dto.response.success.variant.UserDetailsSuccess;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.repositories.UserRepository;
import me.code.springboot_neo4j.utils.CredentialsValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsValidatorUtil credentialsValidator;

    @Autowired
    public UserAccountService(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              CredentialsValidatorUtil credentialsValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.credentialsValidator = credentialsValidator;
    }

    public Success submitRegistration(CreateUserDTO dto) {
        checkForValidationErrors(dto.email(), dto.username(), dto.password());
        try {
            String encryptedPassword = passwordEncoder.encode(dto.password());
            User newUser = new User(dto.email(), dto.username(), encryptedPassword, User.Role.USER);
            userRepository.save(newUser);

            return new Success(
                    HttpStatus.CREATED,
                    "Successfully registered a new account");

        } catch (Exception exception) {
            throw new CustomRuntimeException(
                    HttpStatus.BAD_REQUEST,
                    "Could not register a new account");
        }
    }

    private void checkForValidationErrors(String email, String username, String password) {
        credentialsValidator.findFormattingErrors(email, username, password);
        credentialsValidator.findNonUniqueValues(email, username);
    }


    public Success getUserDetails(User user) {
        try {
            return new UserDetailsSuccess(HttpStatus.OK,
                    "User details were successfully retrieved",
                    user.getEmail(), user.getUsername());

        } catch (Exception exception) {
            throw new CustomRuntimeException(
                    HttpStatus.BAD_REQUEST,
                    "Could not fetch user details");
        }
    }

    public Success changeUsername(User user, ChangeUsernameDTO dto) {
        credentialsValidator.findNullUsername(dto.newUsername());
        credentialsValidator.findUsernameFormattingError(dto.newUsername());

        try {
            user.setUsername(dto.newUsername());
            userRepository.save(user);

            return new Success(
                    HttpStatus.OK,
                    "The username was successfully changed");

        } catch (Exception exception) {
            throw new CustomRuntimeException(
                    HttpStatus.BAD_REQUEST,
                    "Could not change username");
        }
    }

    public Success changeEmail(User user, ChangeEmailDTO dto) {
        credentialsValidator.findNullEmail(dto.newEmail());
        credentialsValidator.findEmailFormattingError(dto.newEmail());

        try {
            user.setEmail(dto.newEmail());
            userRepository.save(user);

            return new Success(
                    HttpStatus.OK,
                    "The email was successfully changed");

        } catch (Exception exception) {
            throw new CustomRuntimeException(
                    HttpStatus.BAD_REQUEST,
                    "Could not change email");
        }
    }

    public Success changePassword(User user, ChangePasswordDTO dto) {
        System.out.println(dto.newPassword());
        credentialsValidator.findNullPassword(dto.newPassword());
        credentialsValidator.findPasswordFormattingError(dto.newPassword());

        try {
            String encryptedPassword = passwordEncoder.encode(dto.newPassword());
            user.setPassword(encryptedPassword);
            userRepository.save(user);

            return new Success(
                    HttpStatus.OK,
                    "The password was successfully changed");

        } catch (Exception exception) {
            throw new CustomRuntimeException(
                    HttpStatus.BAD_REQUEST,
                    "Could not change password");
        }
    }

    public Success deleteAccount(User user) {
        try {
            userRepository.deleteById(user.getId());

            return new Success(
                    HttpStatus.OK,
                    "The account was successfully deleted");

        } catch (Exception exception) {
            throw new CustomRuntimeException(
                    HttpStatus.BAD_REQUEST,
                    "Could not delete account");
        }
    }

    public boolean isValidUserCredentials(String email, String password) {
        User user = loadUserByEmail(email);
        return passwordEncoder.matches(password, user.getPassword());
    }

    public User loadUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find user with id: " + userId));
    }

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find user with email: " + email));
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find user with username: " + username));
    }
}
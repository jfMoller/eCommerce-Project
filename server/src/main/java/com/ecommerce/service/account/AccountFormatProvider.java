package com.ecommerce.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountFormatProvider {

    private static final String USERNAME_FORMAT_REGEX = "^[a-zA-Z0-9_.-]{3,10}$";

/* ^                  # Start of the string
[a-zA-Z0-9_.-]{3,10}  # Match between 3 and 10 occurrences of letters (both uppercase and lowercase), digits,
                        underscores, dots, and hyphens
$                     # End of the string */


    private static final String EMAIL_FORMAT_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    /* ^              # Start of the string
    [a-zA-Z0-9_.+-]+  # Match one or more occurrences of letters (both uppercase and lowercase),
                        digits, underscores, dots, plus signs, and hyphens before the @ symbol
    @                 # Match the literal @ symbol
    [a-zA-Z0-9-]+     # Match one or more occurrences of letters (both uppercase and lowercase),
                        digits, and hyphens after the @ symbol (the domain name)
    \.                # Match the literal dot (.)
    [a-zA-Z0-9-.]+    # Match one or more occurrences of letters (both uppercase and lowercase),
                        digits, dots, and hyphens after the dot (top-level domain)
    $                 # End of the string */

    private static final String PASSWORD_FORMAT_REGEX = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]{8,20}$";

    /* ^                                              # Start of the string
    [a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':",.<>/?]{8,20}  # Match between 8 and 20 characters of letters (both uppercase
                                                      # and lowercase), digits, and special characters
    $                                                 # End of the string */

    @Autowired
    public AccountFormatProvider() {
    }

    public boolean isMatchingUsernameFormat(String username) {
        return isMatchingRegex(username, USERNAME_FORMAT_REGEX);
    }

    public boolean isMatchingEmailFormat(String email) {
        return isMatchingRegex(email, EMAIL_FORMAT_REGEX);
    }

    public boolean isMatchingPasswordFormat(String password) {
        return isMatchingRegex(password, PASSWORD_FORMAT_REGEX);
    }

    private boolean isMatchingRegex(String string, String regex) {
        return string.matches(regex);
    }

    ;
}

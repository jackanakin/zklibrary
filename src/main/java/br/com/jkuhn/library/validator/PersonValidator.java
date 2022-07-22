package br.com.jkuhn.library.validator;


import br.com.jkuhn.library.validator.pattern.RegexPattern;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PersonValidator {
    public String validateName(String name) {

        if (name.length() <= 1) {
            return "Nome inválido, curto demais";
        } else if (name.length() > 110) {
            return "Nome inválido, longo demais";
        }

        return null;
    }

    public String validateEmail(String email) {
        if (Pattern.compile(RegexPattern.email).matcher(email).matches()) {
            return null;
        } else {
            return "E-mail inválido";
        }
    }

    public String validatePassword(String password) {
        if (Pattern.compile(RegexPattern.password).matcher(password).matches()) {
            return null;
        } else {
            return "Use uma senha forte, mínimo 8 dígitos com no mínimo um caractere maiúsculo, um minúsculo, um especial e um número";
        }
    }

}

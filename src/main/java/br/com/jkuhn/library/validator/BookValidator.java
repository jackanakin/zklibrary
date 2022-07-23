package br.com.jkuhn.library.validator;


import org.springframework.stereotype.Service;

@Service
public class BookValidator {
    public String validateName(String name) {
        if (name.length() <= 1) {
            return "Nome inválido, curto demais";
        } else if (name.length() > 110) {
            return "Nome inválido, longo demais";
        }

        return null;
    }
}

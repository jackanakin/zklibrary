package br.com.jkuhn.library.validator.pattern;

public class RegexPattern {
    public static String email = "^(.+)@(\\S+)$";

    // 8 dígitos: um número + um lowercase + um uppercase + special
    public static String password = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,30}$";
}

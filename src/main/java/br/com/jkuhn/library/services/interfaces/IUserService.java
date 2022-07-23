package br.com.jkuhn.library.services.interfaces;

import br.com.jkuhn.library.entity.User;

public interface IUserService {

    public void delete(String username);

    public boolean checkIfUserExist(String email);

    public String encodePassword( String password);
}

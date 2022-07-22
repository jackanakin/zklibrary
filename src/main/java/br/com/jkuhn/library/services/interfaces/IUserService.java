package br.com.jkuhn.library.services.interfaces;

import br.com.jkuhn.library.entity.User;

public interface IUserService {
    public void save(User user) throws Exception;

    public boolean checkIfUserExist(String email);

    public void encodePassword( User userEntity);
}

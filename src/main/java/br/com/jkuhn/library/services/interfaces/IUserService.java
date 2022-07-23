package br.com.jkuhn.library.services.interfaces;

import br.com.jkuhn.library.entity.User;

public interface IUserService {

    public void update(User user) throws Exception;

    public void save(User user) throws Exception;

    public void delete(String username);

    public boolean checkIfUserExist(String email);

    public void encodePassword( User userEntity);
}

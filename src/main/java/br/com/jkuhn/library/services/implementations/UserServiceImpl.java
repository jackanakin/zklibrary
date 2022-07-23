package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.dao.IUserDAO;
import br.com.jkuhn.library.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDAO userDAO;

    @Override
    public void delete(String username) {
        userDAO.deleteById(username);
    }

    @Override
    public boolean checkIfUserExist(String email) {
        return userDAO.findByUsername(email) != null;
    }

    @Override
    public String encodePassword(String password){
        return "{bcrypt}" + new BCryptPasswordEncoder().encode(password);
    }
}

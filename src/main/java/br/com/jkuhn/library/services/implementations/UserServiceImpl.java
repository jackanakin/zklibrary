package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.dao.IUserDAO;
import br.com.jkuhn.library.entity.User;
import br.com.jkuhn.library.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDAO userDAO;

    @Override
    public void update(User user) throws Exception {
        return;
    }

    @Override
    public void save(User user) throws Exception {
        return;
    }

    @Override
    public void delete(String username) {
        userDAO.deleteById(username);
    }

    @Override
    public boolean checkIfUserExist(String email) {
        return userDAO.findByUsername(email) != null;
    }

    @Override
    public void encodePassword(User user){
        user.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword()));
    }
}

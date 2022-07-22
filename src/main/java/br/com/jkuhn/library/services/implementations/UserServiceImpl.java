package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.dao.IUserDAO;
import br.com.jkuhn.library.entity.Authoritie;
import br.com.jkuhn.library.entity.User;
import br.com.jkuhn.library.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private AuthoritieServiceImpl authoritieService;

    @Override
    public void save(User user) throws Exception {
        if(checkIfUserExist(user.getUsername())){
            throw new Exception("Este e-mail já está em uso");
        }

        encodePassword(user);
        userDAO.save(user);
        authoritieService.save(new Authoritie(user.getUsername(), "USER"));
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

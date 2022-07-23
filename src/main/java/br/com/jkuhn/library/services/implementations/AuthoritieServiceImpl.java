package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.dao.IAuthoritieDAO;
import br.com.jkuhn.library.entity.Authoritie;
import br.com.jkuhn.library.services.interfaces.IAuthoritieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthoritieServiceImpl implements IAuthoritieService {
    @Autowired
    private IAuthoritieDAO authoritieDAO;

    @Override
    public void save(Authoritie authoritie) {
        authoritieDAO.save(authoritie);
    }

    @Override
    public void delete(String username) {
        authoritieDAO.deleteById(username);
    }
}

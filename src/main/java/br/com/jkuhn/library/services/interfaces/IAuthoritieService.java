package br.com.jkuhn.library.services.interfaces;

import br.com.jkuhn.library.entity.Authoritie;

public interface IAuthoritieService {
    public void save(Authoritie authoritie);

    public void delete(String username);
}

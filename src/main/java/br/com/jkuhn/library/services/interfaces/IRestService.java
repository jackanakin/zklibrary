package br.com.jkuhn.library.services.interfaces;

import br.com.jkuhn.library.entity.Book;

import java.util.List;

public interface IRestService {

    public List<Book> get() throws Exception;

    //public boolean put(String code, int booked) throws Exception;

}

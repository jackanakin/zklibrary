package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.configuration.ApplicationProperties;
import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.services.interfaces.IRestConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestConsumerServiceImpl implements IRestConsumerService {

    @Autowired
    private ApplicationProperties applicationProperties;

    /***
     *
     * @param code object code in remote api
     * @param booked 1 = booked 0 = available
     */
    public void put(String code, int booked) {
        // booked 1 - reserve
        // booked 0 - return
        final String uri = applicationProperties.getRemoteApi() + "/" + code + "/" + booked;
        RestTemplate restTemplate = new RestTemplate();
        //if (true) throw new Exception("parou");

        ResponseEntity<Book> response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                null,
                Book.class);

        Book result = response.getBody();
        System.out.println(result);
    }

    public List<Book> get() throws Exception {
        final String uri = applicationProperties.getRemoteApi();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Book>>(){});

        List<Book> result = response.getBody();
        List<Book> bookList = new ArrayList<>();
        result.forEach(remote -> bookList.add(new Book(remote.getName(), remote.getCode(), remote.getBooked())));

        return result;
    }

}

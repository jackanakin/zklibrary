package br.com.jkuhn.library.services.implementations;

import br.com.jkuhn.library.configuration.ApplicationProperties;
import br.com.jkuhn.library.entity.Book;
import br.com.jkuhn.library.services.interfaces.IRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestConsumerImpl implements IRestService {

    @Autowired
    private ApplicationProperties applicationProperties;

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

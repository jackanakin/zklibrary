package br.com.jkuhn.library.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

    @Value("${remote.api}")
    private String remoteApi;

    public String getRemoteApi() {
        return remoteApi;
    }
}

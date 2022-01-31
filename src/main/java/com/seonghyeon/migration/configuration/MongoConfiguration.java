package com.seonghyeon.migration.configuration;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfiguration {

    @Value("${nosql.host}")
    private String host;
    @Value("${nosql.port}")
    private int port;
    @Value("${nosql.username}")
    private String username;
    @Value("${nosql.password}")
    private String password;

    @Bean
    public MongoClient mongoClient() {
        MongoCredential mongoCredential = MongoCredential.createCredential(this.username, "node", this.password.toCharArray());
        ServerAddress serverAddress = new ServerAddress(this.host, this.port);
        MongoClientOptions options = MongoClientOptions.builder()
                .readPreference(ReadPreference.primaryPreferred())
                .retryWrites(true)
                .maxConnectionIdleTime(6000)
//                .sslEnabled(true)
                .build();
        return new MongoClient(serverAddress, mongoCredential, options);
    }
}

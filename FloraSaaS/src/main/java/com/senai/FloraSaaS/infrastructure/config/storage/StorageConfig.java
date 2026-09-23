package com.senai.FloraSaaS.infrastructure.config.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.senai.FloraSaaS.application.service.arquivo.FileStorageService;
import com.senai.FloraSaaS.application.service.arquivo.LocalFileStorageService;

import java.io.IOException;

@Configuration
public class StorageConfig {
    @Bean
    @ConditionalOnProperty(name = "storage.type", havingValue = "local", matchIfMissing = true)
    public FileStorageService localStorageService() throws IOException {
        return new LocalFileStorageService();
    }
}
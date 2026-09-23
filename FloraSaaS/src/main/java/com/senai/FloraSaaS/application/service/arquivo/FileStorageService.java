package com.senai.FloraSaaS.application.service.arquivo;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String storeFile(MultipartFile file) throws IOException;
    Resource loadFile(String filename) throws IOException;
}
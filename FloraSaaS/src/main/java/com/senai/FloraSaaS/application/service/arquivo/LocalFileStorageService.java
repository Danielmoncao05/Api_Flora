package com.senai.FloraSaaS.application.service.arquivo;

import com.senai.FloraSaaS.domain.exception.ambiente.arquivo.ArquivoVazioException;
import com.senai.FloraSaaS.domain.exception.ambiente.arquivo.TamanhoMaximoAtingidoException;
import com.senai.FloraSaaS.domain.exception.ambiente.arquivo.TipoDeArquivoNaoSuportadoException;
import org.springframework.core.io.Resource;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
@Profile("local")
@Transactional
public class LocalFileStorageService implements FileStorageService {
    private final Path uploadDir = Paths.get("uploads");

    public LocalFileStorageService() throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        validateFile(file);

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + filename;
    }

    @Override
    public Resource loadFile(String filename) throws IOException {
        Path filePath = uploadDir.resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new NoSuchFileException("Arquivo não encontrado: " + filename);
        }
        return resource;
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ArquivoVazioException("Arquivo vazio não permitido.");
        }

        if (file.getSize() > 50 * 1024 * 1024) { // 50MB
            throw new TamanhoMaximoAtingidoException("Tamanho máximo permitido: 50MB");
        }

        List<String> allowedTypes = List.of(
                "image/png", "image/jpeg", "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        );

        if (!allowedTypes.contains(file.getContentType())) {
            throw new TipoDeArquivoNaoSuportadoException("Tipo de arquivo não suportado: " + file.getContentType());
        }
    }
}
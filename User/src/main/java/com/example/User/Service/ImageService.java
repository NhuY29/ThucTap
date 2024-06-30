package com.example.User.Service;

import com.example.User.Entity.ImageEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public interface ImageService {
    String uploadImage(MultipartFile file) throws IOException;

    Optional<ImageEntity> getImageEntity(Long id);

    ResponseEntity<ByteArrayResource> getFileResponse(Long id) throws IOException;

}




package com.example.User.Service;

import com.example.User.Entity.ImageEntity;
import com.example.User.Reponsitory.ImageRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ImageImpl implements ImageService {
    @Autowired
    private ImageRepositories imageRepositories;

    private final String folder = "D:/ThucTap/MyFile/";

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = folder + file.getOriginalFilename();
        file.transferTo(new File(fileName));

        ImageEntity imageEntity = imageRepositories.save(ImageEntity.builder()
                .nameImage(file.getOriginalFilename())
                .type(file.getContentType())
                .imageUrl(fileName)
                .build());

        if (imageEntity != null) {
            return "File uploaded successfully: " + fileName;
        }
        return "File upload failed";
    }


    @Override
    public Optional<ImageEntity> getImageEntity(Long id) {
        return imageRepositories.findById(id);
    }

    @Override
    public ResponseEntity<ByteArrayResource> getFileResponse(Long id) throws IOException {
        Optional<ImageEntity> imageEntityOptional = getImageEntity(id);
        if (imageEntityOptional.isPresent()) {
            ImageEntity imageEntity = imageEntityOptional.get();
            File file = new File(imageEntity.getImageUrl());
            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}

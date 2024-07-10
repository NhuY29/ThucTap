package com.example.User.Service;

import com.example.User.Entity.ImageEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface ImageService {
    ResponseEntity<byte[]> getImageByIdWithSize(UUID imageId, int width, int height);
}

package com.example.User.Service;

import com.example.User.Entity.ImageEntity;
import com.example.User.Reponsitory.ImageRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Component
public class ImageImpl implements ImageService {

    @Autowired
    private ImageRepositories imageRepository;
//    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Override
    public ResponseEntity<byte[]> getImageByIdWithSize(UUID imageId, int width, int height) {
        Optional<ImageEntity> imageOptional = imageRepository.findById(imageId);
        if (imageOptional.isPresent()) {
            ImageEntity image = imageOptional.get();

            byte[] imageData = loadImageData(image.getUrl());
            byte[] resizedImageData = resizeImage(imageData, width, height);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return ResponseEntity.ok().headers(headers).body(resizedImageData);
        }
        return ResponseEntity.notFound().build();
    }

    private byte[] loadImageData(String imageUrl) {
        try {
            Path imagePath = Paths.get(imageUrl);
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private byte[] resizeImage(byte[] imageData, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new java.io.ByteArrayInputStream(imageData));
            BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);
            baos.flush();
            byte[] resizedImageData = baos.toByteArray();
            baos.close();

            return resizedImageData;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}

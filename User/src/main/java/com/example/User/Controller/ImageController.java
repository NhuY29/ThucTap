package com.example.User.Controller;

import com.example.User.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/image/{imageId}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable UUID imageId,
            @RequestParam(required = false) Integer width,
            @RequestParam(required = false) Integer height) {
            return imageService.getImageByIdWithSize(imageId, width, height);
    }
}

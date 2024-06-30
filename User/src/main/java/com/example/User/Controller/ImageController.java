package com.example.User.Controller;

import com.example.User.Entity.ImageEntity;
import com.example.User.Reponsitory.ImageRepositories;
import com.example.User.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return imageService.uploadImage(file);
        } catch (IOException e) {
            return "Upload failed: " + e.getMessage();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long id) {
        try {
            return imageService.getFileResponse(id);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}



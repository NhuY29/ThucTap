package com.example.User.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idImage")
    private long idImage;

    @Column(name = "nameImage")
    private String nameImage;

    @Column(name = "type")
    private String type;

    @Column(name = "imageUrl")
    private String imageUrl;
}

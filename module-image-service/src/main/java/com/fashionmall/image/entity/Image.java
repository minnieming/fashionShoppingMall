package com.fashionmall.image.entity;

import com.fashionmall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "image")
@Slf4j(topic = "이미지 테이블")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500, columnDefinition = "TEXT")
    private String url;

    @Column(nullable = false)
    private String uniqueFileName;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true) // image가 parent이고 imageMapping이 child
    private List<ImageMapping> imageMappings = new ArrayList<>();

    @Builder
    public Image (String url, String uniqueFileName) {
        this.url = url;
        this.uniqueFileName = uniqueFileName;
    }

}

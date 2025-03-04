package com.fashionmall.image.entity;

import com.fashionmall.common.entity.BaseEntity;
import com.fashionmall.common.moduleApi.enums.ImageTypeEnum;
import com.fashionmall.common.moduleApi.enums.ReferenceTypeEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table(name = "image_mapping")
@Slf4j(topic = "이미지 매핑 테이블")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @Enumerated(EnumType.STRING)
    private ReferenceTypeEnum referenceType;

    @Column(nullable = false)
    private Long referenceId;

    @Enumerated(EnumType.STRING)
    private ImageTypeEnum imageType;

    @Builder
    public ImageMapping (Image image, ReferenceTypeEnum referenceType, Long referenceId, ImageTypeEnum imageType) {
        this.image = image;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.imageType = imageType;
    }
}

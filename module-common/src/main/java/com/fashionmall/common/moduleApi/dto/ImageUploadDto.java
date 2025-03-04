package com.fashionmall.common.moduleApi.dto;

import com.fashionmall.common.moduleApi.enums.ImageTypeEnum;
import com.fashionmall.common.moduleApi.enums.ReferenceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadDto {

    private String fileName;
    private Long referenceId; // ex. itemId Or reviewId
    private ReferenceTypeEnum referenceType; // (Enum) ITEM Or REVIEW
    private ImageTypeEnum imageType; // (Enum) MAIN Or DESCRIPTION

}

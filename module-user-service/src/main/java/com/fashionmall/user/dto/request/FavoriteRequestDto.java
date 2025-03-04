package com.fashionmall.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequestDto {

    @NotNull (message = "좋아요 여부를 알려주세요")
    @JsonProperty("is_selected")
    private boolean isSelected;

}

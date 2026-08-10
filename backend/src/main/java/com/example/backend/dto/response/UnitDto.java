package com.example.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitDto {
    private Integer id;
    private String title;
    private String description;
    private Integer orderIndex;
    private String imageUrl;
}

package com.url.shortener.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClickEventDto {
    private LocalDate clickDate;
    private Long count;
}

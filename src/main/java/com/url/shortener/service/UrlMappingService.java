package com.url.shortener.service;

import com.url.shortener.models.User;
import com.url.shortener.models.dtos.ClickEventDto;
import com.url.shortener.models.dtos.UrlMappingDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface UrlMappingService {
    UrlMappingDto createShortUrl(String originalUrl, User user);

    List<UrlMappingDto> getUrlsByUser(User user);

    List<ClickEventDto> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end);

    Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end);
}

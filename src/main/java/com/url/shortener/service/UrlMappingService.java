package com.url.shortener.service;

import com.url.shortener.models.User;
import com.url.shortener.models.dtos.UrlMappingDto;

import java.util.List;

public interface UrlMappingService {
    UrlMappingDto createShortUrl(String originalUrl, User user);

    List<UrlMappingDto> getUrlsByUser(User user);
}

package com.url.shortener.service;

import com.url.shortener.models.User;
import com.url.shortener.models.dtos.UrlMappingDto;

public interface UrlMappingService {
    UrlMappingDto createShortUrl(String originalUrl, User user);
}

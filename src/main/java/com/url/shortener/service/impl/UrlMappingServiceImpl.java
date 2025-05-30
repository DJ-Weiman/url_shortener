package com.url.shortener.service.impl;

import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.models.dtos.UrlMappingDto;
import com.url.shortener.repository.UrlMappingRepository;
import com.url.shortener.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlMappingServiceImpl implements UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;

    @Override
    public UrlMappingDto createShortUrl(String originalUrl, User user) {
        String shortUrl = generateShortUrl();

        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .user(user)
                .createdTime(LocalDateTime.now())
                .build();

        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        return convertToDto(savedUrlMapping);
    }

    private UrlMappingDto convertToDto(UrlMapping urlMapping){
        return UrlMappingDto.builder()
                .id(urlMapping.getId())
                .originalUrl(urlMapping.getOriginalUrl())
                .shortUrl(urlMapping.getShortUrl())
                .clickCount(urlMapping.getClickCount())
                .createdDate(urlMapping.getCreatedTime())
                .userName(urlMapping.getUser().getUsername())
                .build();
    }

    private String generateShortUrl() {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
        StringBuilder shortUrl = new StringBuilder(8);

        for(int i = 0; i < 8 ; i++){
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }

        return shortUrl.toString();
    }
}

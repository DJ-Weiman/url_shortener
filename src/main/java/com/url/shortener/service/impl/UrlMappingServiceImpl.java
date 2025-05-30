package com.url.shortener.service.impl;

import com.url.shortener.models.ClickEvent;
import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.models.dtos.ClickEventDto;
import com.url.shortener.models.dtos.UrlMappingDto;
import com.url.shortener.repository.ClickEventRepository;
import com.url.shortener.repository.UrlMappingRepository;
import com.url.shortener.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  UrlMappingServiceImpl implements UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final ClickEventRepository clickEventRepository;

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

    @Override
    public List<UrlMappingDto> getUrlsByUser(User user) {
        return urlMappingRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ClickEventDto> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if(urlMapping != null){
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> {
                        return ClickEventDto.builder()
                                .clickDate(entry.getKey())
                                .count(entry.getValue())
                                .build();
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
        List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingInAndClickDateBetween(
                urlMappings,
                start.atStartOfDay(),
                end.plusDays(1).atStartOfDay());

        return clickEvents.stream()
                .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));
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

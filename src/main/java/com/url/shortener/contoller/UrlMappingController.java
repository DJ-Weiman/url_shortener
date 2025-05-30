package com.url.shortener.contoller;

import com.url.shortener.models.User;
import com.url.shortener.models.dtos.UrlMappingDto;
import com.url.shortener.service.UrlMappingService;
import com.url.shortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {

    private final UrlMappingService urlMappingService;
    private final UserService userService;

    @PostMapping("/shortener")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDto> createShortUrl(@RequestBody Map<String, String> request,
                                                        Principal principal){
        String originalUrl = request.get("originalUrl");
        User user = userService.findByUsername(principal.getName());

        UrlMappingDto urlMappingDto = urlMappingService.createShortUrl(originalUrl, user);
        return ResponseEntity.ok(urlMappingDto);
    }
}

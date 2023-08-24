package ru.vinogradov.api.restApiForSocialPlatform.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vinogradov.api.restApiForSocialPlatform.services.ImageService;


@RestController
@RequestMapping("/api/content/img")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @ApiOperation(value = "Get image by post id", response = Resource.class)
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {

        Resource resource = imageService.getImage(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }
}

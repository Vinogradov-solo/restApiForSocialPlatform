package ru.vinogradov.api.restApiForSocialPlatform.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vinogradov.api.restApiForSocialPlatform.dto.PostDto;
import ru.vinogradov.api.restApiForSocialPlatform.models.ResponsePost;
import ru.vinogradov.api.restApiForSocialPlatform.services.FeedService;
import ru.vinogradov.api.restApiForSocialPlatform.util.GetUsernameJwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/content/post")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final GetUsernameJwtTokenUtil usernameByToken;

    @ApiOperation(value = "Saved post", response = String.class)
    @PostMapping()
    public ResponseEntity<String> savePost(@Valid @ModelAttribute PostDto postDto, HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = feedService.savePost(postDto, username);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Getting a subscription feed", response = List.class)
    @GetMapping
    public ResponseEntity<?> getFeed(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "updatedAt") String sortBy
    ) {
        String username = usernameByToken.getUsernameByToken(request);
        List<ResponsePost> response = feedService.getFeed(pageNo, pageSize, sortBy, username);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Delete post", response = String.class)
    @DeleteMapping
    public ResponseEntity<String> deletePost(@Valid @ModelAttribute PostDto postDto, HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = feedService.deletePost(postDto, username);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Update post", response = String.class)
    @PutMapping
    public ResponseEntity<String> updatePost(@Valid @ModelAttribute PostDto postDto, HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = feedService.savePost(postDto, username);
        return ResponseEntity.ok(response);
    }
}

package ru.vinogradov.api.restApiForSocialPlatform.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vinogradov.api.restApiForSocialPlatform.models.RequestFriendship;
import ru.vinogradov.api.restApiForSocialPlatform.models.ResponseFriendship;
import ru.vinogradov.api.restApiForSocialPlatform.services.FriendshipService;
import ru.vinogradov.api.restApiForSocialPlatform.util.GetUsernameJwtTokenUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/content/friendship")
@RequiredArgsConstructor
public class FriendshipController {

    private final GetUsernameJwtTokenUtil usernameByToken;
    private final FriendshipService friendshipService;

    @ApiOperation(value = "Solution on request friendship", response = String.class)
    @PutMapping
    public ResponseEntity<?> responseFriendship(HttpServletRequest request, @RequestBody RequestFriendship requestBody) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = friendshipService.responseFriendship(username, requestBody);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all friendships", response = ResponseFriendship.class)
    @GetMapping
    ResponseEntity<?> findAllFriendshipsByUser(HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        ResponseFriendship response = friendshipService.findAllByUser(username);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Create friendship", response = String.class)
    @PostMapping("/{friendName}")
    ResponseEntity<?> createFriendship(HttpServletRequest request,@PathVariable String friendName) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = friendshipService.createFriendship(username, friendName);
        return ResponseEntity.ok(response);
    }
}

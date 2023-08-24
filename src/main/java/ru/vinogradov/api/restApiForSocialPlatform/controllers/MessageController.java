package ru.vinogradov.api.restApiForSocialPlatform.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vinogradov.api.restApiForSocialPlatform.models.ChatDto;
import ru.vinogradov.api.restApiForSocialPlatform.models.SendMessage;
import ru.vinogradov.api.restApiForSocialPlatform.services.ChatService;
import ru.vinogradov.api.restApiForSocialPlatform.util.GetUsernameJwtTokenUtil;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/content/chat")
@RequiredArgsConstructor
public class MessageController {

    private final GetUsernameJwtTokenUtil usernameByToken;
    private final ChatService chatService;

    @ApiOperation(value = "Get all chats", response = List.class)
    @GetMapping()
    public ResponseEntity<?> getMessages(HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        List<ChatDto> chats = chatService.getMessages(username);
        return ResponseEntity.ok(chats);
    }

    @ApiOperation(value = "Send message", response = String.class)
    @PostMapping()
    public ResponseEntity<?> sendMessage(HttpServletRequest request, @Valid @RequestBody SendMessage message) {

        String username = usernameByToken.getUsernameByToken(request);
        chatService.saveChat(username, message);
        return ResponseEntity.ok("Message send");
    }
}
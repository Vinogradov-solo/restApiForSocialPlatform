package ru.vinogradov.api.restApiForSocialPlatform.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vinogradov.api.restApiForSocialPlatform.dto.UserDto;
import ru.vinogradov.api.restApiForSocialPlatform.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegController {

    private final UserService userService;

    @ApiOperation(value = "Registration and get token", response = String.class)
    @PostMapping()
    public ResponseEntity<?> registration(@Valid @RequestBody UserDto userDto) {

        String response = userService.saveUser(userDto);
        return ResponseEntity.ok(response);
    }
}

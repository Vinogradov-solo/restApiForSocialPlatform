package ru.vinogradov.api.restApiForSocialPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @Column(name = "username")
    @NotBlank(message = "Имя пользователя обязательно для заполнения")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Пароль обязателен для заполнения")
    private String password;

    @Column(name = "email")
    @NotBlank(message = "Email обязателен для заполнения")
    private String email;
}

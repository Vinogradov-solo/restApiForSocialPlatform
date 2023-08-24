package ru.vinogradov.api.restApiForSocialPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostDto {

    private Long id;

    @NotBlank(message = "Заголовок не должен быть пустым")
    private String header;

    @NotBlank(message = "Текст не должен быть пустым")
    private String text;

    private MultipartFile file;
}

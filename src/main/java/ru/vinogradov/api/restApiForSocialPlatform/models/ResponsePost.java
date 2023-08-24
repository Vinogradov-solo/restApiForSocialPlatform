package ru.vinogradov.api.restApiForSocialPlatform.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePost {

    private Long id;
    private String header;
    private String text;
}

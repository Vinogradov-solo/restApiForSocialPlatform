package ru.vinogradov.api.restApiForSocialPlatform.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMessage {

    private Long chatId;
    private String sender;
    private String recipient;
    private String message;
}

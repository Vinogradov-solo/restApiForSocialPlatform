package ru.vinogradov.api.restApiForSocialPlatform.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    private Long id;
    private String chatPath;
    private String senderName;
    private String recipientName;
}

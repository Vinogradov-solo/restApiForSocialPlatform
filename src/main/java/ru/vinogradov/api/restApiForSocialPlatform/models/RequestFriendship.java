package ru.vinogradov.api.restApiForSocialPlatform.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFriendship {

    private String username;
    private Boolean answer;
}

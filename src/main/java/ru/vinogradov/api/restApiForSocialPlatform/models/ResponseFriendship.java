package ru.vinogradov.api.restApiForSocialPlatform.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseFriendship {

    private String username;
    private List<String> friendship;
}

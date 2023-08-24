package ru.vinogradov.api.restApiForSocialPlatform.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.vinogradov.api.restApiForSocialPlatform.models.ResponseFriendship;
import ru.vinogradov.api.restApiForSocialPlatform.services.SubscriptionsService;
import ru.vinogradov.api.restApiForSocialPlatform.util.MessageUtils;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SubscriptionsControllerTest {

    @Autowired
    private SubscriptionsService subscriptionsService;

    @Test
    @Transactional
    void subscribe() {

        ResponseFriendship user = subscriptionsService.getSubscribes("friend");
        Assertions.assertEquals(user.getFriendship().get(0), "user");
    }

    @Test
    @Transactional

    void unsubscribe() {

        String unsubscribe = subscriptionsService.unsubscribe("user", "friend");
        Assertions.assertEquals(String.format(MessageUtils.UNSUBSCRIBE, "friend"), unsubscribe);

        ResponseFriendship unsubscribeFriendship = subscriptionsService.getSubscribes("friend");
        Assertions.assertEquals(0, unsubscribeFriendship.getFriendship().size());
    }
}

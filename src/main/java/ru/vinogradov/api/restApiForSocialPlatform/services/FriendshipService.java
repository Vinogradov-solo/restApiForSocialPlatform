package ru.vinogradov.api.restApiForSocialPlatform.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vinogradov.api.restApiForSocialPlatform.entities.Friendship;
import ru.vinogradov.api.restApiForSocialPlatform.entities.User;
import ru.vinogradov.api.restApiForSocialPlatform.models.RequestFriendship;
import ru.vinogradov.api.restApiForSocialPlatform.models.ResponseFriendship;
import ru.vinogradov.api.restApiForSocialPlatform.repositories.FriendshipRepository;
import ru.vinogradov.api.restApiForSocialPlatform.util.MessageUtils;
import ru.vinogradov.api.restApiForSocialPlatform.util.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final SubscriptionsService subscriptionsService;
    private final UserService userService;

    public String createFriendship(String username, String friendName) {

        if (friendName.isEmpty() || username.isEmpty()) {
            throw new ResourceNotFoundException(MessageUtils.FRIEND_EMPTY + " or " + MessageUtils.USERNAME_EMPTY);
        }
        User sender = getUserByUsername(username);
        User recipient = getUserByUsername(friendName);
        friendshipRepository.save(Friendship.builder().sender(sender).recipient(recipient).build());
        return String.format(MessageUtils.OK_FRIENDSHIP, friendName);
    }

    public ResponseFriendship findAllByUser(String username) {
        if (username.isEmpty()) {
            throw new ResourceNotFoundException(MessageUtils.USERNAME_EMPTY);
        }
        ResponseFriendship response = new ResponseFriendship();
        User user = getUserByUsername(username);
        List<String> sendersFriendship = friendshipRepository.findByRecipient(user)
                .stream()
                .map(Friendship::getSender)
                .map(User::getUsername)
                .collect(Collectors.toList());
        response.setFriendship(sendersFriendship);
        response.setUsername(username);
        return response;
    }

    public String responseFriendship(String username, RequestFriendship request) {
        if (request == null) {
            throw new ResourceNotFoundException(MessageUtils.BAD_REQUEST);
        }
        String friendName = request.getUsername();
        Boolean answer = request.getAnswer();
        if (username.isEmpty() || friendName.isEmpty() || answer == null) {
            throw new ResourceNotFoundException(MessageUtils.BAD_REQUEST);
        }
        if (answer) {
            log.debug(subscriptionsService.subscribe(username, friendName) + " " + MessageUtils.ACCEPT);
            return subscriptionsService.subscribe(username, friendName) + " " + MessageUtils.ACCEPT;
        }
        log.debug(cancelFriendship(username, friendName));
        return cancelFriendship(username, friendName);
    }

    private String cancelFriendship(String username, String friendName) {
        if (username.isEmpty() || friendName.isEmpty()) {
            throw new RuntimeException();
        }
        User user = getUserByUsername(username);
        User friend = getUserByUsername(friendName);
        friendshipRepository.deleteByRecipientAndSender(user, friend);
        return String.format(MessageUtils.REJECT_FRIENDSHIP, friendName);
    }

    private User getUserByUsername(String username) {
        try {
            return userService.findUserByUsername(username);
        } catch (Exception e) {
            log.error(String.format("%s%s or %s", MessageUtils.USER, MessageUtils.NOT_FOUND, Arrays.toString(e.getStackTrace())));
            throw new ResourceNotFoundException(MessageUtils.USER + username + MessageUtils.NOT_FOUND);
        }
    }
}

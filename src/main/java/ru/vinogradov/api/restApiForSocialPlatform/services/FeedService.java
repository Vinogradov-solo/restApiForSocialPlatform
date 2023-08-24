package ru.vinogradov.api.restApiForSocialPlatform.services;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vinogradov.api.restApiForSocialPlatform.dto.PostDto;
import ru.vinogradov.api.restApiForSocialPlatform.entities.Post;
import ru.vinogradov.api.restApiForSocialPlatform.entities.Subscriptions;
import ru.vinogradov.api.restApiForSocialPlatform.entities.User;
import ru.vinogradov.api.restApiForSocialPlatform.models.ResponsePost;
import ru.vinogradov.api.restApiForSocialPlatform.repositories.PostsRepository;
import ru.vinogradov.api.restApiForSocialPlatform.util.MessageUtils;
import ru.vinogradov.api.restApiForSocialPlatform.util.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final UserService userService;
    private final PostsRepository postRepository;
    private final ImageService imageService;
    private final SubscriptionsService subscriptionsService;

    @Transactional
    public String savePost(PostDto postDto, String username) {

        String response = imageService.mediaProcessing(postDto.getFile(), username);
        if (response.startsWith(MessageUtils.FAILED)) {
            throw new ResourceNotFoundException(response);
        }
        User user = getCurrentUser(username);
        if (postDto.getId() != null) {
            postRepository.save(Post.builder()
                    .id(postDto.getId())
                    .header(postDto.getHeader())
                    .text(postDto.getText())
                    .imagePath(response)
                    .user(user)
                    .build());
            log.debug(MessageUtils.POST_UPDATED + "by id: " + postDto.getId());
            return MessageUtils.POST_UPDATED + "by id: " + postDto.getId();
        }
        postRepository.save(Post.builder()
                .header(postDto.getHeader())
                .text(postDto.getText())
                .imagePath(response)
                .user(user)
                .build());
        log.debug(MessageUtils.POST_CREATED);
        return MessageUtils.POST_CREATED;
    }


    private User getCurrentUser(String username) {
        try {
            return userService.findUserByUsername(username);
        } catch (Exception e) {
            throw new ResourceNotFoundException(MessageUtils.USER + username + MessageUtils.NOT_FOUND + " or " + e.getMessage());
        }
    }

    public List<ResponsePost> getFeed(Integer pageNo, Integer pageSize, String sortBy, String username) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        List<Subscriptions> mySubscriptions = subscriptionsService.getMySubscriptions(username);
        List<Long> subscriptionsId = mySubscriptions.stream().map(Subscriptions::getId).collect(Collectors.toList());
        Page<Post> pagedResult = postRepository.findAllByIdIn(subscriptionsId, paging);

        if (pagedResult.hasContent()) {
            List<Post> content = pagedResult.getContent();
            log.debug(content.stream().map(Post::getId).toList().toString());
            return content.stream().map(post -> ResponsePost.builder()
                    .id(post.getId())
                    .header(post.getHeader())
                    .text(post.getText())
                    .build()).collect(Collectors.toList());
        } else {
            log.error("Feed by user " + username + MessageUtils.NOT_FOUND);
            throw new ResourceNotFoundException(MessageUtils.NOT_FOUND);
        }
    }

    @Transactional
    public String deletePost(PostDto postDto, String username) {

        try {
            User user = getCurrentUser(username);
            if (imageService.deleteImg(postDto.getId(), user)) {
                postRepository.delete(Post.builder()
                        .id(postDto.getId())
                        .header(postDto.getHeader())
                        .text(postDto.getText())
                        .user(user)
                        .build());
                log.info(MessageUtils.DELETE_OK + " post by id: " + postDto.getId());
                return MessageUtils.DELETE_OK;
            } else {
                log.info(MessageUtils.DELETE_BAD + " post by id: " + postDto.getId());
                return MessageUtils.DELETE_BAD;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException(MessageUtils.NOT_FOUND + " or " + e.getMessage());
        }
    }
}

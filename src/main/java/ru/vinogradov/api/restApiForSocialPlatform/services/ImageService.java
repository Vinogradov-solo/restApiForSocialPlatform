package ru.vinogradov.api.restApiForSocialPlatform.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vinogradov.api.restApiForSocialPlatform.entities.Post;
import ru.vinogradov.api.restApiForSocialPlatform.entities.User;
import ru.vinogradov.api.restApiForSocialPlatform.repositories.PostsRepository;
import ru.vinogradov.api.restApiForSocialPlatform.util.MediaProcessingUtils;
import ru.vinogradov.api.restApiForSocialPlatform.util.MessageUtils;
import org.springframework.core.io.Resource;
import ru.vinogradov.api.restApiForSocialPlatform.util.ResourceNotFoundException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final MediaProcessingUtils mediaProcessingUtils;
    private final PostsRepository postsRepository;


    public String mediaProcessing(MultipartFile file, String username) {
        try {
            byte[] bytes = file.getBytes();
            String accountHash = mediaProcessingUtils.getAccountHash(username);
            String filename = file.getOriginalFilename();
            Path path = mediaProcessingUtils.getFilePath(filename, accountHash);
            Path filePath = Paths.get(path.toString(), filename);
            Files.write(filePath, bytes);
            log.debug("Image save uri: " + filePath);
            return filePath.toString();
        } catch (IOException e) {
            return MessageUtils.BAD_UPLOAD;
        }
    }

    public Resource getImage(Long id) {

        Post post = postsRepository.getPostById(id).orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NOT_FOUND));
        String imagePath = post.getImagePath();
        try {
            Path file = Paths.get(imagePath);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                log.debug("Get image " + imagePath);
                return resource;
            } else {
                log.error("Image by id " + id + MessageUtils.NOT_FOUND);
                throw new ResourceNotFoundException(MessageUtils.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException(MessageUtils.NOT_FOUND);
        }
    }

    public boolean deleteImg(Long id, User user) {

        try {
            Post post = postsRepository.getPostByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NOT_FOUND));
            String imagePath = post.getImagePath();
            File file = new File(imagePath);
            log.debug(MessageUtils.DELETE_OK + "image by id: " + id);
            return Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

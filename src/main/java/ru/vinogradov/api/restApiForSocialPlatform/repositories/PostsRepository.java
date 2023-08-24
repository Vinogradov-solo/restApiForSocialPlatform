package ru.vinogradov.api.restApiForSocialPlatform.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vinogradov.api.restApiForSocialPlatform.entities.Post;
import ru.vinogradov.api.restApiForSocialPlatform.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByIdIn(List<Long> idList, Pageable pageable);
    Optional<Post> getPostByIdAndUser(Long id, User user);
    Optional<Post> getPostById(Long id);
}

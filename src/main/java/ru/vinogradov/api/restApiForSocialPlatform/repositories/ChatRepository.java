package ru.vinogradov.api.restApiForSocialPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vinogradov.api.restApiForSocialPlatform.entities.Chat;
import ru.vinogradov.api.restApiForSocialPlatform.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllBySenderOrRecipient(User sender, User recipient);

    boolean existsById(Long aLong);

    Optional<Chat> findChatById(Long id);
}

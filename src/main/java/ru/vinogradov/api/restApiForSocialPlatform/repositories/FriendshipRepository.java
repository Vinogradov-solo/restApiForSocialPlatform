package ru.vinogradov.api.restApiForSocialPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vinogradov.api.restApiForSocialPlatform.entities.Friendship;
import ru.vinogradov.api.restApiForSocialPlatform.entities.User;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findByRecipient(User recipient);

    void deleteBySender(User sender);

    void deleteByRecipientAndSender(User recipient, User sender);
}

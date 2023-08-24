package ru.vinogradov.api.restApiForSocialPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vinogradov.api.restApiForSocialPlatform.entities.Subscriptions;
import ru.vinogradov.api.restApiForSocialPlatform.entities.User;

import java.util.List;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long> {

    List<Subscriptions> findAllByUser(User user);
    List<Subscriptions> findAllByFriend(User user);

    @Query(value = "select * from subscriptions s1 join " +
            "subscriptions s2 ON s1.friend_id = s2.user_id" +
            " where s1.user_id = ?1 and s1.user_id = s2.friend_id",
            nativeQuery = true)
    List<Subscriptions> findAllFriends(Long id);

    //    @Query(value = "select * from subscriptions  as sub join " +
//            "subscriptions as suber2 ON sub.friend_id = suber2.user_id" +
//            " where sub.user_id = ?1 and sub.friend_id = ?2",
//            nativeQuery = true)
    void deleteSubscriptionsByUserIdAndFriendId(Long myId, Long friendId);

}

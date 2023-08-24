# RESTful API for a social media platform.

Wrote a RESTful API for a social media platform that allows users to register, log in, create posts, reblog, subscribe to other users, and get their activity feed.

## Requirements met:
### Authentication and authorization:
  - Users can register by providing a username, email, and password.
  - Users can log in by providing the correct credentials.
  - The API must protect the privacy of user data, including password hashing and the use of JWTs.
### Post Management:
  - Users can create new posts by specifying text, title and attaching images.
  - Users can view other users' posts.
  - Users can update and delete their own posts.
### User Interaction:
  - Users can send friend requests to other users. From then on, the user who sent the request remains a subscriber until he/she unsubscribes. If the user who received the request accepts it, both users become friends. If rejected, the user who sent the request, as previously stated, still remains a subscriber.
  - Users who are friends are also subscribers to each other.
  - If one friend deletes the other from friends, the friend is also unsubscribed. The other user must still be a subscriber.
  - Friends can write messages to each other (chat implementation is not necessary, users can request correspondence with a query)
### Subscriptions and activity feed:
  - The user's activity feed should display the latest posts from users to whom the user is subscribed.
  - The activity feed should support pagination and sorting by post creation time.
### Error handling:
  - The API should handle and return understandable error messages for invalid request or internal server issues.
  - API should validate entered data and return informative messages in case of incorrect formatting.
### API Documentation:
  - The API should be well documented using tools such as Swagger or OpenAPI.
  - Documentation should include descriptions of available endpoints, request and response formats, and authentication requirements.

## Technologies and tools:
- Programming language: Java
- Framework: Spring Boot
- Database: PostgreSQL 
- Authentication and authorization: Spring Security
- API Documentation: Swagger


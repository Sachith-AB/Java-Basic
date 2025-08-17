package ports.user;

import domain.user.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findBydId(long id);
    User save(User user);
}

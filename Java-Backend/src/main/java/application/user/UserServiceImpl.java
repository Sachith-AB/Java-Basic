package application.user;

import domain.user.User;
import ports.user.UserRepository;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findBydId(id);
    }

    @Override
    public User createUser(User user) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Invalid email");
        return userRepository.save(user);
    }
}

package vn.edu.usth.service.user;

import jakarta.transaction.Transactional;
import vn.edu.usth.exception.UserNotFoundException;
import vn.edu.usth.service.user.UserService;
import vn.edu.usth.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vn.edu.usth.model.User;
import vn.edu.usth.util.PBKDF2Encoder;

import java.util.List;

@ApplicationScoped
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    @Inject
    PBKDF2Encoder passwordEncoder;

    @Inject
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        User user = userRepository.findById((long) id);
        if (user == null) {
            throw new UserNotFoundException("User doesn't exist");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.listAll();
    }

    @Transactional
    @Override
    public User addUser(User user) {
        userRepository.persist(user);
        return user;
    }

    @Transactional
    @Override
    public User updateUser(int id, User user) throws UserNotFoundException {
        User u = getUserById(id);
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setRole(user.getRole());
        return u;
    }

    @Override
    @Transactional
    public void deleteUser(int id) throws UserNotFoundException {
        userRepository.delete(getUserById(id));
    }
}

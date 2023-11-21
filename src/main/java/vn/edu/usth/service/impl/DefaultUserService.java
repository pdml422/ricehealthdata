package vn.edu.usth.service.impl;

import vn.edu.usth.exception.UserNotFoundException;
import vn.edu.usth.service.UserService;
import vn.edu.usth.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vn.edu.usth.model.User;
import java.util.List;
@ApplicationScoped
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    @Inject
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User getUserById(int id) throws UserNotFoundException {
        return userRepository.findByIdOptional((long) id).orElseThrow(() -> new UserNotFoundException("There user doesn't exist"));
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.listAll();
    }

    @Override
    public User updateUser(int id, User user) throws UserNotFoundException {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(int id) throws UserNotFoundException {

    }
//    @Transactional
//    @Override
//    public User updateUser(long id, User user) throws UserNotFoundException {
//        User existingUser = getUserById(id);
//        existingUser.setName(user.getName());
//        userRepository.persist(existingUser);
//        return existingUser;
//    }
}

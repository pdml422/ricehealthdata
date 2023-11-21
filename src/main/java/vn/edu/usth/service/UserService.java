package vn.edu.usth.service;
import java.util.List;
import vn.edu.usth.exception.UserNotFoundException;
import vn.edu.usth.model.User;
public interface UserService {
     User getUserById(int id) throws UserNotFoundException;

    List<User> getAllUsers();

    User updateUser(int id, User user) throws UserNotFoundException;

    User saveUser(User user);

    void deleteUser(int id) throws UserNotFoundException;
}

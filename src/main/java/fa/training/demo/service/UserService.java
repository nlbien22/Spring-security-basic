package fa.training.demo.service;

import fa.training.demo.entity.UserEntity;
import fa.training.demo.exceptions.ResourceNotFoundException;
import fa.training.demo.exceptions.UserAlreadyExistsException;
import fa.training.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerNewUser(UserEntity user) {
        if (userMapper.checkUsernameExists(user.getUsername()) > 0) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userMapper.checkEmailExists(user.getEmail()) > 0) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
    }

    public List<UserEntity> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Transactional
    public void updateUser(Long id, UserEntity userDetails) {
        Optional<UserEntity> user = Optional.ofNullable(userMapper.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));

        if (user.isPresent()) {
            user.get().setEnabled(userDetails.isEnabled());
            user.get().setPassword(userDetails.getPassword());
            user.get().setRole(userDetails.getRole());
            userMapper.updateUser(user.get());
        }
    }
}

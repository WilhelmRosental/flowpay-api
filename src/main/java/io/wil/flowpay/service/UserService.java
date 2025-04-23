package io.wil.flowpay.service;

import io.wil.flowpay.model.User;
import io.wil.flowpay.repository.UserRepository;
import io.wil.flowpay.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository   = userRepository;
        this.passwordEncoder  = passwordEncoder;
        this.jwtUtil          = jwtUtil;
    }

    public User createUser(User userRequest) throws Exception {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userRepository.save(userRequest);
    }

    public User getUserProfile(String token) throws Exception {
        String userId = jwtUtil.extractUserId(token); // Récupère l'ID utilisateur en tant que String
        return userRepository.findById(userId) // Utilise directement le String
                .orElseThrow(() -> new Exception("User not found!"));
    }

    public String loginUser(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Password is invalid");
        }
        return jwtUtil.generateToken(user.getId()); // Passe l'ID utilisateur (String)
    }

    public User updateUserProfile(String token, User updatedUser) throws Exception {
        String userId = jwtUtil.extractUserId(token); // Récupère l'ID utilisateur en tant que String
        User user = userRepository.findById(userId) // Utilise directement le String
                .orElseThrow(() -> new Exception("User not found!"));

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        return userRepository.save(user);
    }
}

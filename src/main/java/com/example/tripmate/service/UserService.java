package com.example.tripmate.service;

import com.example.tripmate.dto.UserProfileResponse;
import com.example.tripmate.entity.User;
import com.example.tripmate.exception.BadRequestException;
import com.example.tripmate.exception.ResourceNotFoundException;
import com.example.tripmate.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public UserProfileResponse getUserProfileById(Long id, String authenticatedEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equalsIgnoreCase(authenticatedEmail)) {
            throw new BadRequestException("You can only access your own profile");
        }

        return new UserProfileResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }

    public UserProfileResponse getCurrentUserProfile(String email) {
        User user = getUserByEmail(email);
        return new UserProfileResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }

    @Transactional
    public UserProfileResponse updateUser(Long id, String name, String password, String authenticatedEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equalsIgnoreCase(authenticatedEmail)) {
            throw new BadRequestException("You can only update your own profile");
        }

        if (name != null && !name.trim().isEmpty()) {
            user.setName(name.trim());
        }

        if (password != null && !password.trim().isEmpty()) {
            if (password.length() < 6) {
                throw new BadRequestException("Password must be at least 6 characters");
            }
            user.setPassword(passwordEncoder.encode(password));
        }

        User updated = userRepository.save(user);
        return new UserProfileResponse(updated.getId(), updated.getName(), updated.getEmail(), updated.getCreatedAt());
    }

    @Transactional
    public void deleteUser(Long id, String authenticatedEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equalsIgnoreCase(authenticatedEmail)) {
            throw new BadRequestException("You can only delete your own account");
        }

        userRepository.delete(user);
    }
}

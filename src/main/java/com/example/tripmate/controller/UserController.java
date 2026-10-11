package com.example.tripmate.controller;

import com.example.tripmate.dto.UserProfileResponse;
import com.example.tripmate.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserProfileResponse profile = userService.getCurrentUserProfile(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        UserProfileResponse profile = userService.getUserProfileById(id, userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponse> updateUser(@PathVariable Long id,
                                                          @RequestBody Map<String, String> body,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        String name = body.get("name");
        String password = body.get("password");
        UserProfileResponse updated = userService.updateUser(id, name, password, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteUser(id, userDetails.getUsername());
        return ResponseEntity.ok(Map.of("message", "User account deleted successfully"));
    }
}

package cam.example.digitalwalletapi.service;

import cam.example.digitalwalletapi.config.JwtUtils;
import cam.example.digitalwalletapi.entity.User;
import cam.example.digitalwalletapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Method to authenticate user and return token
    public String authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        // Return JWT token after successful authentication
        return jwtUtils.generateToken(user.getEmail());
    }

    // Method to register user and return token after registration
    public String registerUser(String email, String password) {
        String normalizedEmail = email.toLowerCase();

        // Check if the user already exists
        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Create a new user and encode the password
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(email, encodedPassword);

        try {
            // Save the new user to the database
            userRepository.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }

        // After user registration, return a JWT token
        return jwtUtils.generateToken(newUser.getEmail());
    }
}

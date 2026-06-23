package com.PBO2.Tokoelektronikzeus.service;

import org.springframework.stereotype.Service;
import com.PBO2.Tokoelektronikzeus.model.User;
import com.PBO2.Tokoelektronikzeus.repository.UserRepository;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        return null;
    }
}
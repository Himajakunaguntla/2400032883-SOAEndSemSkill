package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/token")
    public Map<String, String> generateToken(
            @RequestBody Map<String, String> data) {

        String username = data.get("username");
        String password = data.get("password");

        if ("admin".equals(username)
                && "1234".equals(password)) {

            String token = jwtUtil.generateToken(username);

            return Map.of("token", token);
        }

        throw new RuntimeException(
                "Invalid username or password");
    }
}
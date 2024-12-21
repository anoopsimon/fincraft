package io.anoopsimon.fincraft.controller;

import io.anoopsimon.fincraft.security.JwtUtil;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> generateToken(@RequestBody Map<String, String> request) {
        String secret = request.get("secret");
        String scope = request.get("scope");

        // Validate the secret
        if (!"mysecret".equals(secret)) { // Replace "mysecret" with your actual secret
            return ResponseEntity.status(403).body(Map.of("error", "Invalid secret"));
        }

        // Generate the token
        String token = jwtUtil.generateToken("system", scope); // "system" as a placeholder subject
        return ResponseEntity.ok(Map.of("token", token));
    }
}

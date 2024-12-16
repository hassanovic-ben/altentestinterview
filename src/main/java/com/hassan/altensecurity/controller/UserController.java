package com.hassan.altensecurity.controller;

import com.hassan.altensecurity.config.JwtService;
import com.hassan.altensecurity.model.Product;
import com.hassan.altensecurity.model.User;
import com.hassan.altensecurity.repository.ProductRepository;
import com.hassan.altensecurity.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ProductRepository productRepository;

    @PostMapping("/panel/{id_product}")
    public ResponseEntity<Product> addProduct(@PathVariable Integer id_product, @NonNull HttpServletRequest request) {
        try {
            Product product = productRepository.findById(id_product).orElseThrow(() -> new EmptyResultDataAccessException(1));
            final String authorizationHeader = request.getHeader("Authorization");
            String jwt = authorizationHeader.substring(7);
            String email = jwtService.extractUserName(jwt);
            User user = userRepository.findByEmail(email).orElseThrow();
            user.getPanel().add(product);
            userRepository.save(user);
            return ResponseEntity.ok(product);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/panel")
    public ResponseEntity<Set<Product>> getPanelProducts(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String email = jwtService.extractUserName(jwt);
        User user = userRepository.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(user.getPanel());
    }

    @DeleteMapping("/panel/{id_product}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer id_product, @NonNull HttpServletRequest request) {
        try {
            final String authorizationHeader = request.getHeader("Authorization");
            String jwt = authorizationHeader.substring(7);
            String email = jwtService.extractUserName(jwt);
            User user = userRepository.findByEmail(email).orElseThrow();
            Product product = user.getPanel().stream().filter(p -> Objects.equals(p.getProduct_id(), id_product)).findFirst().orElseThrow();
            user.getPanel().removeIf(p -> Objects.equals(p.getProduct_id(), id_product));
            userRepository.save(user);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/desired")
    public ResponseEntity<Set<Product>> getDesiredProducts(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String email = jwtService.extractUserName(jwt);
        User user = userRepository.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(user.getDesired());
    }
    @PostMapping("/desired/{id_product}")
    public ResponseEntity<Product> addToDesiredList(@PathVariable Integer id_product, @NonNull HttpServletRequest request) {
        try {
            Product product = productRepository.findById(id_product).orElseThrow(() -> new EmptyResultDataAccessException(1));
            final String authorizationHeader = request.getHeader("Authorization");
            String jwt = authorizationHeader.substring(7);
            String email = jwtService.extractUserName(jwt);
            User user = userRepository.findByEmail(email).orElseThrow();
            user.getDesired().add(product);
            userRepository.save(user);
            return ResponseEntity.ok(product);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Product not found");
        }
    }
    @DeleteMapping("/desired/{id_product}")
    public ResponseEntity<Product> deleteFromDesired(@PathVariable Integer id_product, @NonNull HttpServletRequest request) {
        try {
            String jwt = request.getHeader("Authorization").substring(7);
            String email = jwtService.extractUserName(jwt);
            User user = userRepository.findByEmail(email).orElseThrow();
            Product product = user.getPanel().stream().filter(p -> Objects.equals(p.getProduct_id(), id_product)).findFirst().orElseThrow();
            user.getDesired().removeIf(p -> Objects.equals(p.getProduct_id(), id_product));
            userRepository.save(user);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

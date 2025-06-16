package ru.netology.authorize.model;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserRepository {
    HashMap<String, List<Authorities>> repo = new HashMap<>();

    public UserRepository() {
        repo.put("user1-12345", List.of(Authorities.WRITE, Authorities.READ));
        repo.put("user2-54321", List.of(Authorities.WRITE, Authorities.READ, Authorities.DELETE));
        repo.put("user3-11111", List.of());
    }

    public List<Authorities> getUserAuthorities(String user, String password) {
        return repo.getOrDefault(user + "-" + password, List.of());
    }
}

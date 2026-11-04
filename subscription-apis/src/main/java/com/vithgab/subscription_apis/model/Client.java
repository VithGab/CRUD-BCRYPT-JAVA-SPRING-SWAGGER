package com.vithgab.subscription_apis.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCrypt;
import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 60)
    private String passwordHash;

    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Subscription> subscriptions = new ArrayList<>();

    public Client() {
    }
    public Client(Long id, String name, String email, String senha) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = hashPassword(senha);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
        public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public LocalDateTime getPasswordChangedAt() {
        return passwordChangedAt;
    }
    public void setPasswordChangedAt(LocalDateTime passwordChangedAt) {
        this.passwordChangedAt = passwordChangedAt;
    }
    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }
}

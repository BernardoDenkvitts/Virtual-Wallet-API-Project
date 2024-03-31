package com.virtualwallet.demo.Model.User;

import com.virtualwallet.demo.Model.CryptoType;

import java.lang.String;

import com.virtualwallet.demo.Service.User.Authority.Factory.UserAuthority;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Document("user")
public class User implements UserDetails
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    @Indexed(name = "UserEmailIndex", unique = true)
    private String email;
    private String pswd;
    private HashMap<CryptoType, CryptoAddress> cryptosAddress = new HashMap<>();
    private LocalDateTime timestamp = LocalDateTime.now();
    @Enumerated(value = EnumType.STRING)
    private Roles role = Roles.USER;

    public User() {}

    public User(String id, String name, String email, String pswd)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pswd = pswd;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPswd() {
        return pswd;
    }

    public HashMap<CryptoType, CryptoAddress> getCryptosAddress() {
        return cryptosAddress;
    }

    public void setCryptosAddress(HashMap<CryptoType, CryptoAddress> cryptosAddress) {
        this.cryptosAddress = cryptosAddress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new UserAuthority(role).getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.pswd;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


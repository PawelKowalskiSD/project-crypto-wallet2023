package com.app.crypto.wallet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private long userId;
    private String username;
    private String password;
    private String mailAddressee;
    private boolean isEnabled;
    @ManyToMany
    @JoinTable(
            name = "JOIN_USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")}
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(
            targetEntity = Wallet.class,
            mappedBy = "user",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Wallet> walletList = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private VerificationKey verificationKey;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Jwt> jwts = new ArrayList<>();

    public User(String username, String password, String mailAddressee) {
        this.username = username;
        this.password = password;
        this.mailAddressee = mailAddressee;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(long userId) {
        this.userId = userId;
    }

    public User(long userId, String username, String mailAddressee, boolean isEnabled, List<Role> roles, List<Wallet> walletList) {
        this.userId = userId;
        this.username = username;
        this.mailAddressee = mailAddressee;
        this.isEnabled = isEnabled;
        this.roles = roles;
        this.walletList = walletList;
    }

    public User(long userId, String username, String password, String mailAddressee) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.mailAddressee = mailAddressee;
    }

    public User(long userId, String username, String password, String mailAddressee, boolean isEnabled, List<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.mailAddressee = mailAddressee;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }

    public User(long userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String mailAddressee, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.mailAddressee = mailAddressee;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}

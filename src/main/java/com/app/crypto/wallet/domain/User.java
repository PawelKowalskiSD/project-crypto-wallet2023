package com.app.crypto.wallet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {

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
    private List<JwtToken> jwtTokens = new ArrayList<>();

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
}

package com.app.crypto.wallet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long jwtTokenId;
    private String token;
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Jwt(String token, boolean expired, User user) {
        this.token = token;
        this.expired = expired;
        this.user = user;
    }
}

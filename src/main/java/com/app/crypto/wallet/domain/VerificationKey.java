package com.app.crypto.wallet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VerificationKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verifyKeyId;

    private String value;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public VerificationKey(String value, User user) {
        this.value = value;
        this.user = user;
    }
}

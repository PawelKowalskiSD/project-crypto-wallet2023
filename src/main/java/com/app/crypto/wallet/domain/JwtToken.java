package com.app.crypto.wallet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtToken {
    private long jwtTokenId;
    private String token;
    private boolean expired;

}

package com.app.crypto.wallet.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadUserDto {

    @JsonProperty("id")
    private long userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("mail")
    private String mailAddressee;

    @JsonProperty("email_verification")
    private boolean isEnabled;

    @JsonProperty("roles")
    private List<ReadRoleDto> roles;

    @JsonProperty("wallets")
    private List<ReadWalletDto> walletList = new ArrayList<>();
}

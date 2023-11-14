package com.app.crypto.wallet.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditUserDto {

    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("mail")
    private String mailAddressee;

    public EditUserDto(String username) {
        this.username = username;
    }

    public EditUserDto(String username, String password, String mailAddressee) {
        this.username = username;
        this.password = password;
        this.mailAddressee = mailAddressee;
    }
}

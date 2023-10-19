package com.app.crypto.wallet.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchCoinDto {
    @JsonProperty("name")
    private String coinName;

    @Override
    public String toString() {
        return "SearchCoinDto{" +
                "coinName='" + coinName + '\'' +
                '}';
    }
}

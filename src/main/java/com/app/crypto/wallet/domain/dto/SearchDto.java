package com.app.crypto.wallet.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchDto {
    @JsonProperty("coins")
    private SearchCoinDto[] searchCoinDto;

    @Override
    public String toString() {
        return "SearchDto{" +
                "searchCoinDto=" + Arrays.toString(searchCoinDto) +
                '}';
    }
}

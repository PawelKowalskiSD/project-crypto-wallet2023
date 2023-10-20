package com.app.crypto.wallet.domain;

import com.app.crypto.wallet.domain.dto.SearchCoinDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    private List<SearchCoin> coins;

}

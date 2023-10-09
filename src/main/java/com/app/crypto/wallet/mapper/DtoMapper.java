package com.app.crypto.wallet.mapper;

import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.dto.EditUserDto;
import com.app.crypto.wallet.domain.dto.ReadCoinDto;
import com.app.crypto.wallet.domain.dto.ReadUserDto;
import com.app.crypto.wallet.domain.dto.ReadWalletDto;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {
    public User mapUserDtoToUser(EditUserDto editUserDto) {
        return new User(editUserDto.getUsername(),
                editUserDto.getPassword(),
                editUserDto.getMailAddressee());
    }

    public ReadUserDto mapUserToUserDto(User user) {
        return new ReadUserDto(user.getId(),
                user.getUsername(),
                user.getMailAddressee(),
                user.isEnabled(),
        user.getRole(), user.getWalletList().stream()
                .map(u -> new ReadWalletDto(
                        u.getWalletId(),
                        u.getWalletName(),
                        u.getUser().getId(),
                        mapCoinToCoinReadDto(u.getCoinList())))
                .collect(Collectors.toList()));
    }

    public List<ReadCoinDto> mapCoinToCoinReadDto(List<Coin> coinList) {
        return coinList.stream()
                .map(c -> new ReadCoinDto(
                        c.getCoinId(),
                        c.getCoinName(),
                        c.getSymbol(),
                        c.getQuantity(),
                        c.getAveragePurchasePrice(),
                        c.getAverageSalePrice(),
                        c.getCurrentPrice()))
                        .collect(Collectors.toList());
    }
}

package com.app.crypto.wallet.mapper;

import com.app.crypto.wallet.domain.Coin;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.domain.Wallet;
import com.app.crypto.wallet.domain.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {
    public User mapUserDtoToUser(EditUserDto editUserDto) {
        return new User(editUserDto.getUsername(),
                editUserDto.getPassword(),
                editUserDto.getMailAddressee());
    }

    public ReadUserDto mapUserToUserDto(User user) {
        return new ReadUserDto(user.getUserId(),
                user.getUsername(),
                user.getMailAddressee(),
                user.isEnabled(),
        user.getRole(), user.getWalletList().stream()
                .map(u -> new ReadWalletDto(
                        u.getWalletId(),
                        u.getWalletName(),
                        u.getUser().getUserId(),
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

    public List<ReadWalletDto> mapReadWalletDtoToWalletLists(List<Wallet> wallets) {
        return wallets.stream()
                .map(wallet -> new ReadWalletDto(
                        wallet.getWalletId(),
                        wallet.getWalletName(),
                        wallet.getUser().getUserId(),
                        mapCoinToCoinReadDto(wallet.getCoinList())))
                .collect(Collectors.toList());
    }

    public ReadWalletDto mapReadWalletDtoToWallet(Wallet wallet) {
        return new ReadWalletDto(
                wallet.getWalletId(),
                wallet.getWalletName(),
                wallet.getUser().getUserId(),
                mapCoinToCoinReadDto(wallet.getCoinList()));
    }

    public Wallet mapCreateWalletDtoToWallet(CreateWalletDto createWalletDto) {
        return new Wallet();
    }

    public Wallet mapEditWallDtoToWallet(EditWalletDto editWalletDto) {
        return new Wallet();
    }

    public EditWalletDto mapWalletToEditWalletDto(Wallet wallet) {
        return new EditWalletDto();
    }
}

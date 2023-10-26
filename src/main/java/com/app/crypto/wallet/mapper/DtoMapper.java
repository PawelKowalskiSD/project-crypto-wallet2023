package com.app.crypto.wallet.mapper;

import com.app.crypto.wallet.domain.*;
import com.app.crypto.wallet.domain.dto.*;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class DtoMapper {

    public User mapToUser(EditUserDto editUserDto) {
        return new User(
                editUserDto.getUsername(),
                editUserDto.getPassword(),
                editUserDto.getMailAddressee());
    }

    public User mapToUser(AuthRequestDto authRequestDto) {
        return new User(
                authRequestDto.getUsername(),
                authRequestDto.getPassword());
    }

    public User mapToUser(CreateUserDto createUserDto) {
        return new User(
                createUserDto.getUsername(),
                createUserDto.getPassword(),
                createUserDto.getMailAddressee()
        );
    }

    public AuthResponseDto mapToAuthResponseDto(Jwt token) {
        return new AuthResponseDto(
                token.getToken());
    }

    public ReadUserDto mapToReadUserDto(User user) {
        return new ReadUserDto(
                user.getUserId(),
                user.getUsername(),
                user.getMailAddressee(),
                user.isEnabled(),
                user.getRoles().stream().map(r -> new ReadRoleDto(r.getRoleId(), r.getRoleName())).collect(Collectors.toList()),
                mapToReadWalletDtoList(user.getWalletList()));
    }

    public List<ReadUserDto> mapToReadUserDtoList(List<User> users) {
        return users.stream()
                .map(u -> new ReadUserDto(
                        u.getUserId(),
                        u.getUsername(),
                        u.getMailAddressee(),
                        u.isEnabled(),
                        u.getRoles().stream().map(r -> new ReadRoleDto(r.getRoleId(), r.getRoleName())).collect(Collectors.toList()),
                        mapToReadWalletDtoList(u.getWalletList())))
                .collect(Collectors.toList());
    }

    public Wallet mapToWallet(CreateWalletDto createWalletDto) {
        return new Wallet(
                createWalletDto.getWalletName());
    }

    public Wallet mapToWallet(EditWalletDto editWalletDto) {
        return new Wallet(
                editWalletDto.getWalletName());
    }

    public ReadWalletDto mapToReadWalletDto(Wallet wallet) {
        return new ReadWalletDto(
                wallet.getWalletId(),
                wallet.getWalletName(),
                mapToReadCoinDtoList(wallet.getCoinList()));
    }


    public List<ReadWalletDto> mapToReadWalletDtoList(List<Wallet> wallets) {
        return wallets.stream()
                .map(wallet -> new ReadWalletDto(
                        wallet.getWalletId(),
                        wallet.getWalletName(),
                        mapToReadCoinDtoList(wallet.getCoinList())))
                .collect(Collectors.toList());
    }

    public Coin mapToCoin(AddCoinDto addCoinDto) throws WalletNotFoundException {
        return new Coin(
                addCoinDto.getCoinName(),
                addCoinDto.getQuantity());
    }

    public Coin mapToCoin(SellCoinDto sellCoinDto) {
        return new Coin(
                sellCoinDto.getCoinName(),
                sellCoinDto.getQuantity(),
                sellCoinDto.getCurrentPrice());
    }

    public ReadCoinDto mapToReadCoinDto(Coin coin) {
        return new ReadCoinDto(
                coin.getCoinId(),
                coin.getCoinName(),
                coin.getSymbol(),
                coin.getQuantity(),
                coin.getCurrentPrice());
    }

    public List<ReadCoinDto> mapToReadCoinDtoList(List<Coin> coinList) {
        return coinList.stream()
                .map(c -> new ReadCoinDto(
                        c.getCoinId(),
                        c.getCoinName(),
                        c.getSymbol(),
                        c.getQuantity(),
                        c.getCurrentPrice()))
                .collect(Collectors.toList());
    }

    public List<Role> mapToRole(AddRoleDto addRoleDto) {
        return Stream.of(new Role(addRoleDto.getRoleName()))
                .collect(Collectors.toList());
    }

    public List<Role> mapToRole(RemoveRoleDto removeRoleDto) {
        return Stream.of(new Role(removeRoleDto.getRoleName())).collect(Collectors.toList());
    }

    public ReadRoleDto mapToReadRoleDto(Role role) {
        return new ReadRoleDto(
                role.getRoleId(),
                role.getRoleName());
    }

    public List<ReadRoleDto> mapToReadRoleDtoList(List<Role> roles) {
        return roles.stream()
                .map(r -> new ReadRoleDto(
                        r.getRoleId(),
                        r.getRoleName()))
                .collect(Collectors.toList());
    }

    public SearchDto mapToSearchDto(Search searchCoin) {
        return new SearchDto(searchCoin.getCoins().stream()
                .map(c -> new SearchCoinDto(c.getCoinId(), c.getCoinName(), c.getSymbol(), c.getMarketCapRank()))
                .collect(Collectors.toList()));
    }
}

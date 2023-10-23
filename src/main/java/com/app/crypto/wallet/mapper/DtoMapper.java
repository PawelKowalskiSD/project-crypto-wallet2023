package com.app.crypto.wallet.mapper;

import com.app.crypto.wallet.domain.*;
import com.app.crypto.wallet.domain.dto.*;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DtoMapper {
    private final WalletService walletService;

    public User mapToUser(EditUserDto editUserDto) {
        return new User(
                editUserDto.getUserId(),
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
                createUserDto.getMailAddressee());
    }

    public AuthResponseDto mapToAuthResponseDto(JwtToken token) {
        return new AuthResponseDto(
                token.getToken());
    }

    public CreateUserDto mapToCreateUserDto(User user) {
        List<String> roles = List.of("USER");
        return new CreateUserDto(
                user.getUsername(),
                user.getPassword(),
                user.getMailAddressee(),
                roles);
    }

    public ReadUserDto mapToReadUserDto(User user) {
        return new ReadUserDto(
                user.getUserId(),
                user.getUsername(),
                user.getMailAddressee(),
                user.isEnabled(),
                user.getRoles(),
                mapToReadWalletDtoList(user.getWalletList()));
    }

    public List<ReadUserDto> mapToReadUserDtoList(List<User> users) {
        return users.stream()
                .map(u -> new ReadUserDto(
                        u.getUserId(),
                        u.getUsername(),
                        u.getMailAddressee(),
                        u.isEnabled(),
                        u.getRoles(),
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
                wallet.getUser().getUserId(),
                mapToReadCoinDtoList(wallet.getCoinList()));
    }


    public List<ReadWalletDto> mapToReadWalletDtoList(List<Wallet> wallets) {
        return wallets.stream()
                .map(wallet -> new ReadWalletDto(
                        wallet.getWalletId(),
                        wallet.getWalletName(),
                        wallet.getUser().getUserId(),
                        mapToReadCoinDtoList(wallet.getCoinList())))
                .collect(Collectors.toList());
    }

    public Coin mapToCoin(AddCoinDto addCoinDto) throws WalletNotFoundException {
        Wallet wallet = walletService.findWallet(addCoinDto.getWalletId());
        return new Coin(
                addCoinDto.getCoinName(),
                addCoinDto.getQuantity(),
                wallet);
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

    public Role mapToRole(AddRoleDto addRoleDto) {
        return new Role(
                addRoleDto.getRoleName());
    }

    public Role mapToRole(RemoveRoleDto removeRoleDto) {
        return new Role(
                removeRoleDto.getRoleName());
    }

    public AddRoleDto mapToAddRoleDto(Role role) {
        return new AddRoleDto(
                role.getRoleName());
    }

    public ReadRoleDto mapToReadRoleDto(Role role) {
        return new ReadRoleDto(
                role.getRoleName());
    }

    public RemoveRoleDto mapToRemoveRoleDto(Role role) {
        return new RemoveRoleDto(
                role.getRoleName());
    }

    public List<ReadRoleDto> mapToReadRoleDtoList(List<Role> roles) {
        return roles.stream()
                .map(r -> new ReadRoleDto(
                        r.getRoleName()))
                .collect(Collectors.toList());
    }


    public SearchDto mapToSearchDto(Search searchCoin) {
        return new SearchDto(searchCoin.getCoins().stream()
                .map(c -> new SearchCoinDto(c.getCoinId(), c.getCoinName(), c.getSymbol(), c.getMarketCapRank()))
                .collect(Collectors.toList()));
    }

    public AddCoinDto mapToAddCoinDto(Coin coin) {
        return new AddCoinDto(
                coin.getCoinName(),
                coin.getSymbol(),
                coin.getQuantity(),
                coin.getCurrentPrice(),
                coin.getWallet().getWalletId());
    }
}

package com.app.crypto.wallet.mapper;

import com.app.crypto.wallet.domain.*;
import com.app.crypto.wallet.domain.dto.*;
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
        return new CreateUserDto(
                user.getUsername(),
                user.getPassword(),
                user.getMailAddressee());
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

    public EditWalletDto mapToEditWalletDto(Wallet wallet) {
        return new EditWalletDto(
                wallet.getWalletName(),
                wallet.getUser().getUserId());
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

    public Coin mapToCoin(SearchCoinDto searchCoinDto) {
        return new Coin(
                searchCoinDto.getCoinName());
    }

    public Coin mapToCoin(AddCoinDto addCoinDto) {
        Wallet wallet = walletService.findWallet(addCoinDto.getWalletId());
        return new Coin(
                addCoinDto.getCoinName(),
                addCoinDto.getQuantity(),
                wallet);
    }

    public Coin mapToCoin(SellCoinDto sellCoinDto) {
        Wallet wallet = walletService.findWallet(sellCoinDto.getWalletId());
        return new Coin(
                sellCoinDto.getCoinName(),
                sellCoinDto.getQuantity(),
                sellCoinDto.getCurrentPrice(),
                sellCoinDto.getAverageSalePrice(),
                sellCoinDto.getTotalValueOfCoinsSold(),
                wallet);
    }

    public SearchCoinDto mapToSearchCoinDto(Coin coin) {
        return new SearchCoinDto(
                coin.getCoinName());
    }

    public AddCoinDto mapToAddCoinDto(Coin coin) {
        return new AddCoinDto(
                coin.getCoinName(),
                coin.getQuantity(),
                coin.getWallet().getWalletId());
    }

    public SellCoinDto mapToSellCoinDto(Coin coin) {
        return new SellCoinDto(
                coin.getCoinName(),
                coin.getQuantity(),
                coin.getCurrentPrice(),
                coin.getAverageSalePrice(),
                coin.getWallet().getWalletId(),
                coin.getTotalValueOfCoinsSold());
    }

    public ReadCoinDto mapToReadCoinDto(Coin coin) {
        return new ReadCoinDto(
                coin.getCoinId(),
                coin.getCoinName(),
                coin.getSymbol(),
                coin.getQuantity(),
                coin.getCurrentPrice(),
                coin.getAveragePurchasePrice(),
                coin.getAverageSalePrice());
    }

    public List<ReadCoinDto> mapToReadCoinDtoList(List<Coin> coinList) {
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

    public Role mapToRole(CreateRoleDto createRoleDto) {
        return new Role(
                createRoleDto.getRoleName());
    }

    public Role mapToRole(AddRoleDto addRoleDto) {
        return new Role(
                addRoleDto.getRoleName());
    }

    public Role mapToRole(RemoveRoleDto removeRoleDto) {
        return new Role(
                removeRoleDto.getRoleName());
    }

    public CreateRoleDto mapToCreateRoleDto(Role role) {
        return new CreateRoleDto(
                role.getRoleName());
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
}

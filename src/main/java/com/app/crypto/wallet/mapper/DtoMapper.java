package com.app.crypto.wallet.mapper;

import com.app.crypto.wallet.domain.*;
import com.app.crypto.wallet.domain.dto.*;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.service.RoleService;
import com.app.crypto.wallet.service.UserService;
import com.app.crypto.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DtoMapper {

    private final UserService userService;

    private final RoleService roleService;

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
                editWalletDto.getWalletId(),
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

    public Coin mapToCoin(AddCoinDto addCoinDto) throws WalletNotFoundException, UserPermissionsException {
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
                coin.getCurrentPrice(),
                coin.getAveragePurchasePrice(),
                coin.getAverageSalePrice(),
                coin.getTotalValueOfCoinsSold(),
                coin.getTotalValuePurchaseCoin(),
                coin.getWallet().getWalletId());
    }

    public List<ReadCoinDto> mapToReadCoinDtoList(List<Coin> coinList) {
        return coinList.stream()
                .map(c -> new ReadCoinDto(
                        c.getCoinId(),
                        c.getCoinName(),
                        c.getSymbol(),
                        c.getQuantity(),
                        c.getCurrentPrice(),
                        c.getAveragePurchasePrice(),
                        c.getAverageSalePrice(),
                        c.getTotalValueOfCoinsSold(),
                        c.getTotalValuePurchaseCoin()
                        ))
                .collect(Collectors.toList());
    }

    public Role mapToRole(InputDataRoleDto inputDataRoleDto) throws UserNotFoundException, RoleNotFoundException {

        User user = userService.findByUserId(inputDataRoleDto.getUserId());
        Role role = roleService.findByRoleName(inputDataRoleDto.getRoleName());
        List<User> users = userService.getAllUser();
        return new Role(
                role.getRoleId(),
                role.getRoleName(),
                users.stream().filter(u -> u.getUserId() == user.getUserId()).collect(Collectors.toList())
        );
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

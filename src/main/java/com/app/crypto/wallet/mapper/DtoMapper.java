package com.app.crypto.wallet.mapper;

import com.app.crypto.wallet.domain.*;
import com.app.crypto.wallet.domain.dto.*;
import com.app.crypto.wallet.service.UserService;
import com.app.crypto.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class DtoMapper {
    private final WalletService walletService;
    private final UserService userService;

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
                user.getRole(),
                user.getWalletList().stream()
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
        return new Wallet(createWalletDto.getWalletName());
    }

    public Wallet mapEditWallDtoToWallet(EditWalletDto editWalletDto) {
        return new Wallet(editWalletDto.getWalletName());
    }

    public EditWalletDto mapWalletToEditWalletDto(Wallet wallet) {
        return new EditWalletDto(
                wallet.getWalletName(),
                wallet.getUser().getUserId());
    }

    public Coin mapSearchCoinDtoToSearchCoin(SearchCoinDto searchCoinDto) {
        return new Coin(searchCoinDto.getCoinName());
    }

    public SearchCoinDto mapSearchCoinToSearchCoinDto(Coin coin) {
        return new SearchCoinDto(coin.getCoinName());
    }

    public Coin mapAddCoinDtoToCoin(AddCoinDto addCoinDto) {
        Wallet wallet = walletService.findWallet(addCoinDto.getWalletId());
        User user = userService.getUserById(addCoinDto.getUserId());
        return new Coin(
                addCoinDto.getCoinName(),
                addCoinDto.getQuantity(),
                wallet,
                user);
    }

    public AddCoinDto mapCoinToAddCoinDto(Coin coin) {
        return new AddCoinDto(
                coin.getCoinName(),
                coin.getQuantity(),
                coin.getWallet().getWalletId(),
                coin.getUser().getUserId());
    }

    public Coin mapCoinDtoToSellCoin(SellCoinDto sellCoinDto) {
        Wallet wallet = walletService.findWallet(sellCoinDto.getWalletId());
        User user = userService.getUserById(sellCoinDto.getUserId());
        return new Coin(
                sellCoinDto.getCoinName(),
                sellCoinDto.getQuantity(),
                sellCoinDto.getCurrentPrice(),
                sellCoinDto.getAverageSalePrice(),
                sellCoinDto.getTotalValueOfCoinsSold(),
                wallet,
                user);
    }

    public SellCoinDto mapCoinToSellCoinDto(Coin coin) {
        return new SellCoinDto(
                coin.getCoinName(),
                coin.getQuantity(),
                coin.getCurrentPrice(),
                coin.getAverageSalePrice(),
                coin.getUser().getUserId(),
                coin.getWallet().getWalletId(),
                coin.getTotalValueOfCoinsSold());
    }

    public ReadCoinDto mapCoinDtoToCoin(Coin coin) {
        return new ReadCoinDto(
                coin.getCoinId(),
                coin.getCoinName(),
                coin.getSymbol(),
                coin.getQuantity(),
                coin.getCurrentPrice(),
                coin.getAveragePurchasePrice(),
                coin.getAverageSalePrice());
    }

    public Role mapCreateRoleDtoToRole(CreateRoleDto createRoleDto) {
        return new Role(createRoleDto.getRoleName());
    }

    public CreateRoleDto mapRoleToCreateRoleDto(Role role) {
        return new CreateRoleDto(
                role.getRoleName(),
                role.getUser().getUserId());
    }

    public List<ReadUserDto> mapUserListToReadUserDtoList(List<User> users) {
        return new ArrayList<>();
    }

    public ReadUserDto mapReadUserDtoToUser(User user) {
        return new ReadUserDto(
                user.getUserId(),
                user.getUsername(),
                user.getMailAddressee(),
                user.isEnabled(),
                user.getRole(),
                user.getWalletList().stream()
                        .map(w -> new ReadWalletDto(w.getWalletId(), w.getWalletName(), mapCoinToCoinReadDto(w.getCoinList()))).collect(Collectors.toList()));
    }

    public AddRoleDto mapRoleToAddRoleDto(Role role) {
        return new AddRoleDto();
    }

    public Role mapAddRoleDtoToRole(AddRoleDto addRoleDto) {
        User user = userService.getUserById(addRoleDto.getUserId());
        return new Role(
                addRoleDto.getRoleName(),
                user);
    }

    public List<ReadRoleDto> mapRoleListsToReadRoleDtoLists(List<Role> roles) {
        return new ArrayList<>();
    }

    public ReadRoleDto mapRoleToReadRoleDto(Role role) {
        return new ReadRoleDto(
                role.getRoleName());
    }

    public User mapAuthRequestDtoToUser(AuthRequestDto authRequestDto) {
        return new User(
                authRequestDto.getUsername(),
                authRequestDto.getPassword());
    }

    public AuthResponseDto mapJwtTokenToAuthResponseDto(JwtToken token) {
        return new AuthResponseDto(
                token.getToken());
    }
}

package com.app.crypto.wallet.mapper;

import com.app.crypto.wallet.domain.*;
import com.app.crypto.wallet.domain.dto.*;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
import com.app.crypto.wallet.exceptions.UserPermissionsException;
import com.app.crypto.wallet.exceptions.WalletNotFoundException;
import com.app.crypto.wallet.repository.WalletRepository;
import com.app.crypto.wallet.service.RoleService;
import com.app.crypto.wallet.service.UserService;
import com.app.crypto.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DtoMapperTest {

    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private WalletService walletService;
    @InjectMocks
    private DtoMapper dtoMapper;

    @Test
    void shouldMapEditUserDtoToUser() {
        //Given
        EditUserDto requestToEditUser = new EditUserDto("jan", "jan123", "jan@wp.pl");
        //When
        User result = dtoMapper.mapToUser(requestToEditUser);
        //Then
        assertEquals("jan", result.getUsername());
        assertEquals("jan123", result.getPassword());
        assertEquals("jan@wp.pl", result.getMailAddressee());
    }

    @Test
    void shouldMapAuthRequestDtoToUser() {
        //Given
        AuthRequestDto authRequestDto = new AuthRequestDto("jan", "jan123");
        //When
        User result = dtoMapper.mapToUser(authRequestDto);
        //Then
        assertEquals("jan", result.getUsername());
        assertEquals("jan123", result.getPassword());
    }

    @Test
    void shouldMapCreateUserDtoToUser() {
        //Given
        CreateUserDto requestCreateUser = new CreateUserDto("jan", "jan123", "jan@wp.pl");
        //When
        User result = dtoMapper.mapToUser(requestCreateUser);
        //Then
        assertEquals("jan", result.getUsername());
        assertEquals("jan123", result.getPassword());
        assertEquals("jan@wp.pl", result.getMailAddressee());
    }

    @Test
    void shouldMapToAuthResponseDto() {
        //Given
        Jwt jwt = new Jwt(1L,
                "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
                "eyJpZCI6MiwiZXhwIjoxNjk4OTQyMjU1LCJyb2xlcyI6WyJVU0VSIl19." +
                "51vlH-J50hc190fxuXS9qnSvPZJlCRQ67fL1d0sANezXf2oKZpJn-Jyvz_cJvuwr5OMW53ADeMuKF1Y0m8jtUw", false, new User());
        //When
        AuthResponseDto result = dtoMapper.mapToAuthResponseDto(jwt);
        //Then
        assertEquals("eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
                "eyJpZCI6MiwiZXhwIjoxNjk4OTQyMjU1LCJyb2xlcyI6WyJVU0VSIl19." +
                "51vlH-J50hc190fxuXS9qnSvPZJlCRQ67fL1d0sANezXf2oKZpJn-Jyvz_cJvuwr5OMW53ADeMuKF1Y0m8jtUw", result.getAccessToken());
    }

    @Test
    void shouldMapToReadUserDto() {
        //Given
        User user = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER")));
        //When
        ReadUserDto result = dtoMapper.mapToReadUserDto(user);
        //Then
        assertEquals(1L, result.getUserId());
        assertEquals("jan", result.getUsername());
        assertEquals("jan@wp.pl", result.getMailAddressee());
        assertTrue(result.isEnabled());
        assertEquals("USER", result.getRoles().get(0).getRoleName());
    }

    @Test
    void shouldMapToReadUserDtoList() {
        //Given
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER"))));
        users.add(new User(2L, "tom", "tom123", "tom@wp.pl", false, List.of(new Role(1L,"ADMIN"))));
        //When
        List<ReadUserDto> result = dtoMapper.mapToReadUserDtoList(users);
        //Then
        assertEquals(1L, result.get(0).getUserId());
        assertEquals("jan", result.get(0).getUsername());
        assertEquals("jan@wp.pl", result.get(0).getMailAddressee());
        assertTrue(result.get(0).isEnabled());
        assertEquals("USER", result.get(0).getRoles().get(0).getRoleName());
        assertEquals(2L, result.get(1).getUserId());
        assertEquals("tom", result.get(1).getUsername());
        assertEquals("tom@wp.pl", result.get(1).getMailAddressee());
        assertEquals("ADMIN", result.get(1).getRoles().get(0).getRoleName());
        assertFalse(result.get(1).isEnabled());
    }

    @Test
    void shouldMapCreateWalletDtoToWallet() {
        //Given
        CreateWalletDto createWalletDto = new CreateWalletDto("new wallet");
        //When
        Wallet result = dtoMapper.mapToWallet(createWalletDto);
        //Then
        assertEquals("new wallet", result.getWalletName());
    }

    @Test
    void shouldMapEditWalletDtoToWallet() {
        //Given
        EditWalletDto editWalletDto = new EditWalletDto(1L, "new wallet");
        //When
        Wallet result = dtoMapper.mapToWallet(editWalletDto);
        //Then
        assertEquals(1L, result.getWalletId());
        assertEquals("new wallet", result.getWalletName());
    }

    @Test
    void shouldMapToReadWalletDto() {
        //Given
        User user = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER")));
        Coin coins = new Coin(1L, "bitcoin", "btc", new BigDecimal(32000));
        Wallet wallet = new Wallet(1L, "new wallet", user, List.of(coins));
        //When
        ReadWalletDto result = dtoMapper.mapToReadWalletDto(wallet);
        //Then
        assertEquals(1L, result.getWalletId());
        assertEquals("new wallet", result.getWalletName());
        assertEquals(1L, result.getCoins().get(0).getCoinId());
        assertEquals("bitcoin", result.getCoins().get(0).getCoinName());
        assertEquals("btc", result.getCoins().get(0).getSymbol());
        assertEquals(new BigDecimal(32000), result.getCoins().get(0).getCurrentPrice());
    }

    @Test
    void shouldMapToReadWalletDtoList() {
        //Given
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(new Wallet(1L , "First wallet"));
        wallets.add(new Wallet(2L , "Second wallet"));
        //When
        List<ReadWalletDto> result = dtoMapper.mapToReadWalletDtoList(wallets);
        //Then
        assertEquals(1L, result.get(0).getWalletId());
        assertEquals(2L, result.get(1).getWalletId());
        assertEquals("First wallet", result.get(0).getWalletName());
        assertEquals("Second wallet", result.get(1).getWalletName());
    }

    @Test
    void shouldMapAddCoinDtoToCoin() throws WalletNotFoundException, UserPermissionsException {
        //Given
        Wallet wallet = new Wallet(1L, "wallet");
        AddCoinDto addCoinDto = new AddCoinDto("bitcoin", new BigDecimal(10), 1L);
        when(walletService.findWallet(1L)).thenReturn(wallet);
        //When
        Coin result = dtoMapper.mapToCoin(addCoinDto);
        //Then
        assertEquals("bitcoin", result.getCoinName());
        assertEquals(new BigDecimal(10), result.getQuantity());
    }

    @Test
    void shouldMapSellCoinDtoToCoin() {
        //Given
        SellCoinDto sellCoinDto = new SellCoinDto("bitcoin", new BigDecimal(2), new BigDecimal(32000));
        //When
        Coin result = dtoMapper.mapToCoin(sellCoinDto);
        //Then
        assertEquals("bitcoin", result.getCoinName());
        assertEquals(new BigDecimal(2), result.getQuantity());
        assertEquals(new BigDecimal(32000), result.getCurrentPrice());
    }

    @Test
    void shouldMapToReadCoinDto() {
        //Given
        Coin coin = new Coin(1L, "bitcoin", "btc", new BigDecimal(2), new BigDecimal(32000), new Wallet(1L, "wallet"));
        //When
        ReadCoinDto result = dtoMapper.mapToReadCoinDto(coin);
        //Then
        assertEquals(1L, result.getCoinId());
        assertEquals("bitcoin", result.getCoinName());
        assertEquals("btc", result.getSymbol());
        assertEquals(new BigDecimal(2), result.getQuantity());
        assertEquals(new BigDecimal(32000), result.getCurrentPrice());
        assertEquals(1L, result.getWalletId());
    }

    @Test
    void shouldMapToReadCoinDtoList() throws WalletNotFoundException, UserPermissionsException {
        //Given
        List<Coin> coins = new ArrayList<>();
        coins.add(new Coin(1L, "Bitcoin", "btc", new BigDecimal(2), new BigDecimal(32000)));
        coins.add(new Coin(2L, "Evmos", "evmos", new BigDecimal(120), new BigDecimal(1)));
        Wallet wallet = new Wallet(1L, "new wallet", new User(), coins);
        //When
        List<ReadCoinDto> result = dtoMapper.mapToReadCoinDtoList(coins);
        //Then
        assertEquals(1L, result.get(0).getCoinId());
        assertEquals("Bitcoin", result.get(0).getCoinName());
        assertEquals("btc", result.get(0).getSymbol());
        assertEquals(new BigDecimal(2), result.get(0).getQuantity());
        assertEquals(new BigDecimal(32000), result.get(0).getCurrentPrice());
        assertEquals(2L, result.get(1).getCoinId());
        assertEquals("Evmos", result.get(1).getCoinName());
        assertEquals("evmos", result.get(1).getSymbol());
        assertEquals(new BigDecimal(120), result.get(1).getQuantity());
        assertEquals(new BigDecimal(1), result.get(1).getCurrentPrice());
    }

    @Test
    void shouldMapToRole() throws UserNotFoundException, RoleNotFoundException {
        //Given
        Role role = new Role(1L, "ADMIN");
        User user = new User(1L, "jan", "jan123", "jan@wp.pl", true, List.of(new Role(1L,"USER")));
        List<User> users = new ArrayList<>();
        users.add(user);
        InputDataRoleDto inputDataRoleDto = new InputDataRoleDto(role.getRoleName(), user.getUserId());
        when(userService.findByUserId(inputDataRoleDto.getUserId())).thenReturn(user);
        when(roleService.findByRoleName(inputDataRoleDto.getRoleName())).thenReturn(role);
        when(userService.getAllUser()).thenReturn(users);
        //When
        Role result = dtoMapper.mapToRole(inputDataRoleDto);
        //Then
        assertEquals("ADMIN", result.getRoleName());
        assertEquals(1L, result.getUsers().get(0).getUserId());
    }

    @Test
    void shouldMapToReadRoleDto() {
        //Given
        Role role = new Role(1L, "USER");
        //When
        ReadRoleDto result = dtoMapper.mapToReadRoleDto(role);
        //Then
        assertEquals(1L, result.getRoleId());
        assertEquals("USER", result.getRoleName());
    }

    @Test
    void shouldMapToReadRoleDtoList() {
        //Given
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, "USER"));
        roles.add(new Role(2L, "ADMIN"));
        //When
        List<ReadRoleDto> result = dtoMapper.mapToReadRoleDtoList(roles);
        //Then
        assertEquals(1L, result.get(0).getRoleId());
        assertEquals("USER", result.get(0).getRoleName());
        assertEquals(2L, result.get(1).getRoleId());
        assertEquals("ADMIN", result.get(1).getRoleName());
    }

    @Test
    void shouldMapToSearchDto() {
        //Given
        List<SearchCoin> searchCoins = new ArrayList<>();
        searchCoins.add(new SearchCoin("bitcoin", "Bitcoin", "btc", 600600600634L));
        searchCoins.add(new SearchCoin("evmos", "Evmos", "evmos", 60600634L));
        searchCoins.add(new SearchCoin("juno", "Juno", "juno", 36006004L));
        Search search = new Search(searchCoins);
        //When
        SearchDto result = dtoMapper.mapToSearchDto(search);
        //Then
        assertEquals("bitcoin", result.getCoins().get(0).getCoinId());
        assertEquals("Bitcoin", result.getCoins().get(0).getCoinName());
        assertEquals("btc", result.getCoins().get(0).getSymbol());
        assertEquals(600600600634L, result.getCoins().get(0).getMarketCapRank());
        assertEquals("evmos", result.getCoins().get(1).getCoinId());
        assertEquals("Evmos", result.getCoins().get(1).getCoinName());
        assertEquals("evmos", result.getCoins().get(1).getSymbol());
        assertEquals(60600634L, result.getCoins().get(1).getMarketCapRank());
        assertEquals("juno", result.getCoins().get(2).getCoinId());
        assertEquals("Juno", result.getCoins().get(2).getCoinName());
        assertEquals("juno", result.getCoins().get(2).getSymbol());
        assertEquals(36006004L, result.getCoins().get(2).getMarketCapRank());
    }
}
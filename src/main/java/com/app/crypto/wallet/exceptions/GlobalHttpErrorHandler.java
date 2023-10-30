package com.app.crypto.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("User with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleUserAccountExistsException(AccountExistsException exception) {
        return new ResponseEntity<>("Account with the given e-mail address already exists, " +
                "please enter another e-mail address or go to login", HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlerUserAlreadyLoggedException(AlreadyLoggedException exception) {
        return new ResponseEntity<>("You are already logged in", HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleUserIsLogout(UserIsLogoutException exception) {
        return new ResponseEntity<>("denied, you must log in", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleUserTryLogin(LoginToAccountException exception) {
        return new ResponseEntity<>("Wrong, password or wrong e-mail, please try again", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlerWalletNotFoundException(WalletNotFoundException exception) {
        return new ResponseEntity<>("Wallet not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlerUserPermissionsException(UserPermissionsException exception) {
        return new ResponseEntity<>("You don't have enough permissions", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlerCoinNotFoundException(CoinNotFoundException exception) {
        return new ResponseEntity<>("Coin not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlerRoleNotFoundException(RoleNotFoundException exception) {
        return new ResponseEntity<>("Role not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlerJwtNotFoundException(JwtNotFoundException exception) {
        return new ResponseEntity<>("Token not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlerRoleIsAssignedException(RoleIsAssignedException exception) {
        return new ResponseEntity<>("User has already been assigned such a role", HttpStatus.CONFLICT);
    }

}

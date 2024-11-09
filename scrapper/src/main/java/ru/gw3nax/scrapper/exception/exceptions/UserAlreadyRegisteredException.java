package ru.gw3nax.scrapper.exception.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String s) {
        super(s);
    }
}

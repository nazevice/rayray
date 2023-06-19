package de.digitra.uniplaner.exceptions;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Account not found for account iban %s";

    public AccountNotFoundException(String accountIban) {
        super(String.format(MESSAGE, accountIban));
    }
}

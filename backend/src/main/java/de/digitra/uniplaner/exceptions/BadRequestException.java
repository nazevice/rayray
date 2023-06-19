package de.digitra.uniplaner.exceptions;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception  {
    public BadRequestException(String message) {
        super(message);
    }
}




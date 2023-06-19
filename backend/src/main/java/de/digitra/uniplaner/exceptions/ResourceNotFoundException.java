package de.digitra.uniplaner.exceptions;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception  {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}






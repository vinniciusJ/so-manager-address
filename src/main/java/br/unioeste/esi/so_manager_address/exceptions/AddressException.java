package br.unioeste.esi.so_manager_address.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddressException extends RuntimeException {
    private final HttpStatus status;

    public AddressException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

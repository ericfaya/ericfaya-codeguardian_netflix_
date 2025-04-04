package com.nttdata.indhub.exception;

import lombok.Getter;
import com.nttdata.indhub.exception.error.ErrorDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
public class NetflixException extends Exception {

    private static final long serialVersionUID = 6173659257108201607L;

    private final int code;

    private final String message;

    private final Collection<ErrorDto> errorDtoCollection = new ArrayList<>();

    public NetflixException(final int code, final String message, final Collection<ErrorDto> errorDtoCollection) {
        super(message);
        this.code = code;
        this.message = message;
        this.errorDtoCollection.addAll(errorDtoCollection);
    }

    public NetflixException(final int code, final String message, final ErrorDto errorDto) {
        this(code, message, Arrays.asList(errorDto));
    }
}

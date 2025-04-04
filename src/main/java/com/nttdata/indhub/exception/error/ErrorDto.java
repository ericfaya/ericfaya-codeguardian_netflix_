package com.nttdata.indhub.exception.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorDto implements Serializable {

    private static final long serialVersionUID = -8502900941111111784L;

    @NotNull
    private String code;

    private String message;

    public ErrorDto(final String code) {
        this.code = code;
    }
}

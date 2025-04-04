package com.nttdata.indhub.controller.model.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class NetflixResponse<T> {

    private static final long serialVersionUID = -1462076719007656405L;

    private String status;
    private String code;
    private String message;
    private T data;

    public NetflixResponse(final String status, final String code, final String message) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
    }

}

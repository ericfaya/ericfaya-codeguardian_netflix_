package com.nttdata.indhub.controller.model.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class D4iPageRest<T> {

    private static final long serialVersionUID = 1741899248080904930L;

    private T[] content;
    private D4iPaginationInfo page;

}
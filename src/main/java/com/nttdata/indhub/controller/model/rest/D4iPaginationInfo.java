package com.nttdata.indhub.controller.model.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class D4iPaginationInfo {

    private static final long serialVersionUID = 6650681740249017918L;

    private int pageNumber;
    private int pageSize;
    private int totalPages;
}

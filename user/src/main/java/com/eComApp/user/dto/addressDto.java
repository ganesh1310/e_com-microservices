package com.eComApp.user.dto;

import lombok.Data;

@Data
public class addressDto {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}

package com.dannynoam.checkout.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@EqualsAndHashCode
public class Product {

    @NotNull
    private final String code;

    @NotNull
    private final String name;

    @NotNull
    private final double price;
}

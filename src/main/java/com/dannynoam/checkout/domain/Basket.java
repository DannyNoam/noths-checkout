package com.dannynoam.checkout.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Basket {

    private final List<Product> products = new ArrayList<>();
    private double total = 0.0;

    public void addProduct(Product product) {
        products.add(product);
        total += product.getPrice();
    }

    public void deductFromTotal(double deductible) {
        total -= deductible;
    }

    public double getTotal() {
        return Math.round(total * 100.0) / 100.0;
    }
}

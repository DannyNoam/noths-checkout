package com.dannynoam.checkout.service;

import com.dannynoam.checkout.domain.Basket;
import com.dannynoam.checkout.domain.Product;
import com.dannynoam.checkout.domain.promotion.Promotional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Checkout {

    private final Promotional promotional;
    private final Basket basket = new Basket();

    public void scan(Product product) {
        basket.addProduct(product);
    }

    public double total() {
        promotional.apply(basket);

        return basket.getTotal();
    }
}

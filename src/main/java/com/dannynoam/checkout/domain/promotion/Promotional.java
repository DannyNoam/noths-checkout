package com.dannynoam.checkout.domain.promotion;

import com.dannynoam.checkout.domain.Basket;

@FunctionalInterface
public interface Promotional {
    void apply(Basket basket);
}

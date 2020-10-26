package com.dannynoam.checkout.domain.promotion;

import com.dannynoam.checkout.domain.Basket;

public class EmptyPromotion implements Promotional {
    @Override
    public void apply(Basket basket) {}
}

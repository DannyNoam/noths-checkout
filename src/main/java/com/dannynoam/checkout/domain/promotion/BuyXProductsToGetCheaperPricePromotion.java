package com.dannynoam.checkout.domain.promotion;

import com.dannynoam.checkout.domain.Basket;
import com.dannynoam.checkout.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collections;

@AllArgsConstructor
@Builder
public class BuyXProductsToGetCheaperPricePromotion implements Promotional {

    @NotNull
    private final Product product;

    @Min(1)
    private final int minimumProductCount;

    @NotNull
    private final double discountedPrice;

    @NotNull
    private final Promotional nextPromotion;

    @Override
    public void apply(Basket basket) {
        if(meetsMinimumProductCount(basket)) {
            double originalTotalProductPrice = product.getPrice() * getProductCount(basket);
            double newTotalProductPrice = discountedPrice * getProductCount(basket);

            basket.deductFromTotal(originalTotalProductPrice - newTotalProductPrice);
        }

        nextPromotion.apply(basket);
    }

    private boolean meetsMinimumProductCount(Basket basket) {
        return getProductCount(basket) >= minimumProductCount;
    }

    private int getProductCount(Basket basket) {
        return Collections.frequency(basket.getProducts(), product);
    }
}

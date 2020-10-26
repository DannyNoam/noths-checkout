package com.dannynoam.checkout.domain.promotion;

import com.dannynoam.checkout.domain.Basket;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
public class SpendXToGetPercentageOffPromotion implements Promotional {

    @NotNull
    private final double minimumSpend;

    @Min(1) @Max(100)
    private final int percentageOff;

    @NotNull
    private final Promotional nextPromotion;

    @Override
    public void apply(Basket basket) {
        double total = basket.getTotal();

        if(hasMetMinimumSpend(total)) {
            basket.deductFromTotal(total * (percentageOff / 100.00));
        }

        nextPromotion.apply(basket);
    }

    private boolean hasMetMinimumSpend(double price) {
        return price >= minimumSpend;
    }
}

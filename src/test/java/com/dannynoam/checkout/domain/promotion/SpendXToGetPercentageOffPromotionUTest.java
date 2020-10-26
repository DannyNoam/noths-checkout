package com.dannynoam.checkout.domain.promotion;

import com.dannynoam.checkout.domain.Basket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpendXToGetPercentageOffPromotionUTest {

    private static final double MINIMUM_SPEND = 50.00;
    private static final int PERCENTAGE_OFF = 20;

    @Mock
    private Basket basket;

    @Mock
    private Promotional nextPromotion;

    private SpendXToGetPercentageOffPromotion testObj;

    @BeforeEach
    public void setup() {
        testObj = SpendXToGetPercentageOffPromotion.builder()
                .minimumSpend(MINIMUM_SPEND)
                .percentageOff(PERCENTAGE_OFF)
                .nextPromotion(nextPromotion)
                .build();
    }

    @Test
    public void apply_basketHasNOTMetMinimumSpend_noDiscountAppliedAndChainContinued() {
        when(basket.getTotal()).thenReturn(40.00);

        testObj.apply(basket);

        verify(basket, never()).deductFromTotal(anyDouble());
        verifyNoMoreInteractions(basket);
        verify(nextPromotion).apply(basket);
        verifyNoMoreInteractions(nextPromotion);
    }

    @Test
    public void apply_basketHasMetMinimumSpend_discountAppliedAndChainContinued() {
        double customerSpend = 60.00;
        double expectedDiscount = (customerSpend/100) * PERCENTAGE_OFF;
        when(basket.getTotal()).thenReturn(customerSpend);

        testObj.apply(basket);

        verify(basket).deductFromTotal(expectedDiscount);
        verifyNoMoreInteractions(basket);
        verify(nextPromotion).apply(basket);
        verifyNoMoreInteractions(nextPromotion);
    }
}
package com.dannynoam.checkout.service;

import com.dannynoam.checkout.domain.Product;
import com.dannynoam.checkout.domain.promotion.Promotional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CheckoutUTest {

    @Mock
    private Promotional promotional;

    @InjectMocks
    private Checkout testObj;

    @Test
    public void scan_addsProductToUnderlyingBasket() {
        Product product = Product.builder().build();

        testObj.scan(product);

        assertTrue(testObj.getBasket().getProducts().contains(product));
    }

    @Test
    public void total_appliesPromotionalDiscountsAndReturnsTotalFromUnderlyingBasket() {
        double actual = testObj.total();

        verify(promotional).apply(testObj.getBasket());
        assertEquals(testObj.getBasket().getTotal(), actual);
    }
}
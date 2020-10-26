package com.dannynoam.checkout.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasketUTest {

    private final double PRICE = 10.00;
    private final Product PRODUCT = Product.builder()
            .price(PRICE)
            .build();

    private Basket testObj;

    @BeforeEach
    public void setup() {
        testObj = new Basket();
    }

    @Test
    public void addProduct_addsProductToUnderlyingListOfProductsAndIncreasesTotal() {
        testObj.addProduct(PRODUCT);

        assertTrue(testObj.getProducts().contains(PRODUCT));
        assertEquals(PRICE, testObj.getTotal());
    }

    @Test
    public void deductFromTotal_deductsFromTotalCorrectly() {
        double deductible = 3.00;
        double expectedTotal = PRICE - deductible;
        testObj.addProduct(PRODUCT);

        testObj.deductFromTotal(deductible);

        assertEquals(expectedTotal, testObj.getTotal());
    }

    @Test
    public void getTotal_roundsTotalAndReturnsCorrectly() {
        double unroundedPrice = 10.755;
        double expectedRoundedPrice = 10.76;
        testObj.addProduct(
                Product.builder()
                    .price(unroundedPrice)
                    .build()
        );

        double actual = testObj.getTotal();

        assertEquals(expectedRoundedPrice, actual);
    }
}
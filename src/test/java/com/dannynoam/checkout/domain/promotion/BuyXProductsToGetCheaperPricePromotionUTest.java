package com.dannynoam.checkout.domain.promotion;

import com.dannynoam.checkout.domain.Basket;
import com.dannynoam.checkout.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuyXProductsToGetCheaperPricePromotionUTest {

    private static final int MINIMUM_PRODUCT_COUNT = 2;
    private static final double NORMAL_PRICE = 10.00;
    private static final double DISCOUNTED_PRICE = 7.50;

    private Product product = Product.builder()
            .price(NORMAL_PRICE)
            .build();

    @Mock
    private Basket basket;

    @Mock
    private Promotional nextPromotion;

    private BuyXProductsToGetCheaperPricePromotion testObj;

    @BeforeEach
    public void setup() {
        testObj = BuyXProductsToGetCheaperPricePromotion.builder()
                .product(product)
                .minimumProductCount(MINIMUM_PRODUCT_COUNT)
                .discountedPrice(DISCOUNTED_PRICE)
                .nextPromotion(nextPromotion)
                .build();
    }

    @Test
    public void apply_basketDoesNOTMeetMinimumProductCount_noDiscountAppliedAndChainContinued() {
        basket = addProductsToBasket(product, 1);

        testObj.apply(basket);

        verify(basket, never()).deductFromTotal(anyDouble());
        verifyNoMoreInteractions(basket);
        verify(nextPromotion).apply(basket);
        verifyNoMoreInteractions(nextPromotion);
    }

    @Test
    public void apply_basketMeetsMinimumProductCount_discountAppliedAndChainContinued() {
        double expectedDiscount = (NORMAL_PRICE - DISCOUNTED_PRICE) * MINIMUM_PRODUCT_COUNT;
        basket = addProductsToBasket(product, 2);

        testObj.apply(basket);

        verify(basket).deductFromTotal(expectedDiscount);
        verifyNoMoreInteractions(basket);
        verify(nextPromotion).apply(basket);
        verifyNoMoreInteractions(nextPromotion);
    }

    private Basket addProductsToBasket(Product product, int count) {
        List<Product> products = createProductsList(product, count);

        when(basket.getProducts()).thenReturn(products);

        return basket;
    }

    private List<Product> createProductsList(Product product, int count) {
        List<Product> products = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            products.add(product);
        }

        return products;
    }
}
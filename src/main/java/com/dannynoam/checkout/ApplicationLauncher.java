package com.dannynoam.checkout;

import com.dannynoam.checkout.domain.Product;
import com.dannynoam.checkout.domain.promotion.BuyXProductsToGetCheaperPricePromotion;
import com.dannynoam.checkout.domain.promotion.EmptyPromotion;
import com.dannynoam.checkout.domain.promotion.Promotional;
import com.dannynoam.checkout.domain.promotion.SpendXToGetPercentageOffPromotion;
import com.dannynoam.checkout.service.Checkout;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationLauncher {

    private static final Product PRODUCT_001 = Product.builder()
            .code("001")
            .name("Travel Card Holder")
            .price(9.25)
            .build();
    private static final Product PRODUCT_002 = Product.builder()
            .code("002")
            .name("Personalised cufflinks")
            .price(45.00)
            .build();
    private static final Product PRODUCT_003 = Product.builder()
            .code("003")
            .name("Kids T-shirt")
            .price(19.95)
            .build();

    public static void main(String[] args) {
        Promotional promotional = setupPromotionalChain();

        Checkout checkout = new Checkout(promotional);
        checkout.scan(PRODUCT_001);
        checkout.scan(PRODUCT_002);
        checkout.scan(PRODUCT_003);
        log.info("Total: £{}", checkout.total());

        Checkout checkout2 = new Checkout(promotional);
        checkout2.scan(PRODUCT_001);
        checkout2.scan(PRODUCT_003);
        checkout2.scan(PRODUCT_001);
        log.info("Total: £{}", checkout2.total());

        Checkout checkout3 = new Checkout(promotional);
        checkout3.scan(PRODUCT_001);
        checkout3.scan(PRODUCT_002);
        checkout3.scan(PRODUCT_001);
        checkout3.scan(PRODUCT_003);
        log.info("Total: £{}", checkout3.total());
    }

    private static Promotional setupPromotionalChain() {
        Promotional emptyPromotion = new EmptyPromotion();
        Promotional spendXPromotion = new SpendXToGetPercentageOffPromotion(60.00, 10, emptyPromotion);

        return new BuyXProductsToGetCheaperPricePromotion(PRODUCT_001, 2, 8.50, spendXPromotion);
    }
}

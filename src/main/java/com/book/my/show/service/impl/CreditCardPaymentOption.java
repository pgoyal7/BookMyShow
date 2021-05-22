package com.book.my.show.service.impl;

import com.book.my.show.factory.PaymentFactory;
import com.book.my.show.service.PaymentOption;
import com.book.my.show.type.PaymentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreditCardPaymentOption implements PaymentOption {
    static {
        PaymentFactory.PAYMENT_TYPE_CLASS_MAP.put(PaymentType.CREDIT_CARD, CreditCardPaymentOption.class);
    }

    @Override
    public boolean processPayment() throws InterruptedException {
        log.info("Payment initiated via Credit Card completed successfully");
        Thread.sleep(1000);
        log.info("Payment via Wallet completed successfully");
        return true;
    }
}

package com.book.my.show.service.impl;

import com.book.my.show.factory.PaymentFactory;
import com.book.my.show.service.PaymentOption;
import com.book.my.show.type.PaymentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WalletPaymentOption implements PaymentOption {
    static {
        PaymentFactory.PAYMENT_TYPE_CLASS_MAP.put(PaymentType.WALLET, WalletPaymentOption.class);
    }

    @Override
    public boolean processPayment() throws InterruptedException {
        log.info("Payment initiated via Wallet completed successfully");
        Thread.sleep(1000);
        log.info("Payment via Wallet completed successfully");
        return true;
    }
}

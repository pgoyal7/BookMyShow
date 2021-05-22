package com.book.my.show.factory;

import com.book.my.show.service.PaymentOption;
import com.book.my.show.type.PaymentType;

import java.util.HashMap;
import java.util.Map;

public abstract class PaymentFactory {
    public final static Map<PaymentType, Class<? extends PaymentOption>> PAYMENT_TYPE_CLASS_MAP = new HashMap<>();
}

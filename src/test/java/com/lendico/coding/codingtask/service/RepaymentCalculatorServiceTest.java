package com.lendico.coding.codingtask.service;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class RepaymentCalculatorServiceTest {
    
    private RepaymentCalculatorService repaymentCalculatorService;
    
    @BeforeEach
    public void init() {
        repaymentCalculatorService = new RepaymentCalculatorServiceImpl();
    }
    
    @Test
    public void test_calculateRemainingOutstandingPrincipal() {
        CurrencyUnit inputCurrencyUnit = Monetary.getCurrency("USD");
        
        FastMoney money = repaymentCalculatorService
                .calculateRemainingOutstandingPrincipal(FastMoney.of(5000, inputCurrencyUnit), FastMoney.of(500, inputCurrencyUnit), inputCurrencyUnit);
        
        Assertions.assertEquals(money.getNumber().doubleValueExact(), 4500);
    }
    
    @Test
    public void test_calculatePrincipal() {
        CurrencyUnit inputCurrencyUnit = Monetary.getCurrency("USD");
        
        FastMoney money = repaymentCalculatorService
                .calculatePrincipal(FastMoney.of(300, inputCurrencyUnit), FastMoney.of(500, inputCurrencyUnit));
        
        Assertions.assertEquals(money.getNumber().doubleValueExact(), 200);
        
        
    }
    
    @Test
    public void test_calculateInterest() {
        
        CurrencyUnit inputCurrencyUnit = Monetary.getCurrency("USD");
        
        FastMoney money = repaymentCalculatorService
                .calculateInterest(12L, FastMoney.of(5000, inputCurrencyUnit));
        
        Assertions.assertEquals(money.getNumber().doubleValueExact(), 50.0);
        
        
    }
}

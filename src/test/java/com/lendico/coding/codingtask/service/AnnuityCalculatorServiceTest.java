package com.lendico.coding.codingtask.service;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;

public class AnnuityCalculatorServiceTest {
    
    private AnnuityCalculatorService annuityCalculatorService;
    
    @BeforeEach
    public void init() {
        annuityCalculatorService = new AnnuityCalculatorServiceImpl();
    }
    
    @Test
    public void caluclateAnnuity() {
        CurrencyUnit usd = Monetary.getCurrency("USD");
        
        BigDecimal loanAmount = new BigDecimal(5000);
        int numberOfPeriods = 24;
        double interestPerPeriod = 5.0;
        
        double annuityAmount = annuityCalculatorService.calculate(FastMoney.of(loanAmount, usd), interestPerPeriod, numberOfPeriods, usd).with(Monetary.getDefaultRounding()).getNumber().doubleValue();
        
        Assertions.assertEquals(219.36, annuityAmount);
    }
}

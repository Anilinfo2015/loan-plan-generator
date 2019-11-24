package com.lendico.coding.codingtask.service;

import org.javamoney.moneta.FastMoney;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

/**
 * @author Anil Kurmi
 */
@Service
public class AnnuityCalculatorServiceImpl implements AnnuityCalculatorService {
    
    
    /**
     * @param loanAmount
     * @param nominalRate
     * @param duration
     * @return
     */
    @Override
    public FastMoney calculate(FastMoney loanAmount, double nominalRate, int duration, CurrencyUnit currencyUnit) {
        
        nominalRate = nominalRate / (12 * 100);
        // Calculates the annuity based on the formula
        FastMoney annuity = loanAmount.multiply(nominalRate)
                .divide((1 - Math.pow(1 + nominalRate, -duration)));
        
        FastMoney outputValue = FastMoney.of(annuity.getNumber(), currencyUnit).with(Monetary.getDefaultRounding());
        
        return outputValue;
    }
}
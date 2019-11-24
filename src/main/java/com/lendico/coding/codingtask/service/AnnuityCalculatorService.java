package com.lendico.coding.codingtask.service;

import org.javamoney.moneta.FastMoney;

import javax.money.CurrencyUnit;

/**
 * @author Anil Kurmi
 */
public interface AnnuityCalculatorService {
    
    /**
     * @param loanAmount  loan amount for client
     * @param nominalRate the rate of interest before adjustment for inflation
     * @param duration    duration of loan
     * @return
     */
    FastMoney calculate(FastMoney loanAmount, double nominalRate, int duration, CurrencyUnit currencyUnit);
}

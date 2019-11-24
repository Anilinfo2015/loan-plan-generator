package com.lendico.coding.codingtask.service;

import org.javamoney.moneta.FastMoney;

import javax.money.CurrencyUnit;

/**
 * @author Anil Kurmi
 */
public interface RepaymentCalculatorService {
    
    int DAYS_IN_MONTH = 30;
    int DAYS_IN_YEAR = 360;
    
    /**
     * Given the values of the initial outstanding principal and the principal,
     * calculates the remaining outstanding principal.
     *
     * @param initialOutstandingPrincipal
     * @param principal
     * @return The  remaining outstanding principal value
     */
    FastMoney calculateRemainingOutstandingPrincipal(FastMoney initialOutstandingPrincipal, FastMoney principal, CurrencyUnit currencyUnit);
    
    /**
     * Given an interest calculated value and an annuity, calculates the principal
     * value.
     *
     * @param interest
     * @param annuity
     * @return The  principal value
     */
    FastMoney calculatePrincipal(FastMoney interest, FastMoney annuity);
    
    /**
     * Given the nominal interest rate and the initial outstanding principal value,
     * calculates the interest value.
     *
     * @param nominalRate
     * @param initialOutstandingPrincipal
     * @return The  interest value
     */
    FastMoney calculateInterest(double nominalRate, FastMoney initialOutstandingPrincipal);
    
}

package com.lendico.coding.codingtask.service;

import org.javamoney.moneta.FastMoney;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;

/**
 * @author Anil Kurmi
 */
@Service
public class RepaymentCalculatorServiceImpl implements RepaymentCalculatorService {
    
    @Override
    public FastMoney calculateRemainingOutstandingPrincipal(FastMoney initialOutstandingPrincipal,
                                                            FastMoney principal, CurrencyUnit currencyUnit) {
        
        // Calculates outstanding principal
        FastMoney remainingOutstandingPrincipal;
        remainingOutstandingPrincipal = initialOutstandingPrincipal.subtract(principal);
        /* Because the value cannot be negative, it is set to zero */
        if (remainingOutstandingPrincipal.compareTo(FastMoney.zero(currencyUnit)) < 0) {
            remainingOutstandingPrincipal = FastMoney.zero(currencyUnit);
        }
        return remainingOutstandingPrincipal;
    }
    
    @Override
    public FastMoney calculatePrincipal(FastMoney interest, FastMoney annuity) {
        return annuity.subtract(interest);
    }
    
    @Override
    public FastMoney calculateInterest(double nominalRate, FastMoney initialOutstandingPrincipal) {
        // Interest calculation
        FastMoney interest = initialOutstandingPrincipal.multiply(DAYS_IN_MONTH).multiply(nominalRate).divide(DAYS_IN_YEAR);
        // Divides by 100 and rounds it with the rounding method
        interest = interest.divide(100);
        return interest;
    }
}

package com.lendico.coding.codingtask.service;

import com.lendico.coding.codingtask.model.Repayment;
import com.lendico.coding.codingtask.model.RepaymentPlan;
import org.javamoney.moneta.FastMoney;

import javax.money.CurrencyUnit;
import java.time.LocalDateTime;

/**
 * @author Anil Kurmi
 */
public interface RepaymentService {
    /**
     * @param loanAmount
     * @param nominalRate
     * @param duration
     * @param startDate
     * @return The  repayments plan for the given input values
     */
    RepaymentPlan generatePlan(FastMoney loanAmount, double nominalRate, int duration, LocalDateTime startDate, CurrencyUnit currencyUnit);
    
    /**
     * Calculates the repayments for a given month after the start date.
     *
     * @param loanAmount
     * @param nominalRate
     * @param duration
     * @param startDate
     * @param initialOutstandingPrincipal
     * @param monthsAfterStart
     * @return The resulting repayment for the given input values
     */
    Repayment generateRepayment(FastMoney loanAmount, double nominalRate, int duration, LocalDateTime startDate,
                                FastMoney initialOutstandingPrincipal, int monthsAfterStart, CurrencyUnit currencyUnit);
}

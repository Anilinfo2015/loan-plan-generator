package com.lendico.coding.codingtask.service;

import com.lendico.coding.codingtask.model.Repayment;
import com.lendico.coding.codingtask.model.RepaymentPlan;
import org.javamoney.moneta.FastMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Anil Kurmi
 */
@Service
public class RepaymentServiceImpl implements RepaymentService {
    @Autowired
    private AnnuityCalculatorService annuityCalculator;
    
    @Autowired
    private RepaymentCalculatorService repaymentCalculator;
    
    /**
     * @param loanAmount
     * @param nominalRate
     * @param duration
     * @param startDate
     * @return
     */
    @Override
    public RepaymentPlan generatePlan(FastMoney loanAmount, double nominalRate, int duration,
                                      LocalDateTime startDate, CurrencyUnit currencyUnit) {
        List<Repayment> repayments = new ArrayList<>();
        FastMoney initialOutstandingPrincipal = loanAmount;
        
        for (int i = 0; i < duration; i++) {
            Repayment repayment = generateRepayment(loanAmount, nominalRate, duration, startDate,
                    initialOutstandingPrincipal, i, currencyUnit);
            /* initial outstanding principal for the coming month. */
            initialOutstandingPrincipal = FastMoney.of(repayment.getRemainingOutstandingPrincipal(), currencyUnit);
            repayments.add(repayment);
        }
        
        RepaymentPlan plan = new RepaymentPlan();
        plan.setRepayments(repayments);
        return plan;
    }
    
    /**
     * @param loanAmount
     * @param nominalRate
     * @param duration
     * @param startDate
     * @param initialOutstandingPrincipal
     * @param monthsAfterStart
     * @return
     */
    @Override
    public Repayment generateRepayment(FastMoney loanAmount, double nominalRate, int duration, LocalDateTime startDate,
                                       FastMoney initialOutstandingPrincipal, int monthsAfterStart, CurrencyUnit currencyUnit) {
        
        Repayment repayment = new Repayment();
        
        // Date
        LocalDateTime date = startDate.plusMonths(monthsAfterStart);
        repayment.setDate(date);
        
        // Interest
        FastMoney interest = repaymentCalculator.calculateInterest(nominalRate, initialOutstandingPrincipal);
        repayment.setInterest(interest.with(Monetary.getDefaultRounding()).getNumber().numberValue(BigDecimal.class));
        
        // Annuity
        FastMoney annuity = annuityCalculator.calculate(loanAmount, nominalRate, duration, currencyUnit);
        repayment.setBorrowerPaymentAmount(annuity.with(Monetary.getDefaultRounding()).getNumber().numberValue(BigDecimal.class));
        
        // Principal
        FastMoney principal = repaymentCalculator.calculatePrincipal(interest, annuity);
        repayment.setPrincipal(principal.with(Monetary.getDefaultRounding()).getNumber().numberValue(BigDecimal.class));
        
        // Initial outstanding principal
        repayment.setInitialOutstandingPrincipal(initialOutstandingPrincipal.getNumber().numberValue(BigDecimal.class));
        
        // Remaining outstanding principal
        FastMoney remainingOutstandingPrincipal = repaymentCalculator
                .calculateRemainingOutstandingPrincipal(initialOutstandingPrincipal, principal, currencyUnit);
        repayment.setRemainingOutstandingPrincipal(remainingOutstandingPrincipal.getNumber().numberValue(BigDecimal.class).setScale(2, RoundingMode.HALF_EVEN));
        return repayment;
    }
}

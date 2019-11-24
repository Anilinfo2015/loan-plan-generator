package com.lendico.coding.codingtask.service;

import com.lendico.coding.codingtask.model.RepaymentPlanRequest;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class RepaymentServiceTest {
    
    @InjectMocks
    RepaymentServiceImpl repaymentService;
    
    @Spy
    AnnuityCalculatorServiceImpl annuityCalculatorService;
    
    @Spy
    RepaymentCalculatorServiceImpl repaymentCalculatorService;
    
    
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void test() {
        CurrencyUnit usd = Monetary.getCurrency("USD");
        RepaymentPlanRequest repaymentPlanRequest = new RepaymentPlanRequest();
        repaymentPlanRequest.setDuration(1000);
        repaymentPlanRequest.setLoanAmount(new BigDecimal(456568229));
        repaymentPlanRequest.setNominalRate(12D);
        repaymentPlanRequest.setStartDate(Calendar.getInstance().getTime());
        
        Date startDate = repaymentPlanRequest.getStartDate();
        Instant instant = startDate.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localStartDate = LocalDateTime.ofInstant(instant, zone);
        
        repaymentService
                .generatePlan(
                        FastMoney.of(repaymentPlanRequest.getLoanAmount(), usd),
                        repaymentPlanRequest.getNominalRate(),
                        repaymentPlanRequest.getDuration(),
                        localStartDate, usd
                )
                .getRepayments()
                .stream().forEach(System.out::println);
    }
    
}

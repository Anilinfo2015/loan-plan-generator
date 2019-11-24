package com.lendico.coding.codingtask.api;

import com.lendico.coding.codingtask.model.Repayment;
import com.lendico.coding.codingtask.model.RepaymentPlan;
import com.lendico.coding.codingtask.model.RepaymentPlanRequest;
import com.lendico.coding.codingtask.service.RepaymentService;
import com.lendico.coding.codingtask.utility.RestPathURI;
import org.javamoney.moneta.FastMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


/**
 * @author Anil Kurmi
 */
@RestController
@RequestMapping(RestPathURI.REPAYMENT_ROOT)
public class RepaymentPlanAPI {
    private static final Logger logger = LoggerFactory.getLogger(RepaymentPlanAPI.class);
    
    @Autowired
    private RepaymentService service;
    
    @Value("${repayment.input.currency}")
    private String inputCurrency;
    
    @Value("${repayment.output.currency}")
    private String outputCurrency;
    
    @GetMapping(path = "test")
    public String test() {
        return "running";
    }
    
    @PostMapping(path = RestPathURI.GENERATE_PLAN, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    List<Repayment> generatePlan(@Validated @RequestBody RepaymentPlanRequest request) {
        logger.info(request.toString());
        /* Get request parameters. */
        BigDecimal loanAmount = request.getLoanAmount();
        Double nominalRate = request.getNominalRate();
        Integer duration = request.getDuration();
        
        Date startDate = request.getStartDate();
        Instant instant = startDate.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localStartDate = LocalDateTime.ofInstant(instant, zone);
        
        
        CurrencyUnit inputCurrencyUnit = Monetary.getCurrency(inputCurrency);
        CurrencyUnit outputCurrencyUnit = Monetary.getCurrency(outputCurrency);
        
        /* execution of the service. */
        RepaymentPlan plan = service.generatePlan(FastMoney.of(loanAmount, inputCurrencyUnit), nominalRate, duration, localStartDate, outputCurrencyUnit);
        return plan.getRepayments();
    }
}

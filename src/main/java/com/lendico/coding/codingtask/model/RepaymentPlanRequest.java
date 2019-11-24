package com.lendico.coding.codingtask.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Anil Kurmi
 */
@Getter
@Setter
@ToString
public class RepaymentPlanRequest {
    @NotNull
    @Positive
    private BigDecimal loanAmount;
    
    @NotNull
    @Positive
    private Double nominalRate;
    
    @NotNull
    @Min(1)
    @Positive
    private Integer duration;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date startDate;
}

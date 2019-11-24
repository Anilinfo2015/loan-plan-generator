package com.lendico.coding.codingtask.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Anil Kurmi
 */
@Getter
@Setter
@ToString
public class RepaymentPlan {
    private List<Repayment> repayments;
}
package com.lendico.coding.codingtask.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendico.coding.codingtask.model.RepaymentPlanRequest;
import com.lendico.coding.codingtask.service.*;
import com.lendico.coding.codingtask.utility.RestPathURI;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Calendar;

@WebMvcTest(RepaymentPlanAPI.class)
@ExtendWith(RestDocumentationExtension.class)
public class RepaymentPlanAPITest {
    
    @Autowired
    private MockMvc mockMvc;
    
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void test_generatePlan() throws Exception {
        RepaymentPlanRequest repaymentPlanRequest = new RepaymentPlanRequest();
        repaymentPlanRequest.setDuration(24);
        repaymentPlanRequest.setLoanAmount(new BigDecimal(5000));
        repaymentPlanRequest.setNominalRate(5.0);
        repaymentPlanRequest.setStartDate(Calendar.getInstance().getTime());
        
        mockMvc.perform(MockMvcRequestBuilders.post(RestPathURI.REPAYMENT_ROOT + RestPathURI.GENERATE_PLAN)
                .content(asJsonString(repaymentPlanRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].initialOutstandingPrincipal", CoreMatchers.is("5000.00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].borrowerPaymentAmount", CoreMatchers.is("219.36")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].interest", CoreMatchers.is("20.83")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].principal", CoreMatchers.is("198.53")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].remainingOutstandingPrincipal", CoreMatchers.is("4801.47")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].initialOutstandingPrincipal", CoreMatchers.is("4801.47")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].borrowerPaymentAmount", CoreMatchers.is("219.36")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].interest", CoreMatchers.is("20.01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].principal", CoreMatchers.is("199.35")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].remainingOutstandingPrincipal", CoreMatchers.is("4602.12")))
        
        ;
    }
    
    
    @Test
    public void test_fail_case_generatePlan() {
        RepaymentPlanRequest repaymentPlanRequest = new RepaymentPlanRequest();
        repaymentPlanRequest.setDuration(24);
        repaymentPlanRequest.setLoanAmount(new BigDecimal(5000));
        repaymentPlanRequest.setNominalRate(5.0);
        repaymentPlanRequest.setStartDate(Calendar.getInstance().getTime());
        
        Assertions.assertThrows(AssertionError.class, () -> {
            
            mockMvc.perform(MockMvcRequestBuilders.post(RestPathURI.REPAYMENT_ROOT + RestPathURI.GENERATE_PLAN)
                    .content(asJsonString(repaymentPlanRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].initialOutstandingPrincipal", CoreMatchers.is(6000)));
        });
        
        
    }
    
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public RepaymentService repaymentService() {
            return new RepaymentServiceImpl();
        }
        
        @Bean
        public AnnuityCalculatorService annuityCalculatorService() {
            return new AnnuityCalculatorServiceImpl();
        }
        
        @Bean
        public RepaymentCalculatorService repaymentCalculatorService() {
            return new RepaymentCalculatorServiceImpl();
        }
    }
}

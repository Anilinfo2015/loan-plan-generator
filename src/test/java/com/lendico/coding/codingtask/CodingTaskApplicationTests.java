package com.lendico.coding.codingtask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendico.coding.codingtask.model.RepaymentPlanRequest;
import com.lendico.coding.codingtask.utility.RestPathURI;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Calendar;

@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class CodingTaskApplicationTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext context;
    
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
        }
        
        return null;
    }
    
    @BeforeEach
    void setUp2(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(MockMvcRestDocumentation.document(RestPathURI.GENERATE_PLAN,
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }
    
    @Test
    void document_repayment_plan_method() throws Exception {
        RepaymentPlanRequest repaymentPlanRequest = new RepaymentPlanRequest();
        repaymentPlanRequest.setDuration(12);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].borrowerPaymentAmount", CoreMatchers.is("428.04")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].interest", CoreMatchers.is("20.83")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].principal", CoreMatchers.is("407.21")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].remainingOutstandingPrincipal", CoreMatchers.is("4592.79"))
                );
        
    }
}

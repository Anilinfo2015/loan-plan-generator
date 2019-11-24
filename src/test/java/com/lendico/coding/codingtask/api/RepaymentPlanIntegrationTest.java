package com.lendico.coding.codingtask.api;

import com.lendico.coding.codingtask.CodingTaskApplication;
import com.lendico.coding.codingtask.model.RepaymentPlanRequest;
import com.lendico.coding.codingtask.utility.RestPathURI;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


@SpringBootTest(classes = CodingTaskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RepaymentPlanIntegrationTest {
    
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;
    
    @Test
    public void testRetrieveStudentCourse() {
        RepaymentPlanRequest repaymentPlanRequest = new RepaymentPlanRequest();
        repaymentPlanRequest.setDuration(24);
        repaymentPlanRequest.setLoanAmount(new BigDecimal(5000));
        repaymentPlanRequest.setNominalRate(5.0);
        repaymentPlanRequest.setStartDate(Calendar.getInstance().getTime());
        
        HttpEntity<RepaymentPlanRequest> entity = new HttpEntity<RepaymentPlanRequest>(repaymentPlanRequest, headers);
        
        ResponseEntity<List> response = restTemplate.postForEntity(createURLWithPort(RestPathURI.REPAYMENT_ROOT + "/" + RestPathURI.GENERATE_PLAN),
                entity, List.class);
        
        response.getBody().stream().forEach(System.out::println);
        
        
    }
    
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

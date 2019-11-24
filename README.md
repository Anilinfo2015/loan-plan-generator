# Annuity Replayment Schedule
Loan Plan Generator for Repayment
This project exposes a REST api to caluclate repayment scheduled , annuity amount , interest and principal amount , outstanding prinipal amount for annutiy loan. This project is developed using Spring Boot .

- By default application runs on port 8080 . Kindly make sure the port is available

- Service is available in http://localhost:8080/api/v1/repayment/generate-plan

# Technology Stack
- Spring Boot
- Java 8
- Spring Rest Docs
- Java Money
- JUnit, Spring Test, MockitoJ

# Build / Compile application

``` mvc clean package ```

# Execute the application
``` java -jar -Dspring.profiles.active=prod coding-task-0.0.1-SNAPSHOT.jar ```

Default Currency is USD, it can be configured from property file application-prod.properties
```
repayment.input.currency=USD
repayment.output.currency=USD
```

# API Documentations


[API Documentation Preview](http://htmlpreview.github.io/?https://github.com/Anilinfo2015/loan-plan-generator/blob/master/src/main/resources/api-guide.html)

![Doc1](https://github.com/Anilinfo2015/loan-plan-generator/blob/master/src/main/resources/doc1.PNG "Logo Title Text 1")
![Doc1](https://github.com/Anilinfo2015/loan-plan-generator/blob/master/src/main/resources/doc2.PNG "Logo Title Text 1")
![Doc1](https://github.com/Anilinfo2015/loan-plan-generator/blob/master/src/main/resources/doc3.PNG "Logo Title Text 1")


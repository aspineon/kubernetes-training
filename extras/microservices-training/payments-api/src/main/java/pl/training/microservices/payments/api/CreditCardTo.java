package pl.training.microservices.payments.api;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreditCardTo {

    private String number;
    private String cvv;
    private LocalDate expirationDate;

}

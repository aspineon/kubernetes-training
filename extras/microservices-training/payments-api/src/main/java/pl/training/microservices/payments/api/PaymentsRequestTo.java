package pl.training.microservices.payments.api;

import lombok.Data;

@Data
public class PaymentsRequestTo {

    private Long amount;
    private CreditCardTo creditCard;

}

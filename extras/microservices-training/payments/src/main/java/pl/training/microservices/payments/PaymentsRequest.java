package pl.training.microservices.payments;

import lombok.Data;

@Data
public class PaymentsRequest {

    private Long amount;
    private CreditCard creditCard;

}

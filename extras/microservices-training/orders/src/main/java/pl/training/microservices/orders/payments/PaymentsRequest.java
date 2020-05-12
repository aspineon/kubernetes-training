package pl.training.microservices.orders.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentsRequest {

    private Long amount;
    private CreditCard creditCard;

}

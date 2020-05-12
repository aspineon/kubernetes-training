package pl.training.microservices.orders.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCard {

    private String number;
    private String cvv;
    private LocalDate expiration;

}

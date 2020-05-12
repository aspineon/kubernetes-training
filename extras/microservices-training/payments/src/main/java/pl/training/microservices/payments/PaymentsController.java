package pl.training.microservices.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.training.microservices.payments.api.PaymentTo;
import pl.training.microservices.payments.api.PaymentsRequestTo;
import pl.training.microservices.payments.api.Payments;

@RestController
@RequiredArgsConstructor
public class PaymentsController implements Payments {

    private final PaymentsMapper paymentsMapper;
    private final PaymentsService paymentsService;

    public ResponseEntity<PaymentTo> addPaymentRequest(PaymentsRequestTo paymentsRequestTo) {
        PaymentsRequest paymentsRequest = paymentsMapper.toPaymentRequest(paymentsRequestTo);
        Payment payment = paymentsService.pay(paymentsRequest);
        PaymentTo paymentTo = paymentsMapper.toPaymentTo(payment);
        return ResponseEntity.accepted().body(paymentTo);
    }

}

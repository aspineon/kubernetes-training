package pl.training.microservices.orders;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.training.microservices.orders.payments.*;
import pl.training.microservices.payments.api.PaymentTo;
import pl.training.microservices.payments.api.PaymentsRequestTo;
import pl.training.microservices.payments.api.Payments;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

@Service
@Log
@RequiredArgsConstructor
public class OrdersService {

    private static final String RESOURCE_URI = "http://payments-service/payments";

    private final PaymentsMapper paymentsMapper;
    private final Payments payments;
    private final OrdersFeeConfig feeConfig;
    //private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "placeOrderFallback", ignoreExceptions = RuntimeException.class)
    public Long placeOrder(Order order) {
        PaymentsRequestTo paymentsRequestTo = createPaymentRequest();
        return pay(paymentsRequestTo);
    }

    public Long placeOrderFallback(Order order) {
        log.info("Adding to queue...");
        return -1L;
    }

    private PaymentsRequestTo createPaymentRequest() {
        CreditCard creditCard = new CreditCard("1234567789", "645", LocalDate.now());
        PaymentsRequest paymentsRequest = new PaymentsRequest(1000L + feeConfig.getFee(), creditCard);
        return paymentsMapper.toPaymentRequestTo(paymentsRequest);
    }

    private Long pay(PaymentsRequestTo paymentsRequestTo) {
        PaymentTo paymentTo = payments.addPaymentRequest(paymentsRequestTo).getBody();
        return paymentsMapper.toPayment(paymentTo).getId();

       /* try {
            URI uri = new URI(RESOURCE_URI);
            ResponseEntity<PaymentTo> paymentTo = restTemplate.postForEntity(uri, paymentsRequestTo, PaymentTo.class);
            return paymentsMapper.toPayment(paymentTo.getBody());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException();
        }*/
    }

}

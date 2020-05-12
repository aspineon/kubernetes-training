package pl.training.microservices.payments.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient("http://payments-service")
@FeignClient("payments-service")
public interface Payments {

    @PostMapping("payments")
    ResponseEntity<PaymentTo> addPaymentRequest(@RequestBody PaymentsRequestTo paymentsRequestTo);

}

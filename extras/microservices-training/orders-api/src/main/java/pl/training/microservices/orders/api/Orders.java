package pl.training.microservices.orders.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface Orders {

    @PostMapping("orders")
    ResponseEntity<Void> placeOrder(@RequestBody @Valid OrderTo orderTo, BindingResult bindingResult);

}

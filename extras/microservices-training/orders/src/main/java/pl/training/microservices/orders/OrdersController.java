package pl.training.microservices.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.training.microservices.orders.api.OrderTo;
import pl.training.microservices.orders.api.Orders;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class OrdersController implements Orders {

    private final OrdersMapper ordersMapper;
    private final OrdersService ordersService;
    private final UriBuilder uriBuilder = new UriBuilder();

    public ResponseEntity<Void> placeOrder(OrderTo orderTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Order order = ordersMapper.toOrder(orderTo);
        Long orderId = ordersService.placeOrder(order);
        URI locationUri = uriBuilder.requestUriWithAppendedId(orderId);
        return ResponseEntity.created(locationUri).build();
    }

    /*@ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Void> onProductNotFound(ProductNotFoundException exception) {
        return ResponseEntity.badRequest().build();
    }*/

}

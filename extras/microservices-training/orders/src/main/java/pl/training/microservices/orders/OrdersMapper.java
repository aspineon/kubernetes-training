package pl.training.microservices.orders;

import org.mapstruct.Mapper;
import pl.training.microservices.orders.api.OrderTo;
import pl.training.microservices.orders.api.ProductTo;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    Order toOrder(OrderTo orderTo);

    Product toProduct(ProductTo productTo);

}

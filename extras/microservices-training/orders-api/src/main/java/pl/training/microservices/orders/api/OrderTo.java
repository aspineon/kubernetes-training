package pl.training.microservices.orders.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderTo {

    @NotEmpty
    private List<ProductTo> products;

}

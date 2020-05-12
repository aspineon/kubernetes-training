package pl.training.microservices.products.api;

import lombok.Data;

@Data
public class ProductTo {

    private String name;
    private String description;
    private long price;

}

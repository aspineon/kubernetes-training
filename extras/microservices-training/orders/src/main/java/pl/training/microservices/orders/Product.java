package pl.training.microservices.orders;

import lombok.Data;

@Data
public class Product {

    private Long id;
    private String name;
    private String description;
    private long price;

}

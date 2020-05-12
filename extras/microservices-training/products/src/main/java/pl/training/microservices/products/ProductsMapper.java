package pl.training.microservices.products;

import org.mapstruct.Mapper;
import pl.training.microservices.products.api.ProductTo;
import pl.training.microservices.products.api.ResultPageTo;

@Mapper(componentModel = "spring")
public interface ProductsMapper {

    Product toProduct(ProductTo productTo);

    ProductTo toProductTo(Product product);

    ResultPageTo<ProductTo> toResultPageTo(ResultPage<Product> resultPage);

}

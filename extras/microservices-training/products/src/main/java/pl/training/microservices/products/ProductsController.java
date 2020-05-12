package pl.training.microservices.products;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.training.microservices.products.api.ProductTo;
import pl.training.microservices.products.api.Products;
import pl.training.microservices.products.api.ResultPageTo;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProductsController implements Products {

    private final ProductsMapper productsMapper;
    private final ProductsService productsService;
    private final UriBuilder uriBuilder = new UriBuilder();

    @Override
    public ResponseEntity<ProductTo> add(ProductTo productTo) {
        Product product = productsMapper.toProduct(productTo);
        Long productId = productsService.addProduct(product).getId();
        URI locationUri = uriBuilder.requestUriWithAppendedId(productId);
        return ResponseEntity.created(locationUri).build();
    }

    @Override
    public ResultPageTo<ProductTo> getAll(String nameFragment, int pageNumber, int pageSize) {
        ResultPage<Product> resultPage = productsService.getProductsByName(nameFragment, pageNumber, pageSize);
        return productsMapper.toResultPageTo(resultPage);
    }

}

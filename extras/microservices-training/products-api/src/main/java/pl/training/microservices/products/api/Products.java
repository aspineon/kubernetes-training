package pl.training.microservices.products.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("products")
public interface Products {

    @PostMapping
    ResponseEntity<ProductTo> add(@RequestBody ProductTo productTo);

    @GetMapping
    ResultPageTo<ProductTo> getAll(@RequestParam(defaultValue = "") String nameFragment,
                                   @RequestParam(defaultValue = "0") int pageNumber,
                                   @RequestParam(defaultValue = "5") int pageSize);

}

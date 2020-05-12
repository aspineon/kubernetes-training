package pl.training.microservices.products.api;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class ResultPageTo<T> {

    @NonNull
    private List<T> data;
    private int pageNumber;
    private int totalPages;

}
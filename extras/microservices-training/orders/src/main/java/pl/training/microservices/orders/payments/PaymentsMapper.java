package pl.training.microservices.orders.payments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.training.microservices.payments.api.CreditCardTo;
import pl.training.microservices.payments.api.PaymentTo;
import pl.training.microservices.payments.api.PaymentsRequestTo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PaymentsMapper {

    Payment toPayment(PaymentTo paymentTo);

    PaymentStatus toPaymentStatus(PaymentStatus paymentStatus);

    PaymentsRequestTo toPaymentRequestTo(PaymentsRequest paymentsRequest);

    @Mapping(source = "expiration", target = "expirationDate")
    CreditCardTo toCreditCardTo(CreditCard creditCard);

}

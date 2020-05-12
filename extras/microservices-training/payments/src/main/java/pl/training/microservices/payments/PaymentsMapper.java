package pl.training.microservices.payments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.training.microservices.payments.api.CreditCardTo;
import pl.training.microservices.payments.api.PaymentTo;
import pl.training.microservices.payments.api.PaymentsRequestTo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PaymentsMapper {

    PaymentTo toPaymentTo(Payment payment);

    PaymentStatus toPaymentStatus(PaymentStatus paymentStatus);

    PaymentsRequest toPaymentRequest(PaymentsRequestTo paymentsRequestTo);

    @Mapping(source = "expirationDate", target = "expiration")
    CreditCard toCreditCard(CreditCardTo creditCardTo);

}

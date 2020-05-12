package pl.training.microservices.payments;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.training.microservices.payments.api.PaymentTo;

@Aspect
@Service
@RequiredArgsConstructor
public class PaymentStatusChangeNotifier {

    private final PaymentsMapper paymentsMapper;
    private final Source source;

    @AfterReturning(value = "bean(paymentsService)", returning = "payment")
    //@AfterReturning(value = "execution(void pl.training.microservices.payments.PaymentsService.pay(..))", returning = "payment")
    //@AfterReturning(value = "@annotation(NotifyPaymentStatusChange)", returning = "payment")
    private void notifyChange(Payment payment) {
        PaymentTo paymentTo = paymentsMapper.toPaymentTo(payment);
        Message<PaymentTo> message = MessageBuilder.withPayload(paymentTo).build();
        source.output().send(message);
    }

}

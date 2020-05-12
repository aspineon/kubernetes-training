package pl.training.microservices.payments;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class PaymentsService {

    @NotifyPaymentStatusChange
    public Payment pay(PaymentsRequest paymentsRequest) {
        log.info(paymentsRequest.toString());
        //fakeDelay();
        return new Payment(1L, PaymentStatus.STARTED);
    }

    private void fakeDelay() {
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

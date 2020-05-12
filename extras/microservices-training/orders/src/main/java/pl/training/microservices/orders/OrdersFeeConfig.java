package pl.training.microservices.orders;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Log
public class OrdersFeeConfig {

    @Getter
    private long fee;

    public OrdersFeeConfig(@Value("${broker-fee}") long fee) {
        log.info("Updating broker fee: " + fee);
        this.fee = fee;
    }

}

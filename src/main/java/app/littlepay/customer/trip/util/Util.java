package app.littlepay.customer.trip.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class Util {

  public static void generateTransactionId() {
    final String transactionId = UUID.randomUUID().toString();
    MDC.put("transaction-id", transactionId);
    log.info("Generated transaction-id for this process : {}", transactionId);
  }
}

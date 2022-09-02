package app.littlepay.customer.trip.service;

import app.littlepay.customer.trip.model.CustomerTrip;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PricingCalculationService {

  @Autowired
  private KieContainer kieContainer;

  public BigDecimal calculatePricing(CustomerTrip customerTrip) {
    KieSession kieSession = kieContainer.newKieSession();
    kieSession.insert(customerTrip);
    kieSession.fireAllRules();
    kieSession.dispose();
    customerTrip.setChargeAmount(customerTrip.getChargeAmount() == null ? new BigDecimal(0) : customerTrip.getChargeAmount());
    return customerTrip.getChargeAmount();
  }
}


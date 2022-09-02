package app.littlepay.customer.trip.service;

import app.littlepay.customer.trip.model.CustomerTrip;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static app.littlepay.customer.trip.Helper.STOP_1;
import static app.littlepay.customer.trip.Helper.STOP_2;
import static app.littlepay.customer.trip.Helper.STOP_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
public class PricingCalculationServiceTest {

  @Autowired
  private PricingCalculationService pricingCalculationService;

  static Stream<Arguments> chargeAmountCalculation() {
    return Stream.of(
      arguments(new BigDecimal(3.25), new CustomerTrip(null, null, 100L, STOP_1, STOP_2, null, "Company1", "Bus37", "5500005555555559", "Completed")),
      arguments(new BigDecimal(5.50), new CustomerTrip(null, null, 100L, STOP_2, STOP_3, null, "Company1", "Bus37", "5500005555555558", "Completed")),
      arguments(new BigDecimal(7.50), new CustomerTrip(null, null, 100L, STOP_3, STOP_1, null, "Company1", "Bus37", "5500005555555557", "Completed")),
      arguments(new BigDecimal(3.25), new CustomerTrip(null, null, 100L, STOP_2, STOP_1, null, "Company1", "Bus37", "5500005555555556", "Completed")),
      arguments(new BigDecimal(5.50), new CustomerTrip(null, null, 100L, STOP_3, STOP_2, null, "Company1", "Bus37", "5500005555555555", "Completed")),
      arguments(new BigDecimal(7.50), new CustomerTrip(null, null, 100L, STOP_1, STOP_3, null, "Company1", "Bus37", "5500005555555554", "Completed")),
      arguments(new BigDecimal(7.50), new CustomerTrip(null, null, 100L, STOP_1, "", null, "Company1", "Bus37", "5500005555555553", "Incomplete")),
      arguments(new BigDecimal(5.50), new CustomerTrip(null, null, 100L, STOP_2, "", null, "Company1", "Bus37", "5500005555555552", "Incomplete")),
      arguments(new BigDecimal(7.50), new CustomerTrip(null, null, 100L, STOP_3, "", null, "Company1", "Bus37", "5500005555555551", "Incomplete")),
      arguments(new BigDecimal(0), new CustomerTrip(null, null, 100L, STOP_3, STOP_3, null, "Company1", "Bus37", "5500005555555550", "Cancelled")));
  }

  @ParameterizedTest
  @MethodSource("chargeAmountCalculation")
  public void testCalculatePricing_whenInvokedByCustomerTripInfo_thenShouldCalculateChargeAmount(BigDecimal calculatedAmount, CustomerTrip customerTrip) {

    //WHEN
    pricingCalculationService.calculatePricing(customerTrip);

    //THEN
    assertEquals(calculatedAmount.compareTo(customerTrip.getChargeAmount()), 0);
  }
}


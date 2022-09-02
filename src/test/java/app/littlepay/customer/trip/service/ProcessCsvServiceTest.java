package app.littlepay.customer.trip.service;

import app.littlepay.customer.trip.model.CustomerTapInfo;
import app.littlepay.customer.trip.model.CustomerTrip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.List;

import static app.littlepay.customer.trip.Helper.createListOfCustomerTapInfo;
import static app.littlepay.customer.trip.Helper.createListOfCustomersTrip;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessCsvServiceTest {

  @Autowired
  private ProcessCsvService processCsvService;

  @Test
  public void testProcessCustomerTapDetails_whenListOfCustomerTapInfoIsGiven_thenGenerateCustomerTravelDataWithAmountAndStatus() {

    //GIVEN
    List<CustomerTapInfo> customersTapInfoList = createListOfCustomerTapInfo();
    List<CustomerTrip> expectedCustomersTripList = createListOfCustomersTrip();

    //WHEN
    List<CustomerTrip> customerTrips = processCsvService.processCustomerTapDetails(customersTapInfoList);

    //THEN
    customerTrips.sort(Comparator.comparing(CustomerTrip::getPan));
    expectedCustomersTripList.sort(Comparator.comparing(CustomerTrip::getPan));

    assertThat(customerTrips).usingRecursiveComparison().isEqualTo(expectedCustomersTripList);
  }

}


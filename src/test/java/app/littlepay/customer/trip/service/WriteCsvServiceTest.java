package app.littlepay.customer.trip.service;

import app.littlepay.customer.trip.exception.CustomerTripTechnicalException;
import app.littlepay.customer.trip.model.CustomerTrip;
import app.littlepay.customer.trip.model.TripStatus;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static app.littlepay.customer.trip.Helper.createListOfCustomersTrip;
import static app.littlepay.customer.trip.Helper.getFileAsInputStream;
import static org.junit.Assert.assertArrayEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WriteCsvServiceTest {

  @Autowired
  private WriteCsvService writeCsvService;

  @Test
  public void testGenerateCustomersTripCSVFile_whenListOfCustomersTripIsGenerated_thenCreateCustomerTripCSVFile() throws Exception {

    //GIVEN
    List<CustomerTrip> customerTrips = createListOfCustomersTrip();

    //WHEN
    byte[] customerTripCsvData = writeCsvService.generateCustomersTripCSVFile(customerTrips);

    //THEN
    byte[] expectedCsvContent = IOUtils.toByteArray(getFileAsInputStream("test-data/output/customer-trip-data-generated.csv"));
    assertArrayEquals(customerTripCsvData, expectedCsvContent);
  }

  @Test(expected = CustomerTripTechnicalException.class)
  public void testGenerateCustomersTripCSVFile_whenRequiredFieldsDoNotExist_thenTechnicalExceptionIsThrown() throws Exception {

    List<CustomerTrip> customerTrips = new ArrayList<>();
    customerTrips.add(new CustomerTrip(LocalDateTime.of(2022, 9, 22, 13, 0, 0), LocalDateTime.of(2022, 9, 22, 16, 5, 0), 11100L, "", "", null, "Company1", "Bus37", "5500005555555559", TripStatus.COMPLETED.getTripStatus()));

    //WHEN
    writeCsvService.generateCustomersTripCSVFile(customerTrips);
  }
}

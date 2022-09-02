package app.littlepay.customer.trip.service;

import app.littlepay.customer.trip.model.CustomerTapInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.List;

import static app.littlepay.customer.trip.Helper.createListOfCustomerTapInfo;
import static app.littlepay.customer.trip.Helper.getFileAsInputStream;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadCsvServiceTest {

  @Autowired
  private ReadCsvService readCSVService;

  @Test
  public void testReadAndMapCustomerTapDetails_whenCustomerTapInfoCsvIsReceived_thenShouldProcessAndMapToBean()
    throws Exception {

    //GIVEN
    List<CustomerTapInfo> expectedCustomersTapInfoList = createListOfCustomerTapInfo();
    MockMultipartFile customerTapInfoFile = new MockMultipartFile("file", getFileAsInputStream("test-data/input/customer-tap-info-file-convert-to-bean.csv"));

    //WHEN
    List<CustomerTapInfo> customerTapInfoList = readCSVService.readAndMapCustomerTapDetails(customerTapInfoFile);

    //THEN
    customerTapInfoList.sort(Comparator.comparingLong(CustomerTapInfo::getId));
    expectedCustomersTapInfoList.sort(Comparator.comparingLong(CustomerTapInfo::getId));

    assertThat(customerTapInfoList).usingRecursiveComparison().isEqualTo(expectedCustomersTapInfoList);
  }
}

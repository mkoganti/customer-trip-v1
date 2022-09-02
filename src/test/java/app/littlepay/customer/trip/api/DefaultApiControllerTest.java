package app.littlepay.customer.trip.api;

import app.littlepay.customer.trip.service.ProcessCsvService;
import app.littlepay.customer.trip.service.ReadCsvService;
import app.littlepay.customer.trip.service.WriteCsvService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;

import static app.littlepay.customer.trip.Helper.getFileAsInputStream;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DefaultApiControllerTest {

  @Autowired
  ReadCsvService readCSVService;

  @Autowired
  ProcessCsvService processCsvService;

  @Autowired
  WriteCsvService writeCsvService;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private DefaultApiController defaultApiController;


  @Test
  public void testGenerateCustomerTripDetail_whenCustomerTapInfoCsvIsUploaded_thenCustomerTripInfoShouldBeGeneratedSuccessfully()
    throws Exception {

    // GIVEN
    MockMultipartFile customerTapInfoFile = new MockMultipartFile("file", getFileAsInputStream("test-data/input/customer-tap-info-file-all-scenarios.csv"));
    InputStream customerTripData = getFileAsInputStream("test-data/output/customer-trip-data-all-scenarios.csv");

    // WHEN - THEN
    mvc.perform(
      MockMvcRequestBuilders.multipart("/").file(customerTapInfoFile)
        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
      .andExpect(status().isOk())
      .andExpect(content().contentType("text/csv"))
      .andExpect(content().bytes(IOUtils.toByteArray(customerTripData)));

  }
}
package app.littlepay.customer.trip.api;

import app.littlepay.customer.trip.exception.CustomerTripTechnicalException;
import app.littlepay.customer.trip.model.CustomerTapInfo;
import app.littlepay.customer.trip.model.CustomerTrip;
import app.littlepay.customer.trip.service.ProcessCsvService;
import app.littlepay.customer.trip.service.ReadCsvService;
import app.littlepay.customer.trip.service.WriteCsvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import static app.littlepay.customer.trip.util.Util.generateTransactionId;

@Controller
@RequestMapping("/")
@Slf4j
public class DefaultApiController {

  @Autowired
  private ReadCsvService readCsvService;

  @Autowired
  private ProcessCsvService processCsvService;

  @Autowired
  private WriteCsvService writeCsvService;

  @PostMapping("/")
  public ResponseEntity<Resource> generateCustomerTripDetail(@RequestHeader final Map<String, Object> headers,
                                                             @RequestParam("file") MultipartFile file)
    throws CustomerTripTechnicalException {

    generateTransactionId();
    log.info("Incoming HTTP Headers : {}", headers);

    try {

      List<CustomerTapInfo> customersTapInfo = readCsvService.readAndMapCustomerTapDetails(file);

      List<CustomerTrip> customersTripList = processCsvService.processCustomerTapDetails(customersTapInfo);
      log.info("Number of customer travel detail records to be written to csv file : {}", customersTripList.size());

      byte[] customersTripCsvContent = writeCsvService.generateCustomersTripCSVFile(customersTripList);
      InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(customersTripCsvContent));

      return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("text/csv"))
        .body(inputStreamResource);

    } catch (Exception exception) {

      throw new CustomerTripTechnicalException(exception.getMessage());
    }
  }
}

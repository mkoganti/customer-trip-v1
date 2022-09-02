package app.littlepay.customer.trip.service;

import app.littlepay.customer.trip.exception.CustomerTripTechnicalException;
import app.littlepay.customer.trip.model.CustomerTrip;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WriteCsvService {

  public byte[] generateCustomersTripCSVFile(List<CustomerTrip> customersTripList) throws CustomerTripTechnicalException {

    //This custom strategy (33 to 47) has been written to get the header displayed in required order and required case.
    HeaderColumnNameMappingStrategy<CustomerTrip> headerColumnNameMappingStrategy = new HeaderColumnNameMappingStrategy<>();
    headerColumnNameMappingStrategy.setType(CustomerTrip.class);

    String headerLine = Arrays.stream(CustomerTrip.class.getDeclaredFields())
      .map(field -> field.getAnnotation(CsvBindByName.class))
      .filter(Objects::nonNull)
      .map(CsvBindByName::column)
      .collect(Collectors.joining(","));

    try (StringReader reader = new StringReader(headerLine)) {
      CsvToBean<CustomerTrip> csv = new CsvToBeanBuilder<CustomerTrip>(reader)
        .withType(CustomerTrip.class)
        .withMappingStrategy(headerColumnNameMappingStrategy)
        .build();
      for (CustomerTrip customerTrip : csv) {
      }
    }

    try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
         OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
         CSVWriter writer = new CSVWriter(streamWriter, CSVWriter.DEFAULT_SEPARATOR,
           CSVWriter.NO_QUOTE_CHARACTER,
           CSVWriter.DEFAULT_ESCAPE_CHARACTER,
           CSVWriter.RFC4180_LINE_END)) {

      StatefulBeanToCsv<CustomerTrip> customerTripToCsv = new StatefulBeanToCsvBuilder<CustomerTrip>(writer)
        .withMappingStrategy(headerColumnNameMappingStrategy)
        .build();
      customerTripToCsv.write(customersTripList);
      streamWriter.flush();

      log.info("Completed writing {} customers travel data to CSV file", customersTripList.size());
      return stream.toByteArray();

    } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException exception) {
      log.error("Error while writing bean CustomerTrip date to CSV File - ", exception);
      throw new CustomerTripTechnicalException(exception.getMessage());
    }
  }
}

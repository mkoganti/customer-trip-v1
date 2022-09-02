package app.littlepay.customer.trip.service;

import app.littlepay.customer.trip.model.CustomerTapInfo;
import app.littlepay.customer.trip.model.CustomerTrip;
import app.littlepay.customer.trip.model.TapType;
import app.littlepay.customer.trip.model.TripStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static app.littlepay.customer.trip.model.TripStatus.CANCELLED;
import static app.littlepay.customer.trip.model.TripStatus.COMPLETED;

@Service
@Slf4j
public class ProcessCsvService {

  @Autowired
  private PricingCalculationService pricingCalculationService;

  @Autowired
  private ModelMapper modelMapper;

  public List<CustomerTrip> processCustomerTapDetails(List<CustomerTapInfo> customerTapInfoList) {

    Map<String, List<CustomerTapInfo>> tapInfoListPerCustomer = customerTapInfoList.stream()
      .collect(Collectors.groupingBy(CustomerTapInfo::getPan));
    log.info("Number of customers have been obtained by grouping their pan : {}", tapInfoListPerCustomer.size());

    return tapInfoListPerCustomer.values().stream()
      .map(this::constructCustomerTrip).collect(Collectors.toList());

  }

  private List<CustomerTapInfo> getRecordsPerTapTypeOfCustomer(List<CustomerTapInfo> tapInfoPerCustomer, TapType tapType) {

    return tapInfoPerCustomer.stream()
      .filter(customerTapInfo -> customerTapInfo.getTapType().equalsIgnoreCase(tapType.getTapType()))
      .collect(Collectors.toList());

  }

  private CustomerTrip constructCustomerTrip(List<CustomerTapInfo> customerTapList) {

    CustomerTrip customerTrip = new CustomerTrip();

    //Retrieving ON taps per customer and sorting in descending, so that only latest tap-on can be fetched and initial tap ons can be ignored
    List<CustomerTapInfo> customerTapONList = getRecordsPerTapTypeOfCustomer(customerTapList, TapType.ON);
    customerTapONList.sort(Comparator.comparing(CustomerTapInfo::getDateTimeUTC).reversed());

    List<CustomerTapInfo> customerTapOFFList = getRecordsPerTapTypeOfCustomer(customerTapList, TapType.OFF);

    int numberOfTapONs = customerTapONList.size();
    int numberOfTapOFFs = customerTapOFFList.size();

    if (numberOfTapONs >= 1) {

      CustomerTapInfo mostRecentTapONInfo = customerTapONList.get(0);
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
      customerTrip = modelMapper.map(mostRecentTapONInfo, CustomerTrip.class);
      customerTrip.setStarted(mostRecentTapONInfo.getDateTimeUTC());
      customerTrip.setFromStopId(mostRecentTapONInfo.getStopId());

      if (numberOfTapOFFs == 1) {

        CustomerTapInfo tapOFFInfo = customerTapOFFList.get(0);
        customerTrip.setToStopId(tapOFFInfo.getStopId());
        customerTrip.setStatus(mostRecentTapONInfo.getStopId().equals(tapOFFInfo.getStopId()) ?
          CANCELLED.getTripStatus() : COMPLETED.getTripStatus());
        customerTrip.setFinished(tapOFFInfo.getDateTimeUTC());
        customerTrip.setDurationSecs(ChronoUnit.SECONDS.between(customerTrip.getStarted(), customerTrip.getFinished()));

      } else if (numberOfTapOFFs == 0) {
        customerTrip.setStatus(TripStatus.INCOMPLETE.getTripStatus());
        customerTrip.setToStopId(StringUtils.EMPTY);
      }
      pricingCalculationService.calculatePricing(customerTrip);

    }
    return customerTrip;
  }
}

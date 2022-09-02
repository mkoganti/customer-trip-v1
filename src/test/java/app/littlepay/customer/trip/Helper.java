package app.littlepay.customer.trip;

import app.littlepay.customer.trip.api.DefaultApiController;
import app.littlepay.customer.trip.model.CustomerTapInfo;
import app.littlepay.customer.trip.model.CustomerTrip;
import app.littlepay.customer.trip.model.TapType;
import app.littlepay.customer.trip.model.TripStatus;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Helper {

  public static final String STOP_1 = "Stop1";
  public static final String STOP_2 = "Stop2";
  public static final String STOP_3 = "Stop3";


  public static List<CustomerTapInfo> createListOfCustomerTapInfo() {
    List<CustomerTapInfo> customersTapInfo = new ArrayList<>();
    customersTapInfo.add(new CustomerTapInfo(1L, LocalDateTime.of(2022, 9, 22, 13, 0, 0), TapType.ON.getTapType(), STOP_1, "Company1", "Bus37", "5500005555555559"));
    customersTapInfo.add(new CustomerTapInfo(2L, LocalDateTime.of(2022, 9, 22, 16, 5, 0), TapType.OFF.getTapType(), STOP_2, "Company1", "Bus37", "5500005555555559"));
    customersTapInfo.add(new CustomerTapInfo(3L, LocalDateTime.of(2022, 9, 22, 12, 5, 0), TapType.ON.getTapType(), STOP_3, "Company1", "Bus37", "5500005555555559"));
    customersTapInfo.add(new CustomerTapInfo(4L, LocalDateTime.of(2022, 9, 22, 19, 5, 0), TapType.OFF.getTapType(), STOP_2, "Company2", "Bus38", "5500005555555560"));
    customersTapInfo.add(new CustomerTapInfo(5L, LocalDateTime.of(2022, 9, 22, 17, 5, 0), TapType.ON.getTapType(), STOP_1, "Company2", "Bus38", "5500005555555560"));
    customersTapInfo.add(new CustomerTapInfo(6L, LocalDateTime.of(2022, 9, 22, 17, 5, 0), TapType.ON.getTapType(), STOP_1, "Company2", "Bus32", "5500005555555561"));
    customersTapInfo.add(new CustomerTapInfo(7L, LocalDateTime.of(2022, 9, 22, 17, 5, 0), TapType.ON.getTapType(), STOP_1, "Company2", "Bus38", "5500005555555562"));
    customersTapInfo.add(new CustomerTapInfo(8L, LocalDateTime.of(2022, 9, 22, 17, 15, 0), TapType.OFF.getTapType(), STOP_1, "Company2", "Bus38", "5500005555555562"));
    return customersTapInfo;
  }

  public static List<CustomerTrip> createListOfCustomersTrip() {

    List<CustomerTrip> customerTrips = new ArrayList<>();
    customerTrips.add(new CustomerTrip(LocalDateTime.of(2022, 9, 22, 13, 0, 0), LocalDateTime.of(2022, 9, 22, 16, 5, 0), 11100L, STOP_1, STOP_2, new BigDecimal(3.25), "Company1", "Bus37", "5500005555555559", TripStatus.COMPLETED.getTripStatus()));
    customerTrips.add(new CustomerTrip(LocalDateTime.of(2022, 9, 22, 17, 5, 0), LocalDateTime.of(2022, 9, 22, 17, 15, 0), 600L, STOP_1, STOP_1, new BigDecimal(0), "Company2", "Bus38", "5500005555555562", TripStatus.CANCELLED.getTripStatus()));
    customerTrips.add(new CustomerTrip(LocalDateTime.of(2022, 9, 22, 17, 5, 0), LocalDateTime.of(2022, 9, 22, 19, 5, 0), 7200L, STOP_1, STOP_2, new BigDecimal(3.25), "Company2", "Bus38", "5500005555555560", TripStatus.COMPLETED.getTripStatus()));
    customerTrips.add(new CustomerTrip(LocalDateTime.of(2022, 9, 22, 17, 5, 0), null, null, STOP_1, "", new BigDecimal(7.5), "Company2", "Bus32", "5500005555555561", TripStatus.INCOMPLETE.getTripStatus()));
    return customerTrips;
  }

  public static InputStream getFileAsInputStream(final String path) {
    return Objects.requireNonNull(DefaultApiController.class.getClassLoader().getResourceAsStream(path));
  }
}

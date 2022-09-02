package app.littlepay.customer.trip.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTrip {

  @CsvBindByName(column = "Started", required = true)
  private LocalDateTime started;

  @CsvBindByName(column = "Finished")
  private LocalDateTime finished;

  @CsvBindByName(column = "DurationSecs")
  private Long durationSecs;

  @CsvBindByName(column = "FromStopId", required = true)
  private String fromStopId;

  @CsvBindByName(column = "ToStopId")
  private String toStopId;

  @CsvBindByName(column = "ChargeAmount", required = true)
  private BigDecimal chargeAmount;

  @CsvBindByName(column = "CompanyId", required = true)
  private String companyId;

  @CsvBindByName(column = "BusId", required = true)
  private String busId;

  @CsvBindByName(column = "PAN", required = true)
  private String pan;

  @CsvBindByName(column = "Status", required = true)
  private String status;

}

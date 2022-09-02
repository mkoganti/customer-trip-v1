package app.littlepay.customer.trip.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CustomerTapInfo {

  @CsvBindByName
  private Long id;

  @CsvDate(value = "dd-MM-yyyy HH:mm:ss")
  @CsvBindByName
  private LocalDateTime dateTimeUTC;

  @CsvBindByName
  private String tapType;

  @CsvBindByName
  private String stopId;

  @CsvBindByName
  private String companyId;

  @CsvBindByName
  private String busId;

  @CsvBindByName
  private String pan;
}


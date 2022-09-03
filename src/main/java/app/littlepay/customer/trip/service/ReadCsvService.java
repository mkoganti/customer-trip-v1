package app.littlepay.customer.trip.service;

import app.littlepay.customer.trip.model.CustomerTapInfo;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReadCsvService {


  public List<CustomerTapInfo> readAndMapCustomerTapDetails(MultipartFile file) throws IOException {

    List<CustomerTapInfo> customerTapInfoList;

    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      CsvToBean<CustomerTapInfo> cb = new CsvToBeanBuilder<CustomerTapInfo>(reader)
        .withIgnoreLeadingWhiteSpace(true)
        .withType(CustomerTapInfo.class)
        .build();

      customerTapInfoList = new ArrayList<>(cb.parse());
      log.info("Completed reading CSV file and converting to bean. Number of customers tap details: {}", customerTapInfoList.size());
    }
    return customerTapInfoList;
  }


}

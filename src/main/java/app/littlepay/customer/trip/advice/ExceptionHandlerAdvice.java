package app.littlepay.customer.trip.advice;

import app.littlepay.customer.trip.exception.CustomerTripTechnicalException;
import app.littlepay.customer.trip.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

  @ExceptionHandler(MultipartException.class)
  public ResponseEntity<ErrorResponse> handleFileSizeLimitExceededException(MultipartException exception) {

    ErrorResponse error = new ErrorResponse("001", "Maximum upload size exceeded. Request rejected as its size exceeds maximum configured size of 10MB");

    log.error("Received attachment size is more than 10MB", exception);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomerTripTechnicalException.class)
  public ResponseEntity<ErrorResponse> handleCustomerTripException(CustomerTripTechnicalException exception) {

    ErrorResponse error = new ErrorResponse("002", exception.getMessage());

    log.error("Unexpected error occurred", exception);
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

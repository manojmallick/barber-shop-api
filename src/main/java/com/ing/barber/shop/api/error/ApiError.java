package com.ing.barber.shop.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/** The type Api error. */
@Setter
@AllArgsConstructor
@Getter
public class ApiError {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  private String code;
  private String type;
  private HttpStatus status;
  private String message;
  private List errors;
}

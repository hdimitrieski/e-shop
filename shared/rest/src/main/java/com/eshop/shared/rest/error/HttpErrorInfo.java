package com.eshop.shared.rest.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * Record <code>HttpErrorInfo</code> which encapsulate all HTTP errors sent to client.
 *
 * @implNote Since it is a record and not normal POJO, so it needs some customizations
 * to be serialized to JSON and this is done with method <code>GlobalConfiguration.jacksonCustomizer()</code>.
 */
public record HttpErrorInfo(
    HttpStatus httpStatus,
    String message,
    String path,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    ZonedDateTime timestamp
) {

  /**
   * Instantiates a new Http error info.
   *
   * @param httpStatus the http status code and type.
   * @param path       the request path.
   * @param message    the error message.
   */
  public HttpErrorInfo(HttpStatus httpStatus, String path, String message) {
    this(httpStatus, message, path, ZonedDateTime.now());
  }
}

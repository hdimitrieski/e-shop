package com.eshop.ordering.api.infrastructure.exceptions;

import com.eshop.shared.rest.error.ControllerExceptionHandler;
import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import com.eshop.shared.rest.error.HttpErrorInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * The class Global controller exception handler is a generic and central point for all exceptions.
 */
@RestControllerAdvice
@Log4j2
class GlobalControllerExceptionHandler extends ControllerExceptionHandler {

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(OrderingDomainException.class)
  public @ResponseBody
  HttpErrorInfo handleCatalogDomainExceptions(OrderingDomainException ex, WebRequest request) {
    return createHttpErrorInfo(BAD_REQUEST, ex, request);
  }

  @ResponseStatus(UNAUTHORIZED)
  @ExceptionHandler(UnauthorizedException.class)
  public @ResponseBody
  HttpErrorInfo handleUnauthorizedExceptions(UnauthorizedException ex, WebRequest request) {
    return createHttpErrorInfo(UNAUTHORIZED, ex, request);
  }

}

package com.eshop.catalog.infrastructure;

import com.eshop.catalog.domain.base.CatalogDomainException;
import com.eshop.shared.rest.error.ControllerExceptionHandler;
import com.eshop.shared.rest.error.HttpErrorInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * The class Global controller exception handler is a generic and central point for all exceptions.
 */
@RestControllerAdvice
@Log4j2
class GlobalControllerExceptionHandler extends ControllerExceptionHandler {

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(CatalogDomainException.class)
  public @ResponseBody
  HttpErrorInfo handleCatalogDomainExceptions(CatalogDomainException ex, WebRequest request) {
    return createHttpErrorInfo(BAD_REQUEST, ex, request);
  }

}

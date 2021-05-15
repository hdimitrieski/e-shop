package com.eshop.shared.rest.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * The class Global controller exception handler is a generic and central point for all exceptions.
 */
public abstract class ControllerExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  /**
   * Method to handle <i>not found exceptions</i> http error info.
   *
   * @param request the request to get some request information
   * @param ex      the ex to get its information
   * @return the http error information.
   */
  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public @ResponseBody
  HttpErrorInfo handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
    return createHttpErrorInfo(NOT_FOUND, ex, request);
  }

  /**
   * Method to handle <i>not found exceptions</i> http error info.
   *
   * @param request the request to get some request information
   * @param ex      the ex to get its information
   * @return the http error information.
   */
  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(BadRequestException.class)
  public @ResponseBody
  HttpErrorInfo handleNotFoundExceptions(BadRequestException ex, WebRequest request) {
    return createHttpErrorInfo(NOT_FOUND, ex, request);
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public @ResponseBody
  HttpErrorInfo handleUnhandledExceptions(Exception ex, WebRequest request) {
    return createHttpErrorInfo(INTERNAL_SERVER_ERROR, ex, request);
  }

  protected HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, Exception ex, WebRequest request) {
    final var path = request.getContextPath();
    final var message = ex.getMessage();

    logger.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
    return new HttpErrorInfo(httpStatus, path, message);
  }
}

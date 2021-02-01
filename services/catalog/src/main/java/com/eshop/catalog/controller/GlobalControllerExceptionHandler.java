package com.eshop.catalog.controller;

import com.eshop.catalog.model.CatalogDomainException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

/**
 * The class Global controller exception handler is a generic and central point for all exceptions.
 */
@RestControllerAdvice
@Log4j2
class GlobalControllerExceptionHandler {

    /**
     * Method to handle <i>not found exceptions</i> http error info.
     *
     * @param request the request to get some request information
     * @param ex the ex to get its information
     * @return the http error information.
     */
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody HttpErrorInfo handleNotFoundExceptions(ServerHttpRequest request, Exception ex) {
        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }

    /**
     * Method to handle <i>invalid input exception</i> http error info.
     *
     * @param request the request to get some request information
     * @param ex the ex to get its information
     * @return the http error information.
     */
//    @ResponseStatus(UNPROCESSABLE_ENTITY)
//    @ExceptionHandler(InvalidInputException.class)
//    public @ResponseBody HttpErrorInfo handleInvalidInputException(
//            ServerHttpRequest request, Exception ex) {
//        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
//    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(CatalogDomainException.class)
    public @ResponseBody HttpErrorInfo handleCatalogDomainExceptions(ServerHttpRequest request, Exception ex) {
        return createHttpErrorInfo(BAD_REQUEST, request, ex);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody HttpErrorInfo handleUnhandledExceptions(ServerHttpRequest request, Exception ex) {
        return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {
        final var path = request.getPath().pathWithinApplication().value();
        final var message = ex.getMessage();

        log.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }
}

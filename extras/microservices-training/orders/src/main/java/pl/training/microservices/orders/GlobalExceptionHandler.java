package pl.training.microservices.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.training.microservices.orders.api.ExceptionTo;

import java.util.Locale;

@ControllerAdvice
@Log
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionTo> onException(Exception exception, Locale locale) {
        log.finest("Exception:" + exception.getMessage());
        return createResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, locale);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionTo> onProductNotFound(ProductNotFoundException exception, Locale locale) {
        return createResponse(exception, HttpStatus.NOT_FOUND, locale);
    }

    private ResponseEntity<ExceptionTo> createResponse(Exception exception, HttpStatus httpStatus, Locale locale) {
        String messageKey = exception.getClass().getSimpleName();
        String description;
        try {
            description = messageSource.getMessage(messageKey, null, locale);
        } catch (NoSuchMessageException ex) {
            description = messageKey;
        }
        ExceptionTo exceptionDto = new ExceptionTo(description);
        return ResponseEntity.status(httpStatus).body(exceptionDto);
    }

}

package hu.robnn.reportserver.exception;

import hu.robnn.auth.dao.model.dto.Message;
import hu.robnn.auth.dao.model.dto.Severity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ReportServerMappedException.class})
    protected ResponseEntity<Message> handleError(RuntimeException ex, WebRequest request){
        ReportServerMappedException casted = (ReportServerMappedException) ex;
        Message messageObject;
        if (casted.getErrorCause() != null) {
            messageObject = new Message(Severity.ERROR, casted.getErrorCause().getCause());
        } else {
            messageObject = new Message(Severity.ERROR, casted.getErrorCauseString());
        }
        return new ResponseEntity<>(messageObject, HttpStatus.BAD_REQUEST);
    }
}

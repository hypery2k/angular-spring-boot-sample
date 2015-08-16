package ngSpring.demo.errorhandling;

import ngSpring.demo.exceptions.EntityNotFoundException;
import ngSpring.demo.exceptions.ValidationException;
import ngSpring.demo.util.Message;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOG = Logger
            .getLogger(GlobalControllerExceptionHandler.class);

    // SECURITY ACCESS HANDLING

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<?> handleSecurityErrors(AccessDeniedException ex) {
        LOG.debug("Got access error", ex);
        // fallback to server error
        return new ResponseEntity<Message>(new Message(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    // GENERAL ERROR HANDLING

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleErrors(Exception ex) {
        if (ex.getCause() instanceof RollbackException
                && ex.getCause().getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex.getCause().getCause();
            return new ResponseEntity<List<ValidationMessage>>(getValidationErrorResponse(constraintViolationException), HttpStatus.BAD_REQUEST);
        } else {
            LOG.error("Got unknown error", ex);
            // fallback to server error
            return new ResponseEntity<Message>(new Message(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // VALIDATION ERROR HANDLING

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException ex) {
        final List<ValidationMessage> validationErrors = new ArrayList<>();
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            validationErrors.add(ValidationMessage.builder()
                    .entity(fieldError.getField())
                    .messageTemplate(fieldError.getDefaultMessage())
                    .build());
        }
        return new ResponseEntity<List<ValidationMessage>>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResponseEntity<?> handleBadRequestException(ValidationException ex) {
        LOG.error("Got validation errors", ex);
        if (ex.getValidationMessages() == null || ex.getValidationMessages().isEmpty()) {
            return new ResponseEntity<Message>(new Message(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<List<ValidationMessage>>(ex.getValidationMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Message> handleEntityNotFoundException(
            EntityNotFoundException ex) {
        LOG.error("Could not find entity with id " + ex.getId(), ex);
        return new ResponseEntity<Message>(new Message(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    private List<ValidationMessage> getValidationErrorResponse(ConstraintViolationException constraintViolationException) {
        final List<ValidationMessage> validationErrors = new ArrayList<>();
        LOG.error("Got validation errors", constraintViolationException);
        for (ConstraintViolation<?> violationSet : constraintViolationException.getConstraintViolations()) {
            List<String> propertyList = new ArrayList<>();
            Iterator<Path.Node> propertyIterator = violationSet
                    .getPropertyPath().iterator();
            while (propertyIterator.hasNext()) {
                propertyList.add(propertyIterator.next().getName());
            }
            // add violations errors in response
            validationErrors.add(ValidationMessage.builder()
                    .entity(violationSet.getRootBeanClass().getName())
                            // remove { and }
                    .messageTemplate(violationSet.getMessageTemplate().replaceAll("^[{]|[}]$", ""))
                    .propertyList(propertyList).build());
        }
        return validationErrors;
    }
}

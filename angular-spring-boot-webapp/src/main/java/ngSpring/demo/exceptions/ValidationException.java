package ngSpring.demo.exceptions;

import ngSpring.demo.errorhandling.ValidationMessage;

import java.util.List;

/**
 * Indicate validation errors which are not handled via bean validation,
 * e.g. during file parsing
 *
 * @author hypery2k
 */
@SuppressWarnings("serial")
public class ValidationException extends BusinessException {

    private List<ValidationMessage> validationMessages;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, List<ValidationMessage> validationMessages) {
        super(message);
        this.validationMessages = validationMessages;
    }

    public List<ValidationMessage> getValidationMessages() {
        return validationMessages;
    }

}
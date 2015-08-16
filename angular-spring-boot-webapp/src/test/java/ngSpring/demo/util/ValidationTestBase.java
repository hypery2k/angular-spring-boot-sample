package ngSpring.demo.util;

import org.junit.BeforeClass;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Common base to run bean validation unit tests
 *
 * @author hypery2k
 */
public class ValidationTestBase<T> {

    /**
     * the bean validator instance
     */
    protected static Validator validator;

    /**
     * set up the correct validation
     */
    @BeforeClass
    public static void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // HELPER METHODS
    protected List<String> getValidationMessages(Set<ConstraintViolation<T>> constraintViolations) {
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<T> violation : constraintViolations) {
            messages.add(violation.getMessageTemplate());
        }
        return messages;
    }

}
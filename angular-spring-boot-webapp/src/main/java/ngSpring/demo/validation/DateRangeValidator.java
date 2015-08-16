package ngSpring.demo.validation;

import org.apache.log4j.Logger;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Validator for ensure that a start date is before a end date
 *
 * @author hypery2k
 */
public class DateRangeValidator extends AbstractBeanValidator<ValidateDateRange> {

    /**
     * Write logging messages for this class.
     */
    private static final Logger LOG = Logger.getLogger(DateRangeValidator.class);

    private String start;

    private String end;

    private boolean equal;

    /**
     * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(final ValidateDateRange constraintAnnotation) {
        this.start = constraintAnnotation.start();
        this.end = constraintAnnotation.end();
        this.equal = constraintAnnotation.equal();
    }

    /**
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean isValid = false;
        final Class clazz = value.getClass();

        try {
            Date startDate = null;
            final Method startMethod = clazz.getMethod(this.getGetterDeclaration(this.start), new Class[0]);
            if (startMethod != null) {
                startDate = (Date) startMethod.invoke(value, null);
            }

            Date endDate = null;
            final Method endMethod = clazz.getMethod(this.getGetterDeclaration(this.end), new Class[0]);
            if (startMethod != null) {
                endDate = (Date) endMethod.invoke(value, null);
            }

            if (endDate != null && startDate != null) {
                if (this.equal) {
                    isValid = endDate.compareTo(startDate) >= 0;
                } else {
                    isValid = endDate.after(startDate);
                }
            } else {
                isValid = true;
            }
        } catch (final Exception e) {
            LOG.error("Error occurred during validating date range.", e);
        }
        if (!isValid) {
            final String template = context.getDefaultConstraintMessageTemplate();
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(template)
                    .addPropertyNode(this.start)
                    .addPropertyNode(this.end)
                    .addBeanNode()
                    .addConstraintViolation();
        }
        return isValid;
    }

}
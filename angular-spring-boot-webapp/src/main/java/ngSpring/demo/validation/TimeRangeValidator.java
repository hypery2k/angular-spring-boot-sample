package ngSpring.demo.validation;

import org.apache.log4j.Logger;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This bean validator checks the given morning and evening time stamps if they are valid (start
 * must before end for
 * each entry) and if both are set if the morning time is before evening time.
 * <p/>
 * Note that it's possible that two pairs ( morning) can be left empty or set to 00:00
 *
 * @author hypery2k
 */
public class TimeRangeValidator extends AbstractBeanValidator<ValidateTimeRange> {

    public static final String PLACEHOLDER_EMTPY_TIME = "00:00";

    private String start;

    private String end;

    /**
     * Write logging messages for this class.
     */
    private static final Logger LOG = Logger.getLogger(TimeRangeValidator.class);

    /**
     * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(final ValidateTimeRange constraintAnnotation) {
        this.start = constraintAnnotation.start();
        this.end = constraintAnnotation.end();
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
            final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            final Method start = clazz.getMethod(this.getGetterDeclaration(this.start), new Class[0]);
            if (start != null) {
                Date startDate = null;
                final String propertValue = (String) start.invoke(value, null);
                if (propertValue != null && !propertValue.equals("") && !propertValue.equals(PLACEHOLDER_EMTPY_TIME)) {
                    startDate = format.parse(propertValue);
                }
                if (startDate == null) {
                    isValid = true;
                } else {
                    final Method end = clazz.getMethod(this.getGetterDeclaration(this.end), new Class[0]);
                    if (end != null) {
                        final String propertValueEnd = (String) end.invoke(value, null);
                        if (propertValueEnd != null && !propertValueEnd.equals("") && !propertValueEnd.equals(PLACEHOLDER_EMTPY_TIME)) {
                            Date endDate = format.parse(propertValueEnd);
                            // both are not null and end is after start
                            isValid = endDate.after(startDate);
                        }
                    }
                }
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
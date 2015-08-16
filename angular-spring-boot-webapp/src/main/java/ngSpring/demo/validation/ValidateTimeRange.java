package ngSpring.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Simply checks if the given start time is before the end time and evening time is later the
 * morning time
 *
 * @author hypery2k
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TimeRangeValidator.class})
@Documented
public @interface ValidateTimeRange {

    String message() default "{validation.time.range_error}";

    String start();

    String end();

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

    /**
     * Defines several {@code @ValidateTimeRange} annotations on the same element.
     */
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        ValidateTimeRange[] value();
    }
}
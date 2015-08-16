package ngSpring.demo.validation;


import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;

/**
 * Common superclass for bean validators to share some common functionality for reflection etc
 *
 * @author hypery2k
 */
public abstract class AbstractBeanValidator<K extends Annotation> implements ConstraintValidator<K, Object> {

    /**
     * @param pAttribute to get the method for
     * @return the getter method name for the given attribute
     */
    protected String getGetterDeclaration(final String pAttribute) {
        if (StringUtils.hasLength(pAttribute)) {
            final StringBuilder builder = new StringBuilder("get");
            builder.append(Character.toUpperCase(pAttribute.charAt(0)));
            builder.append(pAttribute.substring(1));
            return builder.toString();
        } else {
            return "";
        }
    }

    /**
     * @param pAttribute to get the method for
     * @return the is method name for the given boolean attribute
     */
    protected String getIsDeclaration(final String pAttribute) {
        if (StringUtils.hasLength(pAttribute)) {
            final StringBuilder builder = new StringBuilder("is");
            builder.append(Character.toUpperCase(pAttribute.charAt(0)));
            builder.append(pAttribute.substring(1));
            return builder.toString();
        } else {
            return "";
        }
    }
}
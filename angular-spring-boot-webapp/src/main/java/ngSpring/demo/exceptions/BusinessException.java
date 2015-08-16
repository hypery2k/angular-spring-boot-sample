package ngSpring.demo.exceptions;


/**
 * Common superclass for business exception in the application
 *
 * @author hypery2k
 */
@SuppressWarnings("serial")
public class BusinessException extends Exception {

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(Exception exception) {
        super(exception);
    }

    public BusinessException(String msg, Exception exception) {
        super(msg, exception);
    }
}

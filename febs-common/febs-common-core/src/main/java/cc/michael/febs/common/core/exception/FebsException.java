package cc.michael.febs.common.core.exception;

/**
 * FEBS系统异常
 *
 * @author michael
 */
public class FebsException extends Exception {

    private static final long serialVersionUID = -6916154462432027437L;

    public FebsException(String message) {
        super(message);
    }
}

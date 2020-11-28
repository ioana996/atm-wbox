package interview.wbox.atm.exception;

/**
 * Created by ioana on 11/28/2020.
 */
public class NoOptimalRepartitionException extends RuntimeException {
    public NoOptimalRepartitionException() {
        super("The ATM cannot return this sum");
    }
}

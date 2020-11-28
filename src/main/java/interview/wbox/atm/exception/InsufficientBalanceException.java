package interview.wbox.atm.exception;

/**
 * Created by ioana on 11/28/2020.
 */
public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException() {
        super("Insufficient Balance");
    }
}

package interview.wbox.atm.exception;

/**
 * Created by ioana on 11/28/2020.
 */
public class InvalidAmountException extends RuntimeException{

    public InvalidAmountException() {
        super("Invalid amount");
    }

}

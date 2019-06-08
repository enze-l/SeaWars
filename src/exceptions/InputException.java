package exceptions;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class InputException extends SeaWarException {
    public InputException() {
        super();
    }

    public InputException(String message){
        super(message);
    }

    public InputException(String message, Exception e){
        super(message, e);
    }
}

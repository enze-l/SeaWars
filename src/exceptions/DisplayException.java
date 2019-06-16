package exceptions;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class DisplayException extends SeaWarException{
    public DisplayException() {
        super();
    }

    public DisplayException(String message){
        super(message);
    }

    public DisplayException(String message, Exception e){
        super(message, e);
    }
}

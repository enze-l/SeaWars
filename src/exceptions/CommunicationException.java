package exceptions;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class CommunicationException extends SeaWarException {
    public CommunicationException() {
        super();
    }

    public CommunicationException(String message){
        super(message);
    }

    public CommunicationException(String message, Exception e){
        super(message, e);
    }
}

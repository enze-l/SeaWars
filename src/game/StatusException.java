package game;

public class StatusException extends SeaWarException {
    public StatusException() {
        super();
    }

    public StatusException(String message){
        super(message);
    }

    public StatusException(String message, Exception e){
        super(message, e);
    }
}

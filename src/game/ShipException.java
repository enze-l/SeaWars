package game;

public class ShipException extends SeaWarException {
    public ShipException() {
        super();
    }

    public ShipException(String message){
        super(message);
    }

    public ShipException(String message, Exception e){
        super(message, e);
    }
}

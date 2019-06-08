package exceptions;

public class SeaWarException extends Exception {
    public SeaWarException(){
        super();
    }

    public SeaWarException(String message){
        super(message);
    }

    public SeaWarException(String message, Exception e){
        super(message, e);
    }
}

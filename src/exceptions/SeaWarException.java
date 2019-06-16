package exceptions;

public class SeaWarException extends Exception {
    SeaWarException(){
        super();
    }

    SeaWarException(String message){
        super(message);
    }

    SeaWarException(String message, Exception e){
        super(message, e);
    }
}

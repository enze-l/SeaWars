package exceptions;

public class FieldException extends SeaWarException {
    public FieldException() {
        super();
    }

    public FieldException(String message){
        super(message);
    }

    public FieldException(String message, Exception e){
        super(message, e);
    }
}

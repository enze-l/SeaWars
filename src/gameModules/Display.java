package gameModules;

import exceptions.*;
import output.OutputImpl;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Display {
    private static OutputImpl outputInstance;

    private Display(){}

    /**
     * assignees the Display the output for the field
     * @param output the Output that should be used
     */
    static void initialize(OutputImpl output){
        outputInstance=output;
    }

    /**
     * Notification Method for changes on the board
     */
    public static void update(){
        try {
            whiteSpace();
            outputInstance.output();
        }catch (StatusException|InputException|IOException ignored){}
    }

    /**
     * Method for delivering an message as overlay to the user. Vanishes after 2 seconds agein
     * @param message the message that should be delivered
     */
    public static void displayMessage(String message){
        try {
            whiteSpace();
            outputInstance.output(message);
            Thread.sleep(2000);
            whiteSpace();
            outputInstance.output();
        }catch (StatusException|InputException|IOException|InterruptedException ignored){}
    }

    private static void whiteSpace(){
        System.out.printf("%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n");
    }
}

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

    static void initialize(OutputImpl output){
        outputInstance=output;
    }

    public static void update(){
        try {
            whiteSpace();
            outputInstance.output();
        }catch (StatusException|InputException|IOException ignored){}
    }

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

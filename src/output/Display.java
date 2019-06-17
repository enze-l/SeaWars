package output;

import exceptions.DisplayException;
import exceptions.InputException;
import exceptions.StatusException;
import gameModules.GameInstance;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Display {
    private static Output outputInstance;

    private Display(){}

    public static void initialize(Output output){
        outputInstance=output;
    }

    public static void update(){
        try {
            outputInstance.output();
        }catch (StatusException|InputException|IOException ignored){}
    }

    public static void displayMessage(String message){
        try {
            outputInstance.output(message);
            Thread.sleep(2000);
            outputInstance.output();
        }catch (StatusException|InputException|IOException|InterruptedException ignored){}
    }
}

package output;

import exceptions.DisplayException;
import exceptions.InputException;
import exceptions.StatusException;
import gameModules.GameInstance;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Notifiable {

    static void update() throws DisplayException{
        try {
            Output.output(GameInstance.getPlayerBoard(), GameInstance.getEnemyBoard());
        }catch (StatusException | InputException | IOException e){
            throw new DisplayException("An error occurred trying to display the board");
        }
    }
}

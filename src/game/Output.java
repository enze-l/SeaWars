package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Output {

    /**
     * Displays board of Player in the console-view
     */
    void outputMyBoard();

    /**
     * Displays enemy board in console-view
     */
    void outputEnemyBoard();

    /**
     * Displays available ships in console view
     */
    void outputShips();

    /**
     * Displays the commands that are available at the moment
     */
    void outputCommandsAvailable();
}

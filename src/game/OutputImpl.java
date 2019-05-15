package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class OutputImpl {

    /**
     * Displays given Board in the console-view
     */
    public static void outputBoard(FieldStatus[][] fields, GameStatus gameStatus) {
        for (int row=0; row<fields.length; row++) {
            for (int column=0; column<fields[row].length; column++) {
                System.out.print(OutputSymbols.getSymbol(fields[column][row])+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Displays available ships in console view
     */
    public static void outputShips(Ship[] ships) {

    }

    /**
     * Displays the commands that are available at the moment
     */
    public static void outputCommandsAvailable(GameStatus gameStatus) {

    }
}

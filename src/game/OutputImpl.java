package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class OutputImpl {

    /**
     * Displays given Board in the console-view
     */
    public static void outputMyBoard(MyBoard board) {
        FieldStatus[][] fields = board.getFields();
        //output numbers over board
        System.out.print(" ");
        for (int number = 1; number <= fields.length; number++) {
            System.out.print(" " + number);
        }
        //output letters and fields in a row at a time
        System.out.println();
        for (int row = 0; row < fields.length; row++) {
            System.out.print(OutputSymbols.getAlphabet(row + 1));
            for (int column = 0; column < fields[row].length; column++) {
                System.out.print(" " + OutputSymbols.getSymbol(fields[column][row]));
            }
            System.out.println(" " + OutputSymbols.getAlphabet(row + 1));
        }
        //output numbers below board
        System.out.print(" ");
        for (int number = 1; number <= fields.length; number++) {
            System.out.print(" " + number);
        }
        System.out.printf("%n%n");
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

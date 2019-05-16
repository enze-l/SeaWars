package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class OutputImpl {

    public static void output(MyBoard board) throws StatusException {
        outputShips(board.shipsAvailable());
        outputMyBoard(board);
    }

    /**
     * Displays given Board in the console-view
     */
    public static void outputMyBoard(MyBoard board) throws StatusException {
        FieldStatus[][] fields = board.getFields();
        GameStatus gameStatus = board.getStatus();
        //output numbers over board
        System.out.print("   ");
        for (int number = 1; number <= fields.length; number++) {
            System.out.print(" " + number);
        }
        System.out.println("    │");
        //output field-boarder over the board
        System.out.print("  ");
        System.out.print(OutputSymbols.fieldBoarder(BoarderPiece.UPPER_LEFT, gameStatus));
        for (int boarderPart = 0; boarderPart < fields.length * 2 + 1; boarderPart++) {
            System.out.print(OutputSymbols.fieldBoarder(BoarderPiece.HORRIZONTAL, gameStatus));
        }
        OutputSymbols.even = false;
        System.out.println(OutputSymbols.fieldBoarder(BoarderPiece.UPPER_RIGHT, gameStatus) + "   │");

        //output letters and fields in a row at a time
        for (int row = 0; row < fields.length; row++) {
            System.out.print(OutputSymbols.getAlphabet(row + 1) + " "
                    + OutputSymbols.fieldBoarder(BoarderPiece.VERTICAL, gameStatus));
            for (int column = 0; column < fields[row].length; column++) {
                System.out.print(" " + OutputSymbols.getSymbol(fields[column][row]));
            }
            System.out.println(" " + OutputSymbols.fieldBoarder(BoarderPiece.VERTICAL, gameStatus)
                    + " " + OutputSymbols.getAlphabet(row + 1) + " │");
        }
        //output field-boarder below the board
        System.out.print("  ");
        System.out.print(OutputSymbols.fieldBoarder(BoarderPiece.LOWER_LEFT, gameStatus));
        for (int boarderPart = 0; boarderPart < fields.length * 2 + 1; boarderPart++) {
            System.out.print(OutputSymbols.fieldBoarder(BoarderPiece.HORRIZONTAL, gameStatus));
        }
        OutputSymbols.even = false;
        System.out.println(OutputSymbols.fieldBoarder(BoarderPiece.LOWER_RIGHT, gameStatus) + "   │");
        //output numbers below board
        System.out.print("   ");
        for (int number = 1; number <= fields.length; number++) {
            System.out.print(" " + number);
        }
        System.out.println("    │");
        System.out.println("────────────────────────────┴────────────────────────────");
    }

    /**
     * Displays available ships in console view
     */
    public static void outputShips(int[] ships) {

    }

    /**
     * Displays the commands that are available at the moment
     */
    public static void outputCommandsAvailable(GameStatus gameStatus) {

    }
}

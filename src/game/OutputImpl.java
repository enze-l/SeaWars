package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class OutputImpl {

    public static void output(MyBoard board) throws StatusException {
        outputShips(board);
        outputMyBoard(board.getFields(), board.getStatus());
    }

    /**
     * Displays given Board in the console-view
     */
    public static void outputMyBoard(FieldStatus[][] fields, GameStatus gameStatus) throws StatusException {
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

    /*
    @Deprecated
    public static void outputShips(MyBoard board) {
        int[] ships = board.shipsAvailable();
        //first ship-line
        if (ships[3]>=4) System.out.println("▪▪");
        else System.out.println("▫▫");
        //second line
        if (ships[3]>=3) System.out.print("▪▪ ");
        else System.out.print("▫▫ ");
        if (ships[2]>=3) System.out.println("▪▪▪");
        else System.out.println("▫▫▫");
        //third line
        if (ships[3]>=2) System.out.print("▪▪ ");
        else System.out.print("▫▫ ");
        if (ships[2]>=2) System.out.print("▪▪▪ ");
        else System.out.print("▫▫▫ ");
        if (ships[1]>=2) System.out.println("▪▪▪▪");
        else System.out.println("▫▫▫▫");
        //fourth line
        if (ships[3]>=1) System.out.print("▪▪ ");
        else System.out.print("▫▫ ");
        if (ships[2]>=1) System.out.print("▪▪▪ ");
        else System.out.print("▫▫▫ ");
        if (ships[1]>=1) System.out.print("▪▪▪▪ ");
        else System.out.print("▫▫▫▫ ");
        if (ships[0]>=1) System.out.println("▪▪▪▪▪");
        else System.out.println("▫▫▫▫▫");
        System.out.println();
    }
    */
    public static void outputShips(MyBoard board) {
        int[] availabelOnes = board.shipsAvailable();
        Ship[] ships = board.getShips();
        //Battleship
        for (int battleship=ShipInfo.getAmount(ShipType.BATTLESHIP); battleship>0; battleship--){
            if (ships[battleship-1].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.BATTLESHIP)+1; cruiserpart++) {
                    System.out.print(OutputSymbols.getMiniSymbol(ships[battleship-1].getSegmentStatus(cruiserpart)));
                }
            }
        }
        System.out.println();
        //Cruiser
        for (int destroyer=ShipInfo.getAmount(ShipType.CRUISER); destroyer>0; destroyer--){
            if (ships[destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.CRUISER)+1; cruiserpart++) {
                    System.out.print(OutputSymbols.getMiniSymbol(ships[destroyer].getSegmentStatus(cruiserpart)));
                }
                System.out.print("  ");
            }
        }
        System.out.println();
        //Submarine
        for (int destroyer=ShipInfo.getAmount(ShipType.SUBMARINE); destroyer>0; destroyer--){
            if (ships[2+destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.SUBMARINE)+1; cruiserpart++) {
                    System.out.print(OutputSymbols.getMiniSymbol(ships[2 + destroyer].getSegmentStatus(cruiserpart)));
                }
                System.out.print("   ");
            }
        }
        System.out.println();
        //Destroyer
        for (int destroyer=ShipInfo.getAmount(ShipType.DESTROYER); destroyer>0; destroyer--){
            if (ships[5+destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.DESTROYER)+1; cruiserpart++) {
                    System.out.print(OutputSymbols.getMiniSymbol(ships[5 + destroyer].getSegmentStatus(cruiserpart)));
                }
                System.out.print("    ");
            }
        }
        System.out.println();

    }

    /**
     * Displays the commands that are available at the moment
     */
    public static void outputCommandsAvailable(GameStatus gameStatus) {

    }
}

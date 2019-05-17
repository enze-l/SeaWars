package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class OutputImpl {
    static int STANDARD_HALF_SIZE=28;

    public static void output(MyBoard board) throws StatusException {
        System.out.printf("%n%n%n%n%n");
        outputShips(board);
        outputMyBoard(board.getFields(), board.getStatus());
        outputCommandsAvailable(board.getStatus());
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

    public static void outputShips(MyBoard board) {
        StringBuilder ships=new StringBuilder();
        ships.append(toLeft("─ Player ──────────────")+'N'+toRight("────────────── Enemy ─"));
        ships.append(System.lineSeparator());
        ships.append(toLeft("B " + toStringBattleship(board))+'↑');
        ships.append(System.lineSeparator());
        ships.append(toLeft("C "+toStringCruiser(board),-4)+"W ←   → E");
        ships.append(System.lineSeparator());
        ships.append(toLeft("S "+toStringSubmarine(board))+"↓");
        ships.append(System.lineSeparator());
        ships.append(toLeft("D "+toStringDestroyer(board))+"S");
        ships.append(System.lineSeparator());
        ships.append(tDivider());
        ships.append(System.lineSeparator());
        System.out.print(ships.toString());
        /*
          ↑      ▲      ˄
        ←   →  ◄   ►  ˂   ˃
          ↓      ▼      ˅
         */
    }

    /**
     * Displays the commands that are available at the moment
     */
    public static void outputCommandsAvailable(GameStatus gameStatus) {
        StringBuilder commands=new StringBuilder();
        switch (gameStatus){
            case PREPARATION:
                commands.append("≡ legend");
                commands.append(System.lineSeparator());
                commands.append("˅ set (B/C/S/D) (A-J) (1-10) (N/E/S/W)");
                commands.append(System.lineSeparator());
                commands.append("˄ remove (A-J) (1-10)");
                commands.append(System.lineSeparator());
                commands.append("► ready");
                commands.append(System.lineSeparator());
                break;
            case READY:
                commands.append("≡ legend");
                commands.append(System.lineSeparator());
                commands.append(System.lineSeparator());
                commands.append(System.lineSeparator());
                commands.append("◄ revoke");
                commands.append(System.lineSeparator());
                break;
            case ATTACK:
                commands.append("≡ legend");
                commands.append(System.lineSeparator());
                commands.append("☼ shoot (A-J) (1-10)");
                commands.append(System.lineSeparator());
                commands.append("◦ last shots");
                commands.append(System.lineSeparator());
                commands.append(System.lineSeparator());
                break;
            case RECEIVE:
                commands.append("≡ legend");
                commands.append(System.lineSeparator());
                commands.append(System.lineSeparator());
                commands.append("◦ last shots");
                commands.append(System.lineSeparator());
                commands.append(System.lineSeparator());
                break;
            case OVER:
                commands.append("≡ legend");
                commands.append(System.lineSeparator());
                commands.append(System.lineSeparator());
                commands.append("◦ last shots");
                commands.append(System.lineSeparator());
                commands.append("→ continue");
                commands.append(System.lineSeparator());
                break;
        }
        System.out.print(commands.toString());
    }

    private static String standartDivider(){
        StringBuilder divider=new StringBuilder();
        for (int whitespace=STANDARD_HALF_SIZE; whitespace>0; whitespace--){
            divider.append("─");
        }
        return divider.toString()+"─"+divider.toString();
    }

    private static String xDivider(){
        StringBuilder divider=new StringBuilder();
        for (int whitespace=STANDARD_HALF_SIZE; whitespace>0; whitespace--){
            divider.append("─");
        }
        return divider.toString()+"┼"+divider.toString();
    }

    private static String tDivider(){
        StringBuilder divider=new StringBuilder();
        for (int whitespace=STANDARD_HALF_SIZE; whitespace>0; whitespace--){
            divider.append("─");
        }
        return divider.toString()+"┬"+divider.toString();
    }

    private static String toLeft(String inputString){
        StringBuilder outputString=new StringBuilder();
        outputString.append(inputString);
        for (int whitespace=STANDARD_HALF_SIZE-inputString.length(); whitespace>0; whitespace--){
            outputString.append(" ");
        }
        return outputString.toString();
    }

    private static String toLeft(String inputString, int plusWhitespace){
        StringBuilder outputString=new StringBuilder();
        outputString.append(inputString);
        for (int whitespace=STANDARD_HALF_SIZE-inputString.length()+plusWhitespace; whitespace>0; whitespace--){
            outputString.append(" ");
        }
        return outputString.toString();
    }

    private static String toRight(String inputString){
        StringBuilder outputString=new StringBuilder();
        for (int whitespace=STANDARD_HALF_SIZE-inputString.length(); whitespace>0; whitespace--){
            outputString.append(" ");
        }
        outputString.append(inputString);
        return outputString.toString();
    }

    private static String toRight(String inputString, int plusWhitespace){
        StringBuilder outputString=new StringBuilder();
        for (int whitespace=STANDARD_HALF_SIZE-inputString.length()+plusWhitespace; whitespace>0; whitespace--){
            outputString.append(" ");
        }
        outputString.append(inputString);
        return outputString.toString();
    }


    private static String toMiddle(String inputString){
        StringBuilder half=new StringBuilder();
        for (int whitespace=(STANDARD_HALF_SIZE-inputString.length())/2; whitespace>0; whitespace--){
            half.append(" ");
        }
        return half.toString()+inputString+half.toString();
    }

    private static String toStringBattleship(MyBoard board){
        StringBuilder battleshipString=new StringBuilder();
        Ship[] ships = board.getShips();
        //Battleship
        for (int battleship=ShipInfo.getAmount(ShipType.BATTLESHIP); battleship>0; battleship--){
            if (ships[battleship-1].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.BATTLESHIP)+1; cruiserpart++) {
                    battleshipString.append(OutputSymbols.getMiniSymbol(ships[battleship-1].getSegmentStatus(cruiserpart)));
                }
            }
        }
        return battleshipString.toString();
    }

    private static String toStringCruiser(MyBoard board){
        StringBuilder cruiserString=new StringBuilder();
        Ship[] ships = board.getShips();
        for (int destroyer=ShipInfo.getAmount(ShipType.CRUISER); destroyer>0; destroyer--){
            if (ships[destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.CRUISER)+1; cruiserpart++) {
                    cruiserString.append(OutputSymbols.getMiniSymbol(ships[destroyer].getSegmentStatus(cruiserpart)));
                }
                cruiserString.append("  ");
            }
        }
        return cruiserString.toString();
    }

    private static String toStringSubmarine(MyBoard board){
        StringBuilder submarineString=new StringBuilder();
        Ship[] ships = board.getShips();
        for (int destroyer=ShipInfo.getAmount(ShipType.SUBMARINE); destroyer>0; destroyer--){
            if (ships[2+destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.SUBMARINE)+1; cruiserpart++) {
                    submarineString.append(OutputSymbols.getMiniSymbol(ships[2 + destroyer].getSegmentStatus(cruiserpart)));
                }
                submarineString.append("   ");
            }
        }
        return submarineString.toString();
    }

    private static String toStringDestroyer(MyBoard board){
        StringBuilder destroyerString=new StringBuilder();
        Ship[] ships = board.getShips();
        for (int destroyer=ShipInfo.getAmount(ShipType.DESTROYER); destroyer>0; destroyer--){
            if (ships[5+destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.DESTROYER)+1; cruiserpart++) {
                    destroyerString.append(OutputSymbols.getMiniSymbol(ships[5 + destroyer].getSegmentStatus(cruiserpart)));
                }
                destroyerString.append("    ");
            }
        }
        return destroyerString.toString();
    }
}

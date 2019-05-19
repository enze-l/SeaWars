package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class OutputImpl {
    private static int STANDARD_HALF_SIZE=28;

    public static void output(MyBoard board) throws StatusException, IOException {
        StringBuilder output=new StringBuilder();
        BufferedReader boardSplitter =
                new BufferedReader(new StringReader(toStringMyBoard(board.getFields(), board.getStatus())));
        System.out.printf("%n%n%n%n%n");
        output.append(toStringShips(board))
                .append(tDivider());
        String split=boardSplitter.readLine();
        do {
            if (split.length()!=0)output.append(split)
                    .append(iDivider())
                    .append(System.lineSeparator());
            split=boardSplitter.readLine();
        }while(split!=null);
        output.append(lDivider())
                .append(toStringCommands(board.getStatus(), board.shipsAvailable()));
        System.out.println(output.toString());
    }

    /**
     * Displays given Board in the console-view
     */
    private static String toStringMyBoard(FieldStatus[][] fields, GameStatus gameStatus) {
        StringBuilder ownSide=new StringBuilder();
        //output numbers over board
        ownSide.append("   ");
        for (int number = 1; number <= fields.length; number++) {
            ownSide.append(" ").append(number);
        }
        ownSide.append("    ").append(System.lineSeparator());
        //output field-boarder over the board
        ownSide.append("  ");
        ownSide.append(OutputSymbols.fieldBoarder(BoarderPiece.UPPER_LEFT, gameStatus));
        for (int boarderPart = 0; boarderPart < fields.length * 2 + 1; boarderPart++) {
            ownSide.append(OutputSymbols.fieldBoarder(BoarderPiece.HORRIZONTAL, gameStatus));
        }
        OutputSymbols.even = false;
        ownSide.append(OutputSymbols.fieldBoarder(BoarderPiece.UPPER_RIGHT, gameStatus))
                .append("   ").append(System.lineSeparator());

        //output letters and fields in a row at a time
        for (int row = 0; row < fields.length; row++) {
            ownSide.append(OutputSymbols.getAlphabet(row + 1))
                    .append(" ")
                    .append(OutputSymbols.fieldBoarder(BoarderPiece.VERTICAL, gameStatus));
            for (int column = 0; column < fields[row].length; column++) {
                ownSide.append(" ")
                        .append(OutputSymbols.getSymbol(fields[column][row]));
            }
            ownSide.append(" ")
                    .append(OutputSymbols.fieldBoarder(BoarderPiece.VERTICAL, gameStatus))
                    .append(" ")
                    .append(OutputSymbols.getAlphabet(row + 1))
                    .append(" ")
                    .append(System.lineSeparator());
        }
        //output field-boarder below the board
        ownSide.append("  ");
        ownSide.append(OutputSymbols.fieldBoarder(BoarderPiece.LOWER_LEFT, gameStatus));
        for (int boarderPart = 0; boarderPart < fields.length * 2 + 1; boarderPart++) {
            ownSide.append(OutputSymbols.fieldBoarder(BoarderPiece.HORRIZONTAL, gameStatus));
        }
        OutputSymbols.even = false;
        ownSide.append(OutputSymbols.fieldBoarder(BoarderPiece.LOWER_RIGHT, gameStatus))
                .append("   ")
                .append(System.lineSeparator());
        //output numbers below board
        ownSide.append("   ");
        for (int number = 1; number <= fields.length; number++) {
            ownSide.append(" ")
                    .append(number);
        }
        ownSide.append("    ").append(System.lineSeparator());
        return ownSide.toString();
    }

    private static String toStringShips(MyBoard board) {
        return toLeft("─ Player ──────────────") + 'N' + toRight("────────────── Enemy ─") +
                System.lineSeparator() +
                toLeft("B " + toStringBattleship(board)) + '↑' +
                System.lineSeparator() +
                toLeft("C " + toStringCruiser(board), -4) + "W ←   → E" +
                System.lineSeparator() +
                toLeft("S " + toStringSubmarine(board)) + "↓" +
                System.lineSeparator() +
                toLeft("D " + toStringDestroyer(board)) + "S" +
                System.lineSeparator();
        /*
          ↑      ▲      ˄
        ←   →  ◄   ►  ˂   ˃
          ↓      ▼      ˅
         */
    }

    /**
     * Displays the commands that are available at the moment
     */
    private static String toStringCommands(GameStatus gameStatus, int[] shipsAvailable) {
        StringBuilder commands=new StringBuilder();
        switch (gameStatus){
            case PREPARATION:
                commands.append("≡ legend")
                        .append(System.lineSeparator())
                        .append("˅ set (B/C/S/D) (A-J) (1-10) (N/E/S/W)")
                        .append(System.lineSeparator())
                        .append("˄ remove (A-J) (1-10)")
                        .append(System.lineSeparator());
                if (Arrays.equals(shipsAvailable, new int[]{0, 0, 0, 0})) {
                    commands.append("► ready")
                            .append(System.lineSeparator());
                } else commands.append(System.lineSeparator()).append(System.lineSeparator());
                break;
            case READY:
                commands.append("≡ legend")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("◄ revoke")
                        .append(System.lineSeparator());
                break;
            case ATTACK:
                commands.append("≡ legend")
                        .append(System.lineSeparator())
                        .append("☼ shoot (A-J) (1-10)")
                        .append(System.lineSeparator())
                        .append("◦ last shots")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
                break;
            case RECEIVE:
                commands.append("≡ legend")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("◦ last shots")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
                break;
            case OVER:
                commands.append("≡ legend")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("◦ last shots")
                        .append(System.lineSeparator())
                        .append("→ continue")
                        .append(System.lineSeparator());
                break;
        }
       return commands.toString();
    }

    private static String standardDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("─".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"─"+divider.toString()+System.lineSeparator();
    }

    private static String xDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("─".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"┼"+divider.toString()+System.lineSeparator();
    }

    private static String tDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("─".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"┬"+divider.toString()+System.lineSeparator();
    }

    private static String lDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("─".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"┴"+divider.toString()+System.lineSeparator();
    }

    private static char iDivider(){
        return '│';
    }

    private static String toLeft(String inputString){
        return inputString +
                " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length()));
    }

    private static String toLeft(String inputString, int plusWhitespace){
        return inputString +
                " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length() + plusWhitespace));
    }

    private static String toRight(String inputString){
        return " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length())) +
                inputString;
    }

    private static String toRight(String inputString, int plusWhitespace){
        return " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length() + plusWhitespace)) +
                inputString;
    }


    private static String toMiddle(String inputString){
        StringBuilder half=new StringBuilder();
        half.append(" ".repeat(Math.max(0, (STANDARD_HALF_SIZE - inputString.length()) / 2)));
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

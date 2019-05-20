package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class OutputSymbols {
    public static boolean even = false;

    /**
     * Assigns every field-status a symbol
     *
     * @param fieldStatus status of which the corresponding symbols is asked for
     * @return corresponding representation as a char
     */
    public static char getSymbol(FieldStatus fieldStatus) {
        char fieldSymbol;
        switch (fieldStatus) {
            case WATER:
                fieldSymbol = '~';
                break;
            case SHOTWATER:
                fieldSymbol = '○';
                break;
            case SETSHIP:
                fieldSymbol = '■';
                break;
            case HIT:
                fieldSymbol = '●';
                break;
            case SUNK:
                fieldSymbol = '□';
                break;
            default:
                fieldSymbol = ' ';
                break;
        }
        return fieldSymbol;
    }

    public static char getMiniSymbol(FieldStatus fieldStatus){
        char fieldSymbol;
        switch (fieldStatus) {
            case SHIP:
                fieldSymbol = '▪';
                break;
            case SETSHIP:
                fieldSymbol = '▫';
                break;
            case HIT:
                fieldSymbol = '·';
                break;
            case SUNK:
            default:
                fieldSymbol = ' ';
                break;
        }
        return fieldSymbol;
    }

    /**
     * Assigns every vertical coordinate the corresponding representation as an letter
     *
     * @param number of which the corresponding letter is asked for
     * @return char that corresponds with the given number
     */
    public static char getAlphabet(int number) {
        char letter = 'x';
        switch (number) {
            case 1:
                letter = 'A';
                break;
            case 2:
                letter = 'B';
                break;
            case 3:
                letter = 'C';
                break;
            case 4:
                letter = 'D';
                break;
            case 5:
                letter = 'E';
                break;
            case 6:
                letter = 'F';
                break;
            case 7:
                letter = 'G';
                break;
            case 8:
                letter = 'H';
                break;
            case 9:
                letter = 'I';
                break;
            case 10:
                letter = 'J';
                break;
        }
        return letter;
    }

    public static char fieldBoarder(BoarderPiece piece, GameStatus status) {
        char symbol = 'x';
        switch (status) {
            case ATTACK:
                switch (piece) {
                    case VERTICAL:
                        symbol = '║';
                        break;
                    case HORRIZONTAL:
                        symbol = '═';
                        break;
                    case UPPER_LEFT:
                        symbol = '╔';
                        break;
                    case UPPER_RIGHT:
                        symbol = '╗';
                        break;
                    case LOWER_LEFT:
                        symbol = '╚';
                        break;
                    case LOWER_RIGHT:
                        symbol = '╝';
                        break;
                }
                break;
            case RECEIVE:
            case READY:
                switch (piece) {
                    case VERTICAL:
                        symbol = '│';
                        break;
                    case HORRIZONTAL:
                        symbol = '─';
                        break;
                    case UPPER_LEFT:
                        symbol = '┌';
                        break;
                    case UPPER_RIGHT:
                        symbol = '┐';
                        break;
                    case LOWER_LEFT:
                        symbol = '└';
                        break;
                    case LOWER_RIGHT:
                        symbol = '┘';
                        break;
                }
                break;
            case OVER:
            case PREPARATION:
                switch (piece) {
                    case VERTICAL:
                        symbol = '─';
                        break;
                    case HORRIZONTAL:
                        if (even) {
                            symbol = 'ǀ';
                            even = false;
                        } else {
                            symbol = ' ';
                            even = true;
                        }
                        break;
                    default:
                        symbol = ' ';
                        break;
                }
                break;
        }
        return symbol;
    }

}

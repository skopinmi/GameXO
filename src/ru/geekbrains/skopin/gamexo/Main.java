
package ru.geekbrains.skopin.gamexo;

import java.util.Random;

public class Main {
/*
    еще есть глюки ... и черная материя.
    вывод в консоль оставлен для отладки.
    X-O основная часть в Main

 */

    public static final int SIZE = 3;
    public static final int DOTS_TO_WIN = 3;
    public static final String DOT_EMPTY = "*";
    public static final String[][] gameField = new String [SIZE][SIZE];;
    public static final Random random = new Random();

    private static String  DotComp = "O";
    private static String DotHuman = "X";
    private static boolean myMove = true;
    private static boolean difficulty = true;
    public static int countOfHumanWin = 0;
    public static int countOfAIWin = 0;
    public static int countOfDraw = 0;


    public static void main(String[] args) {

        GameWindow gameWindow = GameWindow.createGameWindow();
        gameWindow.setVisible(true);
        newGameField(SIZE);
        do {
            playGame();
            setMyMove(true);
        } while (true);
    }
/*
    блок методов для общения с другими классами
 */

    public static void setMyMove(boolean myMove) {
        Main.myMove = myMove;
    }

    public static String getDotComp() {
        return DotComp;
    }

    public static void putInGameField (int x, int y) {
        gameField[y][x] = DotHuman;
    }
    public static String getGameField (int x, int y) {
        return gameField[y][x];
    }

    public static void newGameField (int SIZE) {
        for (int i = 0; i < SIZE; i++) {
            for (int ii = 0; ii < SIZE; ii++) {
                gameField [i] [ii] = DOT_EMPTY;
            }
        }
    }

    public static void setDotComp(String dotComp) {
        DotComp = dotComp;
    }

    public static void setDotHuman(String dotHuman) {
        DotHuman = dotHuman;
    }

    public static void setDifficulty(boolean difficulty) {
        Main.difficulty = difficulty;
    }

/*
    блок внутриклассовых методов
*/

    public static void playGame (){                             // последовательность ходов и оформление игры
        while (true) {
            printGameField(); // тут магия - без печати ничего не работает...
            if (!myMove) {
                if (isWin(DotHuman)) {
                    countOfHumanWin++;
                    MassageWindow.createMassageWindow("Вы выиграли " + countOfHumanWin + " раз.");
                    break;
                } else if (isGameFieldFull()) {
                    countOfDraw++;
                    MassageWindow.createMassageWindow("Ничья " + countOfDraw + " раз.");
                    break;
                }
                if (difficulty) {
                    moveOfAi();
                } else {
                    moveOfAi2();
                }
                printGameField();
                if (isWin(DotComp)) {
                    countOfAIWin++;
                    MassageWindow.createMassageWindow("Компьютер выиграл " + countOfAIWin + " раз.");
                    break;
                } else if (isGameFieldFull()) {
                    countOfDraw++;
                    MassageWindow.createMassageWindow("Ничья " + countOfDraw + " раз.");
                    break;
                }
            }
        }
        GameWindow.sayResult();
        printGameField();
        newGameField(SIZE);
    }

    public static boolean isGameFieldFull () {
        for (String [] a : gameField){
            for(String b : a) {
                if (b.equals(DOT_EMPTY)) return false;
            }
        }
        return true;
    }

    public static void printGameField () {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for(String a: gameField[i]) {
                System.out.print(a + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void moveOfAi (){
        int x;
        int y;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isSpaceValid( y, x ));
        gameField [y][x] = DotComp;
        GameWindow.setJButton(x, y);
        setMyMove(true);
        printGameField();
    }

    public static boolean isWin(String symbol) {

        int countDotDiagonal1 = 0;
        int countDotDiagonal2 = 0;
        int countDotVertical;
        int countDotHorizontal;
        for (int i = 0; i < SIZE; i++) {
            countDotVertical = 0;                    //  обнуление счетчиков вертикалей и диагоналей
            countDotHorizontal = 0;                  //
            for (int ii = 0; ii < SIZE; ii++) {
                if (gameField[i][ii].equals(symbol)) {
                    countDotHorizontal++;
                }
                if (gameField[ii][i].equals(symbol)) {
                    countDotVertical++;
                }
                if (countDotHorizontal == DOTS_TO_WIN || countDotVertical == DOTS_TO_WIN) {
                    return true; }
            }
            if(gameField[i][i].equals(symbol)) {
                countDotDiagonal1++;
            }
            if (gameField[i][gameField.length - 1 - i].equals(symbol)) {
                countDotDiagonal2++;
            }
            if (countDotDiagonal1 == DOTS_TO_WIN || countDotDiagonal2 == DOTS_TO_WIN) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSpaceValid ( int y, int x) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        return gameField[y][x].equals(DOT_EMPTY);
    }
/*
        "Интеллект"
*/

    public static void moveOfAi2 (){                        // интеллект предупреждение проигрыша по горизонтали и вертикали

        /* тут что - то надо делать ... */
        int x;
        int y;
        int xFirstMove = getXYFirstMove();                    // дополнительные координаты для увеличения уровня "ума"
        int yFirstMove = getXYFirstMove();                    //
        int yForMoveAttack = getHorizontal(DotComp);       // координата горизонтали предполагаемого выигрыша
        int xForMoveAttack = getVertical(DotComp);         // координата вертикали предполагаемого выигрыша
        int yForMoveProtection = getHorizontal(DotHuman);   // координата горизонтали предполагаемого проигрыша
        int xForMoveProtection = getVertical(DotHuman);     // координата вертикали предполагаемого проигрыша
        int xDiagonal1MoveAttack = getXYInDiagonal1(DotComp);           // координата Х в диагонали предполагаемого выигрыша
        int yDiagonal1MoveAttack = getXYInDiagonal1(DotComp);           // координата У в диагонали предполагаемого выигрыша
        int xDiagonal1Protection = getXYInDiagonal1(DotHuman);           // координата Х в диагонали предполагаемого проигрыша
        int yDiagonal1Protection = getXYInDiagonal1(DotHuman);            // координата У в диагонали предполагаемого проигрыша
        int xDiagonal2MoveAttack = getXInDiagonal2(DotComp);           // координата Х в диагонали предполагаемого выигрыша
        int yDiagonal2MoveAttack = getYInDiagonal2(DotComp);           // координата У в диагонали предполагаемого выигрыша
        int xDiagonal2Protection = getXInDiagonal2(DotHuman);           // координата Х в диагонали предполагаемого проигрыша
        int yDiagonal2Protection = getYInDiagonal2(DotHuman);            // координата У в диагонали предполагаемого проигрыша
        do {
            if (xDiagonal1MoveAttack != -1 && yDiagonal1MoveAttack != -1) {
                x = xDiagonal1MoveAttack;
                y = yDiagonal1MoveAttack;
            } else if (xDiagonal1Protection != -1 && yDiagonal1Protection != -1) {
                x = xDiagonal1Protection;
                y = yDiagonal1Protection;
            } else if (xDiagonal2MoveAttack != -1 && yDiagonal2MoveAttack != -1) {
                x = xDiagonal2MoveAttack;
                y = yDiagonal2MoveAttack;
            } else if (xDiagonal2Protection != -1 && yDiagonal2Protection != -1) {
                x = xDiagonal2Protection;
                y = yDiagonal2Protection;
            } else if (xForMoveAttack != -1 && yForMoveAttack == -1) {           // исключение случая когда образовалась "ВИЛКА" - заведомый проигрыш
                x = xForMoveAttack;                                              //
                y = random.nextInt(SIZE);
            } else if ( xForMoveAttack == -1 && yForMoveAttack != -1 ) {
                y = yForMoveAttack;
                x = random.nextInt(SIZE);
            } else if (xForMoveProtection != -1 && yForMoveProtection == -1) {    // исключение случая когда образовалась "ВИЛКА" - заведомый проигрыш
                x = xForMoveProtection;                                           //
                y = random.nextInt(SIZE);
            } else if (xForMoveProtection == -1 && yForMoveProtection != -1 ) {
                y = yForMoveProtection;
                x = random.nextInt(SIZE);
            } else if (xFirstMove != -1  && yFirstMove != -1) {
                x = xFirstMove;
                y = yFirstMove;
            } else {
                y = random.nextInt(SIZE);
                x = random.nextInt(SIZE);
            }
        } while (!isSpaceValid( y, x ));
        gameField [y][x] = DotComp;
        GameWindow.setJButton(x, y);
        printGameField();
        setMyMove(true);
    }

    public static boolean isHorizontalFull (int horizontal) {   // проверка горизонтали на полную заполненность
        int countFullSpace = 0;
        for (int i = 0; i < SIZE; i++) {
            if (!gameField[horizontal][i].equals(DOT_EMPTY)) {
                countFullSpace++;
                if (countFullSpace == SIZE) return true;
            }
        }
        return false;
    }

    public static boolean isVerticalFull (int vertical) {      // проверка вертикали на полную заполненность
        int countFullSpace = 0;
        for (int i = 0; i < SIZE; i++) {
            if (!gameField[i][vertical].equals(DOT_EMPTY)) {
                countFullSpace++;
                if (countFullSpace == SIZE) return true;
            }
        }
        return false;
    }

    public static int getVertical (String checkDot ){
        int xForMove = - 1;                               // координата вертикали  где проверяемых символов DOT_TO_WIN - 1
        for (int i = 0; i < SIZE; i++) {
            int countDotVertical = 0;                        // счет символов  по вертикалям
            for (int ii = 0; ii < SIZE; ii++) {
                if (gameField[ii][i].equals(checkDot)) {
                    countDotVertical++;
                }
                if (countDotVertical == (DOTS_TO_WIN - 1) && !isVerticalFull(i)) {     // символов в вертикаль на 1 меньше до победы
                    return i;                                                         //  + вертикаль не заполнена полностью
                }
                if (countDotVertical == (DOTS_TO_WIN - 2) && !isVerticalFull(i) && SIZE > 3) {     // символов в вертикаль на 2 меньше до победы
                    return i;                                                                      //  + вертикаль не заполнена полностью
                }
            }
        }
        return  xForMove;
    }

    public static int getHorizontal (String checkDot ){

        int yForMove = - 1;                               // координата горизонтали где проверяемых символов DOT_TO_WIN - 1
        for (int i = 0; i < SIZE; i++) {
            int countDotHorizontal = 0;                      // счет символов в горизонтали где проверяемых символов DOT_TO_WIN - 1
            for (int ii = 0; ii < SIZE; ii++) {
                if (gameField[i][ii].equals(checkDot)) {
                    countDotHorizontal++;
                }
                if (countDotHorizontal == (DOTS_TO_WIN - 1) && !isHorizontalFull(i)) { // символов  в горизонтали на 1 меньше
                    return i;                                                      // до  победы + горизонталь не заполнена полностью
                }
                if (countDotHorizontal == (DOTS_TO_WIN - 2) && !isHorizontalFull(i) && SIZE > 3) { // символов  в горизонтали на 2 меньше
                    return  i;                                                      // до  победы + горизонталь не заполнена полностью
                }
            }
        }
        return  yForMove;
    }

    public static int getXYInDiagonal1(String symbol){    // получение Х в диагонали

        if(isDiagonal1OneMoveToFull(symbol)) {
            for (int i = 0; i < SIZE; i++) {
                if (gameField[i][i].equals(DOT_EMPTY)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int getXInDiagonal2(String symbol) {
        if (isDiagonal2OneMoveToFull(symbol)) {
            for (int i = 0; i < SIZE; i++) {
                if (gameField[i][SIZE - 1 - i].equals(DOT_EMPTY)) {
                    return SIZE - 1 - i;
                }
            }
        }
        return -1;
    }

    public static int getYInDiagonal2(String symbol) {
        if (isDiagonal2OneMoveToFull(symbol)) {
            for (int i = 0; i < SIZE; i++) {
                if (gameField[i][SIZE - 1 - i].equals(DOT_EMPTY)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static boolean isDiagonal1OneMoveToFull (String symbol ) {  // есть ли в диагонали  символов DOT_TO_WIN - 1 и DOT_TO_WIN - 2 для размера > 3
        int countSymbols = 0;
        for (int i = 0; i < SIZE; i++) {
            if (gameField[i][i].equals(symbol)) {
                countSymbols ++;
            } else if (gameField[i][i] != DOT_EMPTY && SIZE > 3) {
                countSymbols = 0;
            }
            if (countSymbols == (DOTS_TO_WIN - 1) && !isDiagonal1Full()) {
                return true;
            }
            if (countSymbols == (DOTS_TO_WIN - 1) && !isDiagonal1Full() && SIZE < 3) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDiagonal2OneMoveToFull (String symbol) {  // есть ли в диагонали  символов DOT_TO_WIN - 1  и DOT_TO_WIN - 2 для размера > 3
        int countSymbols = 0;
        for (int i = 0; i < SIZE; i++) {
            if (gameField[i][SIZE - 1 - i].equals(symbol)) {
                countSymbols++;
            } else if (!gameField[i][SIZE - 1 - i].equals(DOT_EMPTY) && SIZE > 3) {
                countSymbols = 0;
            }
            if (countSymbols == (DOTS_TO_WIN - 1) && !isDiagonal2Full()) {
                return true;
            }
            if (countSymbols == (DOTS_TO_WIN - 2) && !isDiagonal2Full() && SIZE > 3) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDiagonal1Full () {                     // проверка диагонали 1 на полную заполненность
        int countFullSpace = 0;
        for (int i = 0; i < SIZE; i++) {

            if (!gameField[i][i].equals(DOT_EMPTY)) {
                countFullSpace++;
            }
            if (countFullSpace == SIZE) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDiagonal2Full () {                     // проверка диагонали 2 на полную заполненность
        int countFullSpace = 0;
        for (int i = 0; i < SIZE; i++) {
            if (!gameField[i][SIZE - 1 - i].equals(DOT_EMPTY)) {
                countFullSpace++;
            }
            if (countFullSpace == SIZE) return true;
        }
        return false;
    }

    public static int getXYFirstMove () {                       // определяет координаты первого хода AI + иногда срабатывает в дальнейшем
        // при тестировании выявлено "слабое" место в логике игры
        int xyCheck = (int ) SIZE / 2;                          // добавлена "хитрость" для первого хода
        if (gameField[xyCheck][xyCheck].equals(DOT_EMPTY))
        {                                                              // при пустующем центральном поле занимает его
            return xyCheck;
        }
        else if (gameField[0][0].equals(DOT_EMPTY) &&
                gameField[0][gameField.length - 1].equals(DOT_EMPTY) &&
                gameField[gameField.length - 1][0].equals(DOT_EMPTY) &&
                gameField[gameField.length - 1][gameField.length - 1].equals(DOT_EMPTY))
        {

            if (random.nextInt(2) == 0) {
                return 0;                                             // занимает угловые позиции поля
            } else {
                return (gameField.length - 1);
            }
        }
        return -1;
    }
}


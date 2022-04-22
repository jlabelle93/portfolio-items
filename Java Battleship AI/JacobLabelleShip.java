//Jacob Labelle COSC111-L04 Assignment 9 Battleship AI
public class JacobLabelleShip
{
    public static String makeGuess(char[][] guesses)
    {
        //Update Probability Board
        int[][] probBoard = new int[10][10];
        probability(guesses, probBoard);
        prevShots(guesses, probBoard);

        //printBoard(probBoard);
        //printBoard(guesses);

        //Decide Traversal Method for this game
        int travDir = 0;
        if(turnCount(guesses) == 0) {
            travDir = travMethod();
        }
        //Find Highest Probable Tile
        int[] highCoord = new int[2];
        if(travDir == 0) {
            highPriorityR(probBoard, highCoord);
        }
        else if(travDir == 1) {
            highPriorityF(probBoard, highCoord);
        }
        //Shoot at highest probability tile
        int row = highCoord[0];
        int col = highCoord[1];

        //Send it
        char a = (char)((int)'A' + row);
        String guess = a + Integer.toString(col+1);
        //System.out.println("------------------------");
        //System.out.println();
        return guess;
    }

    public static void highPriorityR(int[][] probBoard, int[] highCoord) {
        //Reverse Traversal
        //Go through, grab highest value on probBoard and put location into an array
        int high = 0;
        for(int row = probBoard.length-1; row >= 0; row--) {
            for(int col = probBoard.length-1; col >= 0; col--) {
                if(probBoard[row][col] > high) {
                    high = probBoard[row][col];
                    highCoord[0] = row;
                    highCoord[1] = col;
                }
            }
        }
    }

    public static void highPriorityF(int[][] probBoard, int[] highCoord) {
        //Traverse Forwards
        int high = 0;
        for(int row = 0; row < probBoard.length; row++) {
            for(int col = 0; col < probBoard.length; col++) {
                if(probBoard[row][col] > high) {
                    high = probBoard[row][col];
                    highCoord[0] = row;
                    highCoord[1] = col; 
                }
            }
        }
    }

    public static void prevShots(char[][] guesses, int[][] probBoard) {
        //Check the established probBoard against guesses for further manipulation
        for(int row = 0; row < probBoard.length; row++) {
            for(int col = 0; col < probBoard.length; col++) {
                //If value is a miss, or if a ship has been revealed to be sunk
                if(guesses[row][col] == 'O' || (guesses[row][col] >= '1' && guesses[row][col] <= '5')) {
                    probBoard[row][col] = -1;
                }
                //Edge of board check, and hit detection check
                if(guesses[row][col] == 'X') {
                    probBoard[row][col] = -1;
                    //Add to value of adjacent tiles, or revise them downward as appropriate
                    if(col < probBoard[row].length-1) {
                        if(guesses[row][col+1] == '.') {
                            probBoard[row][col+1] += 33;
                        }
                        else {
                            probBoard[row][col+1] = -1;
                        }
                    }
                    if(col < probBoard[row].length && col-1 >= 0) {
                        if(guesses[row][col-1] == '.') {
                            probBoard[row][col-1] += 33;
                        }
                        else {
                            probBoard[row][col-1] = -1;
                        }
                    }
                    if(row < probBoard.length-1) {
                        if(guesses[row+1][col] == '.') {
                            probBoard[row+1][col] += 33;
                        }
                        else {
                            probBoard[row+1][col] = -1;
                        }
                    }
                    if(row < probBoard.length && row-1 >= 0) {
                        if(guesses[row-1][col] == '.') {
                            probBoard[row-1][col] += 33;
                        }
                        else {
                            probBoard[row-1][col] = -1;
                        }
                    }
                    //Adjacent Hits Detection
                    if(col < probBoard.length-1) {
                        if(guesses[row][col] == 'X' && guesses[row][col+1] == 'X') {
                            if(col < probBoard.length-2 && guesses[row][col+2] == '.') {
                                probBoard[row][col+2] *= 100;
                            }
                            if(col-1 >= 0 && guesses[row][col-1] == '.') {
                                probBoard[row][col-1] *= 100;
                            }
                        }
                    }
                    if(row < probBoard.length-1) {
                        if(guesses[row][col] == 'X' && guesses[row+1][col] == 'X') {
                            if(row < probBoard.length-2 && guesses[row+2][col] == '.') {
                                probBoard[row+2][col] *= 100;
                            }
                            if(row-1 >= 0 && guesses[row-1][col] == '.' ) {
                                probBoard[row-1][col] *= 100;
                            }
                        } 
                    }
                }
            }
        }
    }

    public static void probability(char[][] guesses, int[][] probBoard) {
        //See if any ships are sunk, if they are set to true and ignore probability assessment for that ship
        boolean patSunk = false, subSunk = false, destSunk = false, battSunk = false, carrSunk = false;
        for(int row = 0; row < guesses.length; row++) {
            for(int col = 0; col < guesses[row].length; col++) {
                if(guesses[row][col] == '1') {
                    patSunk = true; 
                }
                if(guesses[row][col] == '2') {
                    subSunk = true; 
                }
                if(guesses[row][col] == '3') {
                    destSunk = true; 
                }
                if(guesses[row][col] == '4') {
                    battSunk = true; 
                }
                if(guesses[row][col] == '5') {
                    carrSunk = true; 
                }
            }
        }
        //Patrol Boat Probability
        if(patSunk == false) {
            for(int row = 0; row < probBoard.length; row++) {
                for(int col = 0; col < probBoard[row].length-1; col++) {
                    if(guesses[row][col] == '.' && guesses[row][col+1] == '.'){
                        probBoard[row][col]++;
                        probBoard[row][col+1]++;
                    }
                }
            }
            for(int row = 0; row < probBoard.length-1; row++) {
                for(int col = 0; col < probBoard[row].length; col++) {
                    if(guesses[row][col] == '.' && guesses[row+1][col] == '.'){
                        probBoard[row][col]++;
                        probBoard[row+1][col]++;
                    }
                }
            }
        }
        //Submarine Probability
        if(subSunk == false) {
            for(int row = 0; row < probBoard.length; row++) {
                for(int col = 0; col < probBoard[row].length-2; col++) {
                    if(guesses[row][col] == '.' && guesses[row][col+1] == '.' && guesses[row][col+2] == '.'){
                        probBoard[row][col]++;
                        probBoard[row][col+1]++;
                        probBoard[row][col+2]++;
                    }
                }
            }
            for(int row = 0; row < probBoard.length-2; row++) {
                for(int col = 0; col < probBoard[row].length; col++) {
                    if(guesses[row][col] == '.' && guesses[row+1][col] == '.' && guesses[row+2][col] == '.'){
                        probBoard[row][col]++;
                        probBoard[row+1][col]++;
                        probBoard[row+2][col]++;
                    }
                }
            }
        }
        //Destroyer Probability
        if(destSunk == false) {
            for(int row = 0; row < probBoard.length; row++) {
                for(int col = 0; col < probBoard[row].length-2; col++) {
                    if(guesses[row][col] == '.' && guesses[row][col+1] == '.' && guesses[row][col+2] == '.'){
                        probBoard[row][col]++;
                        probBoard[row][col+1]++;
                        probBoard[row][col+2]++;
                    }
                }
            }
            for(int row = 0; row < probBoard.length-2; row++) {
                for(int col = 0; col < probBoard[row].length; col++) {
                    if(guesses[row][col] == '.' && guesses[row+1][col] == '.' && guesses[row+2][col] == '.'){
                        probBoard[row][col]++;
                        probBoard[row+1][col]++;
                        probBoard[row+2][col]++;
                    }
                }
            }
        }
        //Battleship Probability
        if(battSunk == false) {
            for(int row = 0; row < probBoard.length; row++) {
                for(int col = 0; col < probBoard[row].length-3; col++) {
                    if(guesses[row][col] == '.' && guesses[row][col+1] == '.' && guesses[row][col+2] == '.' && guesses[row][col+3] == '.'){
                        probBoard[row][col]++;
                        probBoard[row][col+1]++;
                        probBoard[row][col+2]++;
                        probBoard[row][col+3]++;
                    }
                }
            }
            for(int row = 0; row < probBoard.length-3; row++) {
                for(int col = 0; col < probBoard[row].length; col++) {
                    if(guesses[row][col] == '.' && guesses[row+1][col] == '.' && guesses[row+2][col] == '.' && guesses[row+3][col] == '.'){
                        probBoard[row][col]++;
                        probBoard[row+1][col]++;
                        probBoard[row+2][col]++;
                        probBoard[row+3][col]++;
                    }
                }
            }
        }
        //Carrier Probability
        if(carrSunk == false) {
            for(int row = 0; row < probBoard.length; row++) {
                for(int col = 0; col < probBoard[row].length-4; col++) {
                    if(guesses[row][col] == '.' && guesses[row][col+1] == '.' && guesses[row][col+2] == '.' && guesses[row][col+3] == '.' && guesses[row][col+4] == '.'){
                        probBoard[row][col]++;
                        probBoard[row][col+1]++;
                        probBoard[row][col+2]++;
                        probBoard[row][col+3]++;
                        probBoard[row][col+4]++;
                    }
                }
            }
            for(int row = 0; row < probBoard.length-4; row++) {
                for(int col = 0; col < probBoard[row].length; col++) {
                    if(guesses[row][col] == '.' && guesses[row+1][col] == '.' && guesses[row+2][col] == '.' && guesses[row+3][col] == '.' && guesses[row+4][col] == '.'){
                        probBoard[row][col]++;
                        probBoard[row+1][col]++;
                        probBoard[row+2][col]++;
                        probBoard[row+3][col]++;
                        probBoard[row+4][col]++;
                    }
                }
            }
        }
    }

    public static int turnCount(char[][] guesses) {
        //Count the number of turns based on the character changes on guesses board
        int turn = 0;
        for(int row = 0; row < guesses.length; row++) {
            for(int col = 0; col < guesses[row].length; col++) {
                if(guesses[row][col] != '.') {
                    turn++; 
                }
            }
        }
        return turn;
    }

    public static int travMethod() {
        final int TRAVERSE = (int)(Math.random()*10)%2;
        return TRAVERSE;
    }

    public static void printBoard(int[][] probBoard) {
        //Print the probBoard
        for(int row = 0; row < probBoard.length; row++) {
            System.out.print((char)('A' + row) + " ");
            for(int col = 0; col < probBoard[row].length; col++) {
                System.out.print(probBoard[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printBoard(char[][] guesses) {
        //Print the guesses board
        for(int row = 0; row < guesses.length; row++) {
            System.out.print((char)('A' + row) + " ");
            for(int col = 0; col < guesses[row].length; col++) {
                System.out.print(guesses[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    } 
}
import processing.core.PApplet;

public class SudoBoard extends PApplet {
    int [][] sudoArray; // Numeric Array
    SudoButton [][] sudoButtonArray;
    SudoButton [][] numberArray;
    int boardSize; // whole board size
    int buttonSize; // each button size (boardsize/9)
    int x0, y0; // starting x,y  location of board

    public SudoBoard() {}

    public SudoBoard(int[][] sudoArray) {
        this.sudoArray = sudoArray;
    }

    public SudoBoard(PApplet pa, int [][] sudoArray,
                     int boardSize, int x0, int y0){
        this.sudoArray = sudoArray;
        this.boardSize = boardSize;
        this.x0 = x0;
        this.y0 = y0;
        this.buttonSize = boardSize/9;
        sudoButtonArray = new SudoButton[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                sudoButtonArray[r][c] =
                        new SudoButton(buttonSize,
                                x0+(c*buttonSize),
                                y0+(r*buttonSize),
                                sudoArray[r][c]);
            }
        }
    }

    //Creates the numberpad based on the SudoButton class.
    public void numberPad(PApplet pa, int [][] pad, int x0, int y0){
        numberArray = new SudoButton[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                numberArray[r][c] =
                        new SudoButton(buttonSize, x0+700+(c*buttonSize), y0+(3*buttonSize)+(r*buttonSize), pad[r][c]);
            }
        }
    }

    //Checks if the cell is legal or not.
    public void checkLegal(){
        for(int i = 0; i < 9; i++){
            for(int n = 0; n < 9; n++){
                if(isSafe(i, n, sudoArray[i][n]) && !sudoButtonArray[i][n].isConstant){
                    sudoButtonArray[i][n].isCorrect(true);
                }
                if(isSafe(i, n, sudoArray[i][n]) == false && !sudoButtonArray[i][n].isConstant){
                    sudoButtonArray[i][n].isCorrect(false);
                }
            }
        }
    }


    public void displayBoard(PApplet pa){
        pa.strokeWeight(1);
        for(SudoButton sbuttonArray []: sudoButtonArray){
            for(SudoButton sb: sbuttonArray){
                sb.display(pa);
            }
        }
        drawLines(pa);
    }

    //Displays the numberpad.
    public void displayPad(PApplet pa){
        pa.strokeWeight(1);
        for(SudoButton sbuttonArray2 []: numberArray){
            for(SudoButton b: sbuttonArray2){
                b.display(pa);
            }
        }
    }

    public void drawLines(PApplet pa){
        pa.strokeWeight(5);
        pa.line(x0-(buttonSize/2),y0-(buttonSize/2) + 3 * (buttonSize),
                x0-(buttonSize/2) + 9 * (buttonSize), y0-(buttonSize/2) + 3 * (buttonSize));
        pa.line(x0-(buttonSize/2),y0-(buttonSize/2) + 6 * (buttonSize),
                x0-(buttonSize/2) + 9 * (buttonSize), y0-(buttonSize/2) + 6 * (buttonSize));

        pa.line(x0-(buttonSize/2) + 3 * (buttonSize),y0-(buttonSize/2),
                x0-(buttonSize/2) + 3 * (buttonSize),y0-(buttonSize/2) + 9 * (buttonSize));
        pa.line(x0-(buttonSize/2) + 6 * (buttonSize),y0-(buttonSize/2),
                x0-(buttonSize/2) + 6 * (buttonSize),y0-(buttonSize/2) + 9 * (buttonSize));


    }


    // isSafe(r,c,n)
    public boolean isSafe(int row, int col, int n){
        // intersecting row
        for (int c = 0; c < 9; c++) {
            if(sudoArray[row][c] == n && c != col){ // c!=col essentially skips over the current cell row,col
                return false;
            }
        }
        // intersecting col
        for (int r = 0; r < 9; r++) {
            if(sudoArray[r][col] == n && r!=row){
                return false;
            }
        }

        // check 3x3 box of [row,col]
        int startR = 3 * (row/3);
        int startC = 3 * (col/3);
        for (int r =  startR; r < startR+3; r++) {
            for (int c = startC; c < startC+3; c++) {
                if(sudoArray[r][c] == n && r!=row && c!=col){
                    return false;
                }
            }
        }
        return true;
    }

    // sudoSolve() - starts solving the board at row,col
    public boolean sudoSolve(int row, int col){
        //base condition = if row == 8 AND col == 9
        if(row == 8 && col == 9)
            return true; // SOLVED@!!!
        else {
            if (col == 9) {
                row++;
                col = 0;
            }
            // check if SPOT is NOT ZERO (There's a pre-existing number)
            // if so - SOLVE the board starting at 1 column over:
            if (sudoArray[row][col] > 0) {
                return sudoSolve(row, col + 1);
            }
            // loop through numbers (N) from 1 to 9
            for (int N = 1; N <= 9; N++) {
                // check if N is safe @ row,col
                if (isSafe(row, col, N)) {

                    // Set [row, col] to N
                    sudoArray[row][col] = N;

                    // check if it can sudoSolve for row, col +1
                    if (sudoSolve(row, col + 1)) {
                        return true;
                    }
                    // reset [row,col] to 0; // BACK TRACK
                    sudoArray[row][col] = 0;
                }
            }
            return false;
        }
    }
}

import processing.core.PApplet;
public class SudoRunner extends PApplet{

    //GLOBAL variables
    SudoBoard puzzleBoard;
    FunctButton clearButton;
    FunctButton solveButton;
    FunctButton resetButton;
    FunctButton setButton;
    FunctButton generateBoard;
    FunctButton Difficulty;
    FunctButton easy;
    FunctButton medium;
    FunctButton hard;
    int numberOnFile = 0;

    public static void main(String[] args) {
        PApplet.main("SudoRunner", args);
    }

    public void settings(){
        size(1000,1000);
        int[][] puzzle = {
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 6, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 7, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 8, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 9}
        };

        int[][] pad = {
                {7, 8, 9},
                {4, 5, 6},
                {1, 2, 3}
        };

        puzzleBoard = new SudoBoard(this,puzzle,600,50,50);
        int spacing = puzzleBoard.buttonSize;
        clearButton = new FunctButton(2*spacing, spacing,width - 3*spacing, 50, "CLEAR");
        solveButton = new FunctButton(2*spacing, spacing,width - 3*spacing, 150, "SOLVE");
        resetButton = new FunctButton(2*spacing, spacing,width - 3*spacing, 475, "RESET");
        setButton = new FunctButton(2*spacing, spacing,width - 3*spacing, 575, "SET");
        Difficulty = new FunctButton(3*spacing, spacing,50+4*spacing, 80+9*spacing, "DIFFICULTY");
        easy = new FunctButton(2*spacing, spacing,50+spacing, 100+10*spacing, "EASY");
        medium = new FunctButton(2*spacing, spacing,50+4*spacing, 100+10*spacing, "MEDIUM");
        hard = new FunctButton(2*spacing, spacing,50+7*spacing, 100+10*spacing, "HARD");
        generateBoard = new FunctButton(2*spacing, spacing,width - 3*spacing, 675, "NEW");
        puzzleBoard.numberPad(this, pad, 50, 50);


    }
    public void setup(){
    }
    public void draw(){
        puzzleBoard.displayBoard(this);
        clearButton.display(this);
        solveButton.display(this);
        resetButton.display(this);
        setButton.display(this);
        generateBoard.display(this);
        Difficulty.display(this);
        easy.display(this);
        medium.display(this);
        hard.display(this);
        puzzleBoard.displayPad(this);
        puzzleBoard.checkLegal();
    }
    public void mouseReleased() {
        //Checks ever cell on the board
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                //Changes the number on the board based on selected number from the numberpad.
                if(puzzleBoard.sudoButtonArray[r][c].isOverButton(this)){
                    if(numberOnFile != 0){
                        puzzleBoard.sudoButtonArray[r][c].increaseNumber(numberOnFile);
                        puzzleBoard.sudoArray[r][c] = numberOnFile;
                        numberOnFile = 0;
                    }
                }
                //Updates each cell if the number is legal or not.
                if(!puzzleBoard.isSafe(r, c, puzzleBoard.sudoButtonArray[r][c].getNumber())){
                    if(puzzleBoard.sudoButtonArray[r][c].getNumber() != 0) {
                        puzzleBoard.sudoButtonArray[r][c].isCorrect(false);
                    }
                }
                else{
                    puzzleBoard.sudoButtonArray[r][c].isCorrect(true);
                }
            }
        }

        //Records the number selected from the number pad
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if(puzzleBoard.numberArray[r][c].isOverButton(this)){
                    numberOnFile = puzzleBoard.numberArray[r][c].number;
                }
            }
        }

        //The following checks for if any of the buttons are clicked
        if(clearButton.isOverButton(this)){
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if(!puzzleBoard.sudoButtonArray[r][c].isConstant){
                        puzzleBoard.sudoButtonArray[r][c].number = 0;
                        puzzleBoard.sudoArray[r][c] = 0;
                        puzzleBoard.sudoButtonArray[r][c].isSolved(false);
                    }
                }
            }
        }

        if(solveButton.isOverButton(this)){
            puzzleBoard.sudoSolve(0,0);
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    puzzleBoard.sudoButtonArray[r][c].number = puzzleBoard.sudoArray[r][c];
                    puzzleBoard.sudoButtonArray[r][c].isSolved(true);
                }
            }
        }

        if(resetButton.isOverButton(this)){
            for(int r = 0; r < 9; r++){
                for(int c = 0; c < 9; c++){
                    puzzleBoard.sudoButtonArray[r][c].number = 0;
                    puzzleBoard.sudoButtonArray[r][c].isConstant = false;
                    puzzleBoard.sudoArray[r][c] = 0;
                    puzzleBoard.sudoButtonArray[r][c].isSolved(false);
                }
            }
        }

        if(setButton.isOverButton(this)){
            for(int r = 0; r < 9; r++){
                for(int c = 0; c < 9; c++){
                    if(puzzleBoard.sudoButtonArray[r][c].number != 0){
                        puzzleBoard.sudoButtonArray[r][c].isConstant = true;
                    }
                }
            }
        }

        int howMany = 0;

        if(easy.isOverButton(this)){
            howMany = 24;
        }

        if(medium.isOverButton(this)){
            howMany = 16;
        }

        if(hard.isOverButton(this)){
            howMany = 8;
        }

        //The following is used by both the difficulty buttons and the generate button.
        if(howMany != 0 || generateBoard.isOverButton(this)){
            for(int r = 0; r < 9; r++){
                for(int c = 0; c < 9; c++){
                    puzzleBoard.sudoButtonArray[r][c].number = 0;
                    puzzleBoard.sudoButtonArray[r][c].isConstant = false;
                    puzzleBoard.sudoArray[r][c] = 0;
                    puzzleBoard.sudoButtonArray[r][c].isSolved(false);
                }
            }

            int number = 0;
            if(howMany > 0){
                number = howMany;
            }
            else{
                number = 12;
            }

            for(int i = 0; i < number; i++){
                int temp = 0;
                int xRow;
                int yCol;
                boolean isGood = false;

                xRow = (int)(Math.random()*9);
                yCol = (int)(Math.random()*9);
                while(temp < 9 && isGood != true){
                    if((int)(Math.random()*100)%2 == 0){
                        temp = (int)(Math.random()*9+1);
                    }
                    else{
                        temp++;
                    }

                    puzzleBoard.sudoArray[xRow][yCol] = temp;
                    puzzleBoard.sudoButtonArray[xRow][yCol].number = temp;
                    puzzleBoard.sudoButtonArray[xRow][yCol].isConstant = true;

                    if(temp != 0 && puzzleBoard.isSafe(xRow, yCol, temp) == true){
                        isGood = true;
                    }
                    else{
                        puzzleBoard.sudoArray[xRow][yCol] = 0;
                        puzzleBoard.sudoButtonArray[xRow][yCol].number = 0;
                        puzzleBoard.sudoButtonArray[xRow][yCol].isConstant = false;
                    }
                }
            }
        }
    }
}

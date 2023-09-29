import processing.core.PApplet;

public class SudoButton extends Button{

    public int number;

    public boolean isConstant;
    public boolean legal;
    public boolean win = false;

    public SudoButton(int size, int xPos, int yPos, int number) {
        super(size, size, xPos, yPos);
        this.number = number;
        if(number > 0){
            this.isConstant = true;
        }
        else{
            this.isConstant = false;
        }
    }

    public int getNumber(){
        return number;
    }
    public void increaseNumber(int numberOnFile){
        if(!isConstant) {
            number = numberOnFile;
        }
    }

    //Parameters that accept changes from other class.
    public void isCorrect(boolean legal){
        this.legal = legal;
    }
    public void isSolved(boolean win){
        this.win = win;
    }


    public void display(PApplet pa){
        // pa.rect(xPos,yPos, size, size);
        if (pa.mousePressed && isOverButton(pa)){
            pa.fill(255,0,0);
        }
        else{
            pa.fill(255,255,255);
        }

        pa.rect(xPos-(wSize/2),yPos-(hSize/2), wSize, hSize);
        pa.fill(0,0,0); // black text
        pa.textSize(hSize);
        pa.textAlign(pa.CENTER, pa.CENTER);
        if(!legal){
            pa.fill(255,0,0);
        }
        else{
            pa.fill(0, 0, 0);
        }
        //CHanges the color of the text based on the preconditions meet.
        if(isConstant)
            pa.fill(0,0,255);
        if(win && !isConstant)
            pa.fill(0,255,0);
        if(number>0)
            pa.text(number, xPos, yPos-hSize/5);

    }
}

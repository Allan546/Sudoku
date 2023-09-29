import processing.core.PApplet;

public class Button {
    public int wSize; //width
    public int hSize; //height
    public int xPos;
    public int yPos;
    public int number;

    public Button() {
    }
    public Button(int wSize, int hSize, int xPos, int yPos) {
        this.wSize = wSize;
        this.hSize = hSize;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Button(int wSize, int hSize, int xPos, int yPos, int number) {
        this.wSize = wSize;
        this.hSize = hSize;
        this.xPos = xPos;
        this.yPos = yPos;
        this.number = number;
    }

    public boolean isOverButton(PApplet pa){
        return (pa.mouseX >= xPos-wSize/2 && pa.mouseX <= xPos + wSize/2 &&
                pa.mouseY >= yPos-hSize/2 && pa.mouseY <= yPos + hSize/2);
    }

    public void display(PApplet pa){
        if (pa.mousePressed && isOverButton(pa)){
            pa.fill(255,0,0);
        }
        else{
            pa.fill(255,255,255);
        }
        pa.rect(xPos-(wSize/2),yPos-(hSize/2), wSize, hSize);
    }
}

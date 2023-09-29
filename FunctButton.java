import processing.core.PApplet;

public class FunctButton extends Button{
    public String text;

    public FunctButton(int wSize, int hSize, int xPos, int yPos, String text) {
        super(wSize, hSize, xPos, yPos);
        this.text = text;
    }


    public void display(PApplet pa){
        if (pa.mousePressed && isOverButton(pa)){
            pa.fill(255,0,0);
        }
        else{
            pa.fill(255,255,255);
        }
        pa.strokeWeight(0);
        pa.rect(xPos-(wSize/2),yPos-(hSize/2), wSize, hSize);
        pa.fill(0,0,0); // black text
        pa.textSize(hSize/2);
        pa.textAlign(pa.CENTER, pa.CENTER);
        pa.text(text, xPos, yPos);
    }
}

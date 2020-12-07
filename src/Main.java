import processing.core.PApplet;


public class Main extends PApplet {

    public static void main(String[] args) {

        // method for setting the size of the window
        PApplet.main("Main");
    }
    public void settings(){
        size(500, 500);
    }

    // identical use to setup in Processing IDE except for size()
    public void setup(){
        background(0);
        stroke(255);
        strokeWeight(10);
    }

    // identical use to draw in Prcessing IDE
    public void draw(){
        line(0, 0, 500, 500);
    }

    public void keyPressed() {
        if (key=='a') {
            fill(150,123,123);
            circle(100,100,50);
        }
    }
}


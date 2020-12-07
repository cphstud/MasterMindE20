import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Main extends PApplet {

    Drop drop;
    Catcher catcher;
    Drop[] drops;
    float maxRad, maxSpeed, mouseRad;
    int unit, numOfDrops, dropFrequency, dropCounter;

    public void setup() {

        numOfDrops=100;
        mouseRad = 8;
        maxRad = 70;
        maxSpeed = 4;
        dropFrequency = 30;
        dropCounter=10;
        drops = new Drop[numOfDrops];
        initArr();
        colorMode(HSB);
    }

    public void draw() {
        background(255);
        if (frameCount%dropFrequency == 0) {
            dropCounter++;
        }
        catcher.setPos(mouseX, mouseY);
        catcher.display();
        for (int i = 0; i<dropCounter; i++) {
            if (catcher.interSect(drops[i])) {
                println("done ..");
            }
            for (int j=0; j<dropCounter; j++) {
                if (j!=i) {
                    drops[i].interSect(drops[j]);
                }
            }
            drops[i].move();
            drops[i].display();
        }
    }

    public void initArr() {
        catcher = new Catcher();
        for (int i=0; i<numOfDrops; i++) {
            float rad=random(maxRad);
            int speed=PApplet.parseInt(random(1, maxSpeed));
            int col=PApplet.parseInt(random(255));
            float xPos=random(1, width-maxRad);
            float yPos=0;
            drop = new Drop(xPos, yPos, col, rad, speed);
            drops[i]=drop;
        }
    }
    class Catcher {
        // egenskaber
        float xPos;
        float yPos;
        int catcherColor;
        float catcherRadius;

        // konstruktør

        Catcher() {
            catcherRadius = 10;
            xPos = width/2;
            yPos = height - (2*catcherRadius);
        }

        public void setPos(float x, float y) {
            xPos=x;
            yPos=y;
        }

        // metoder

        public boolean interSect(Drop drop) {
            boolean inRange = false;
            float mDist = dist(xPos, yPos, drop.xPos, drop.yPos);
            if (mDist < (catcherRadius+drop.dropRadius)) {
                print(" in range ");
                inRange = true;
                if (catcherRadius > drop.dropRadius) {
                    catcherRadius = 10 + ((catcherRadius + 1)%100);
                    drop.suicide();
                } else {
                    catcherRadius--;
                }
                if (catcherRadius < 0) {
                    noLoop();
                }
                println(mDist + " w rad " + catcherRadius);
            }
            return inRange;
        }

        public void display() {
            colorMode(RGB);
            fill(120, 100, 100);
            circle(xPos, yPos, catcherRadius);
            colorMode(HSB);
        }
    }
    class Drop {
        // egenskaber
        float xPos;
        float yPos;
        int dropColor;
        float dropRadius;
        int dropSpeed;
        int dropSpeedX;
        int direction;

        // konstruktør
        Drop(float x, float y, int col, float rad, int speed) {
            xPos = x;
            yPos = y;
            dropColor = col;
            dropRadius = rad;
            dropSpeed = speed;
            direction = 1;
            dropSpeedX = 0;
        }
        Drop(float x, float y, int col, float rad, int speed, int speedX) {
            xPos = x;
            yPos = y;
            dropColor = col;
            dropRadius = rad;
            dropSpeed = speed;
            dropSpeedX = speedX;
        }
        // metoder

        public void move() {
            yPos=yPos+dropSpeed;
        }

        public void moveX() {
            xPos=xPos+(dropSpeedX*direction);
        }
        public void display() {
            fill(dropColor, 100, 100);
            circle(xPos, yPos, dropRadius);
        }
        //suicide()
        public void suicide() {
            xPos=width*10;
            yPos=height*10;
        }

        public boolean interSect(Drop drop) {
            boolean inRange = false;
            float mDist = dist(xPos, yPos, drop.xPos, drop.yPos);
            if (mDist < (dropRadius+drop.dropRadius)) {
                print(" in range ");
                inRange = true;
                direction=direction*-1;
            }
            return inRange;
        }
    }
    public void settings() {  size(800, 600); }
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "Main" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}

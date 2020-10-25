import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ball extends Rectangle{
    int xVelocity;
    int yVelocity;
    int initialSpeed = 2;

    public ball(int x, int y, int width, int height){
        super(x,y,width,height);

        Random random = new Random();
        int randomXDirection = random.nextInt(2);
        if(randomXDirection == 0) randomXDirection--;
        setXDirection(randomXDirection * initialSpeed);

        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 0) randomYDirection--;
        setYDirection(randomYDirection * initialSpeed);
    }

    public void setXDirection(int randXDir){
        xVelocity = randXDir;
    }

    public void setYDirection(int randYDir){
        yVelocity = randYDir;
    }

    public void move(){
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics graph){
        graph.setColor(Color.white);
        graph.fillOval(x,y,width, height);
    }
}

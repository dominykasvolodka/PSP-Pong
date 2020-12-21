import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle implements Movement {
    private static Ball instance;

    private int xVelocity;
    private int yVelocity;
    private int initialSpeed = 2;

    private Ball(int x, int y, int width, int height) {
        super(x, y, width, height);

        Random random = new Random();
        int randomXDirection = random.nextInt(2);
        if (randomXDirection == 0) randomXDirection--;
        setXDirection(randomXDirection * initialSpeed);

        int randomYDirection = random.nextInt(2);
        if (randomYDirection == 0) randomYDirection--;
        setYDirection(randomYDirection * initialSpeed);
    }

    public static Ball getInstance(int x, int y, int width, int height){
        if(instance == null){
            instance = new Ball(x,y,width,height);
        }

        return instance;
    }

    public void resetBall(int gameWidth, int gameHeight, int ballDiameter){
        Random random = new Random();
        x = (gameWidth / 2) - (ballDiameter / 2);
        y = random.nextInt(gameHeight - ballDiameter);
        setXDirection(initialSpeed);
        setYDirection(initialSpeed);
    }

    public int getXDirection(){
        return xVelocity;
    }

    public void setXDirection(int randXDir){
        xVelocity = randXDir;
    }

    public int getYDirection(){ return yVelocity; }

    public void setYDirection(int randYDir){
        yVelocity = randYDir;
    }

    public void invertYDirection(){
        if(yVelocity<0){
            yVelocity = Math.abs(yVelocity);
        }
        else{
            yVelocity = -yVelocity;
        }
    }

    @Override
    public void move(){
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics graph){
        graph.setColor(Color.white);
        graph.fillOval(x,y,width, height);
    }
}

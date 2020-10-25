import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class gameView extends JPanel implements Runnable{
    private final int ballDiameter = 20;
    private final int playerPlatformWidth = 10;
    private final int playerPlatformHeight = 100;
    private final int gameWidth = 1000;
    private final int gameHeight = (int)(gameWidth *(0.5555));
    private final Dimension screenSize = new Dimension(gameWidth, gameHeight);

    //Player scores
    private int player1Score;
    private int player2Score;
    private int winScore;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    player player1;
    player player2;
    ball ball;

    public gameView(){
        newPlayerPlatforms();
        startBall();
        winScore = 5;
        this.setFocusable(true);
        this.addKeyListener(new keyListener());
        this.setPreferredSize(screenSize);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startBall(){
        random = new Random();
        ball = new ball((gameWidth /2)-(ballDiameter /2),random.nextInt(gameHeight - ballDiameter), ballDiameter, ballDiameter);
    }

    public void newPlayerPlatforms(){
        player1 = new player(0,(gameHeight /2)-(playerPlatformHeight /2), playerPlatformWidth, playerPlatformHeight,1);
        player2 = new player(gameWidth - playerPlatformWidth,(gameHeight /2)-(playerPlatformHeight /2), playerPlatformWidth, playerPlatformHeight,2);
    }

    public void paint(Graphics graph){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        graph.drawImage(image,0,0,this);
    }

    public void draw(Graphics graphics){
        player1.draw(graphics);
        player2.draw(graphics);
        ball.draw(graphics);
        displayScore(graphics);
    }

    public void displayScore(Graphics graph){
        graph.setColor(Color.white);
        graph.setFont(new Font("Consolas",Font.PLAIN,60));
        graph.drawLine(gameWidth/2,0, gameWidth/2, gameHeight);
        graph.drawString(String.valueOf(player1Score),(gameWidth/2)-53, gameHeight/2);
        graph.drawString(String.valueOf(player2Score),(gameWidth/2)+20, gameHeight/2);

        if(player1Score==winScore){
            win(1);
        }
        if(player2Score==winScore){
            win(2);
        }
    }

    public void win(int id) {
        graphics.setColor(Color.white);
        graphics.fillRect((gameWidth/2)-232,(gameHeight/2)-90,gameWidth/2,gameHeight/4);
        graphics.setColor(Color.black);
        graphics.drawString("Player "+String.valueOf(id)+" won !",(gameWidth/2)-213,(gameHeight/2));
    }

    public void move(){
        player1.move();
        player2.move();
        ball.move();
    }

    public void collisionDetection(){
        //bounce ball off top & bottom window edges
        if(ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= gameHeight - ballDiameter) {
            ball.setYDirection(-ball.yVelocity);
        }

        //bounce ball off player platforms and increase speed
        if(ball.intersects(player1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if(ball.yVelocity > 0){
                ball.yVelocity++;
            }
            else{
                ball.yVelocity--;
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(ball.intersects(player2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if(ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //dont allow player platforms to exit window edges
        if(player1.y <= 0){
            player1.y = 0;
        }
        if(player1.y >= (gameHeight - playerPlatformHeight)){
            player1.y = gameHeight - playerPlatformHeight;
        }
        if(player2.y <= 0){
            player2.y = 0;
        }
        if(player2.y >= (gameHeight - playerPlatformHeight)){
            player2.y = gameHeight - playerPlatformHeight;
        }

        //append one point to player who scored
        if(ball.x <= 0) {
            player2Score++;
            startBall();
        }
        if(ball.x >= gameWidth - ballDiameter) {
            player1Score++;
            startBall();
        }
    }

    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double nanosec = 1000000000 / amountOfTicks;
        double delta = 0;

        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime)/nanosec;
            lastTime = now;
            if(delta >= 1){
                move();
                collisionDetection();
                repaint();
                delta--;
            }

            if(player1Score==winScore){
                break;
            }
            if(player2Score==winScore){
                break;
            }
        }
    }

    public class keyListener extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            player1.keyPressed(e);
            player2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e){
            player1.keyReleased(e);
            player2.keyReleased(e);
        }
    }
}

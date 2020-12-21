import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameTable extends JPanel implements Runnable, Movement {
    private final int ballDiameter = 20;
    private final int playerPlatformWidth = 10;
    private final int playerPlatformHeight = 100;
    private final int gameWidth = 1000;
    private final int gameHeight = (int) (gameWidth * (0.5555));
    private final Dimension screenSize = new Dimension(gameWidth, gameHeight);

    //Player scores
    private int player1Score;
    private int player2Score;
    private int winScore;

    private Thread gameThread;
    private Image image;
    private Graphics graphics;
    private Random random;
    private Player player1;
    private Player player2;
    private Ball ball;

    public GameTable() {
        newPlayerPlatforms();
        throwBall();
        winScore = 5;
        this.setFocusable(true);
        this.addKeyListener(new keyListener());
        this.setPreferredSize(screenSize);

        gameThread = new Thread(this);
        gameThread.start();
    }

    private void throwBall(){
        random = new Random();
        ball = Ball.getInstance((gameWidth / 2) - (ballDiameter / 2), random.nextInt(gameHeight - ballDiameter), ballDiameter, ballDiameter);
    }

    private void newPlayerPlatforms() {
        player1 = new Player(0, (gameHeight / 2) - (playerPlatformHeight / 2), playerPlatformWidth, playerPlatformHeight, 1);
        player2 = new Player(gameWidth - playerPlatformWidth, (gameHeight / 2) - (playerPlatformHeight / 2), playerPlatformWidth, playerPlatformHeight, 2);
    }

    public void paint(Graphics graph){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        graph.drawImage(image,0,0,this);
    }

    private void draw(Graphics graphics){
        player1.draw(graphics);
        player2.draw(graphics);
        ball.draw(graphics);
        displayScore(graphics);
    }

    private void displayScore(Graphics graph){
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

    private void win(int id) {
        graphics.setColor(Color.white);
        graphics.fillRect((gameWidth/2)-232,(gameHeight/2)-90,gameWidth/2,gameHeight/4);
        graphics.setColor(Color.black);
        graphics.drawString("Player "+String.valueOf(id)+" won !",(gameWidth/2)-213,(gameHeight/2));
    }

    @Override
    public void move(){
        player1.move();
        player2.move();
        ball.move();
    }

    private void collisionDetection(){
        //bounce ball off top & bottom window edges
        if(ball.y <= 0) {
            ball.invertYDirection();
        }
        if(ball.y >= gameHeight - ballDiameter) {
            ball.invertYDirection();
        }

        //bounce ball off player platforms and increase speed
        if(ball.intersects(player1)) {
            ball.setXDirection(Math.abs(ball.getXDirection()));
            ball.setXDirection(ball.getXDirection()+1);
            ball.setXDirection(ball.getXDirection());
            ball.setYDirection(ball.getYDirection());
        }
        if(ball.intersects(player2)) {
            ball.setXDirection(Math.abs(ball.getXDirection()));
            ball.setXDirection(ball.getXDirection()+1);
            ball.setXDirection(-ball.getXDirection());
            ball.setYDirection(ball.getYDirection());
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
            ball.resetBall(gameWidth,gameHeight,ballDiameter);
        }
        if(ball.x >= gameWidth - ballDiameter) {
            player1Score++;
            ball.resetBall(gameWidth,gameHeight,ballDiameter);
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

    private class keyListener extends KeyAdapter{
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

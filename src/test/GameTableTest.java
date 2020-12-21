import org.junit.*;

public class GameTableTest {

  @Test
  public void ballSingletonCheck(){
    Ball ball = Ball.getInstance(0,0,0,0);
    Ball ball2 = Ball.getInstance(10,10,10,10);

    Assert.assertEquals("Ball singleton design pattern failed",ball,ball2);
  }

  @Test
  public void ballCollisionWithUpAndBottom() {
    Ball ball = Ball.getInstance(1,1,1,1);
    ball.y = -1;
    int gameHeight = 1000;
    int ballDiameter = 50;

    int preTest = ball.getYDirection();
    if(ball.y <= 0) {
      ball.invertYDirection();
    }
    Assert.assertNotEquals("Y direction inversion when ball's y = -1 with game height = 1000 and ball diameter = 50",preTest,ball.getYDirection());

    ball.y = 951;
    preTest = ball.getYDirection();
    if(ball.y >= gameHeight - ballDiameter) {
      ball.invertYDirection();
    }
    Assert.assertNotEquals("Y direction inversion when ball's y = -951 with game height = 1000 and ball diameter = 50",preTest,ball.getYDirection());
  }

  @Test
  public void ballCollisionWithPlayerPlatforms(){
    int gameWidth = 1000;
    int gameHeight = (int) (gameWidth * (0.5555));
    Ball ball = Ball.getInstance(0,gameHeight/2,2,2);

    int playerPlatformWidth = 10;
    int playerPlatformHeight = 100;
    Player player = new Player(0, (gameHeight / 2) - (playerPlatformHeight / 2), playerPlatformWidth, playerPlatformHeight, 1);

    int preTest = ball.getXDirection();
    if(ball.intersects(player)) {
      ball.setXDirection(-ball.getXDirection());
    }
    Assert.assertNotEquals("X direction inversion when ball's x = 0 and y = "+gameHeight/2+"",preTest,ball.getXDirection());
  }
}
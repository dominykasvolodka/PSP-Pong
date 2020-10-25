import java.awt.*;
import javax.swing.*;

public class gameFrame extends JFrame{
    gameView gameView;

    public gameFrame(){
        gameView gameView = new gameView();
        this.add(gameView);
        this.setTitle("Pong");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

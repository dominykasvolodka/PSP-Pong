import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        GameTable gameTable = new GameTable();
        this.add(gameTable);
        this.setTitle("Pong");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {

    GamePanelAI panelAI;
    GamePanel panel;
    
    GameFrame(boolean isAi, int value, int value2) {
        if (isAi) {
            panelAI = new GamePanelAI(value, value2);
            this.add(panelAI, BorderLayout.CENTER);
        } 
        else {
            panel = new GamePanel(value);
            this.add(panel, BorderLayout.CENTER);
        }
        this.setTitle("Ping Pong!");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
}

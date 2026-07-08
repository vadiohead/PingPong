import java.awt.*;

@SuppressWarnings("serial")
public class Score extends Rectangle {

    protected int gameWidth;
    protected int gameHeight;
    protected int player1;
    protected int player2;

    Score(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }
    public void reset() {
        player1 = 0;
        player2 = 0;
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 60));

        // draw center line
        g.drawLine(gameWidth / 2, 0, gameWidth / 2, gameHeight);

        // draw scores
        String score1 = String.format("%02d", player1);
        String score2 = String.format("%02d", player2);

        int yOffset = 60; // adjust to center scores vertically
        g.drawString(score1, (gameWidth / 2) - 85, yOffset);
        g.drawString(score2, (gameWidth / 2) + 20, yOffset);
    }
}

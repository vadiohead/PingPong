import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Paddle extends Rectangle {

    int id;
    int yVelocity;
    int speed = 13;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.id = id;
    }

    public void keyPressed(KeyEvent e) {
        if (id == 1) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                setYDirection(-speed);
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                setYDirection(speed);
            }
        } else if (id == 2) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                setYDirection(-speed);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                setYDirection(speed);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (id == 1) {
            if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
                setYDirection(0);
            }
        } else if (id == 2) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                setYDirection(0);
            }
        }
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move(int gameHeight) {
        y += yVelocity;
        if (y < 0) y = 0;
        if (y > gameHeight - height) y = gameHeight - height;
    }

    public void moveAI(int gameHeight, int targetY) {
        if (id == 2) {  // bot control
            if (y + height / 2 < targetY) {
                yVelocity = speed;
            } 
            else if (y + height / 2 > targetY) {
                yVelocity = -speed;
            } 
            else {
                yVelocity = 0;
            }
        }
        y += yVelocity;

        // boundary constraints
        if (y < 0) y = 0;
        if (y > gameHeight - height) y = gameHeight - height;
    }

    public void draw(Graphics g) {
        if (id == 1)
            g.setColor(Color.blue);
        else
            g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }
}

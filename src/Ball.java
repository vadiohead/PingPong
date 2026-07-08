import java.awt.*;
import java.util.*;

@SuppressWarnings("serial")
public class Ball extends Rectangle {

    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 5;
    int maxSpeed = 13;

    Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        random = new Random();
        setXDirection(random.nextBoolean() ? 1 : -1);
        setYDirection(random.nextBoolean() ? 1 : -1);
        xVelocity *= initialSpeed;
        yVelocity *= initialSpeed;
    }

    public void setXDirection(int randomXDirection) {
        xVelocity = Math.max(-maxSpeed, Math.min(maxSpeed, randomXDirection));
    }

    public void setYDirection(int randomYDirection) {
        yVelocity = Math.max(-maxSpeed, Math.min(maxSpeed, randomYDirection));
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }
}

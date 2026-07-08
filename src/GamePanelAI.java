import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GamePanelAI extends JPanel implements Runnable, ActionListener {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    Thread gameThread;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    boolean gameOver = false;

    JPanel endScreen;
    JButton mainMenu;
    JButton restart;
    
    int points = 5;
    int difficulty = 1;
    double randomReactionTime;

    GamePanelAI(int value, int value2) {
    	newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new KeyListener());
        this.setPreferredSize(SCREEN_SIZE);
        this.setLayout(null);
        
        if(value < 0) {
        	points = value * -1;
        }
        else if(value == 0) {
        	points = value + 1;
        }
        else {
        	points = value;
        }
        difficulty = value2;
        System.out.println("First to "+points);
        System.out.println("AI difficulty: "+difficulty);
        adjustAIDifficulty();

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        Random random = new Random();
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2),
                random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2),
                PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2),
                PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }
    
    public void adjustAIDifficulty() {
    	Random random = new Random();
    	switch(difficulty) {
    	case 0: 
            randomReactionTime = 1.1 + (random.nextDouble() * (1.4 - 1.1));
    		break;
    	case 1:
            randomReactionTime = 1.3 + (random.nextDouble() * (1.6 - 1.3));
    		break;
    	case 2:
    		randomReactionTime = 1.5 + (random.nextDouble() * (1.8 - 1.5));
    		break;
    	}
    }

    public void checkScore() {
        if(score.player1 == points) {
            gameOver = true;
            showEndScreen();
            
        }
        else if(score.player2 == points) {
            gameOver = true;
            showEndScreen();
            
        }
    }

    public void showEndScreen() {
    	endScreen = new JPanel();
        endScreen.setBounds(200, 150, 600, 300);
        endScreen.setBackground(Color.GREEN);
        endScreen.setLayout(new GridLayout(2, 1, 10, 10));

        restart = new JButton("Restart");
        restart.setFocusable(true);
        restart.addActionListener(this);
        restart.setFont(new Font("Consolas", Font.PLAIN, 24));
        restart.setBackground(Color.GREEN);
        restart.setForeground(Color.BLACK);
        endScreen.add(restart);

        mainMenu = new JButton("Main Menu");
        mainMenu.setFocusable(true);
        mainMenu.addActionListener(this);
        mainMenu.setFont(new Font("Consolas", Font.PLAIN, 24));
        mainMenu.setBackground(Color.GREEN);
        mainMenu.setForeground(Color.BLACK);
        endScreen.add(mainMenu);
        
        if(score.player1 > score.player2) {
        	System.out.println("You won!");
        }
        else {
        	System.out.println("You lost!");
        }
        this.add(endScreen);
        endScreen.revalidate();
        endScreen.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restart) {
        	this.remove(endScreen);
            score.reset();
            newPaddles();
            newBall();
            gameOver = false;
            gameThread = new Thread(this);
            gameThread.start();
        }
        else if (e.getSource() == mainMenu) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose(); // close current game window
            new Main();
        }
    }

    public void paint(Graphics g) {
        Image image = createImage(getWidth(), getHeight());
        Graphics graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.paint(g);
        
        // AI reaction line
        int triggerLineX = (int) (GAME_WIDTH / randomReactionTime);
        g.setColor(new Color(255, 0, 0, 100));
        g.drawLine(triggerLineX, 0, triggerLineX, GAME_HEIGHT);
    }

    public void move() {
        paddle1.move(GAME_HEIGHT); // player-controlled paddle moves as usual

        // AI-controlled paddle logic
        if (ball.x > GAME_WIDTH / randomReactionTime) { // ball is on paddle2's side
            if (paddle2.y + (PADDLE_HEIGHT / randomReactionTime) < ball.y) {
                int targetY = ball.y + ball.height / 2; // bot targets the ball's center
                paddle2.moveAI(GAME_HEIGHT, targetY); // apply movement to AI paddle
                paddle2.setYDirection(paddle2.speed); // move down
            } 
            else if (paddle2.y + (PADDLE_HEIGHT / randomReactionTime) > ball.y) {
                int targetY = ball.y + ball.height / 2; // bot targets the ball's center
                paddle2.moveAI(GAME_HEIGHT, targetY); // apply movement to AI paddle
                paddle2.setYDirection(-paddle2.speed); // move up
            } 
            else {
                paddle2.setYDirection(0); // stay still
            }
        } 
        else {
            paddle2.setYDirection(0); // stop AI paddle when ball is on the other side
        }
        ball.move();
    }

    public void checkCollision() {
        // bounce ball off top & bottom window edges
        if (ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }

        // bounce ball off paddles
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if (ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if (ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if (ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
            
            adjustAIDifficulty(); // randomly move the invisible line
        }

        // stops paddles at window edges
        if (paddle1.y <= 0)
            paddle1.y = 0;
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddle2.y <= 0)
            paddle2.y = 0;
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;

        // Player 1 or Player 2 scores a point
        if (ball.x <= 0) {
            score.player2++;
            System.out.println("Player 2: " + score.player2);
            newPaddles();
            newBall();
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.player1++;
            System.out.println("Player 1: " + score.player1);
            newPaddles();
            newBall();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (gameOver != true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                checkScore();
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class KeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
        }
    }
}
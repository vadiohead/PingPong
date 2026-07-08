import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings({"serial", "unchecked", "rawtypes"})
public class Main extends JFrame implements ActionListener {
	
	public static void main(String[] args) {
		
		/*
		 * Created by Vadim Khakhanov
		 * Inspired by Bro Code's Pong Game
		 */
		
        new Main();
    }
	
	Image bgImg;
	String bg = "Resources\\bg.jpg";
	BackgroundPanel bgPanel;
    
    JButton playVsAI = new JButton();
    JButton playVsPlayer = new JButton();
    
    JSpinner gameLength;
    int points = 2;
	JComboBox comboBox;
	int difficulty = 1;
	JLabel label;
    String[] AIDifficulties = {"AI: Easy", "AI: Medium", "AI: Hard"};

    Main() {
    	bgImg = new ImageIcon(bg).getImage();
    	bgPanel = new BackgroundPanel();
    	
    	playVsAI.setText("Play vs AI");
        playVsAI.setBounds(150, 160, 300, 40);
        playVsAI.setBackground(Color.GREEN);
        playVsAI.setForeground(Color.BLACK);
        playVsAI.setFont(new Font("Consolas", Font.PLAIN, 14));
        playVsAI.addActionListener(this);
        
        comboBox = new JComboBox(AIDifficulties);
        comboBox.setBounds(460, 170, 160, 20);
        comboBox.setEditable(false);
        comboBox.addActionListener(this);
        
        gameLength = new JSpinner();
        gameLength.setValue(points);
        gameLength.setBounds(100, 200, 40, 40);
        gameLength.addChangeListener(new ChangeListener() {
        	@Override
        	public void stateChanged(ChangeEvent e) {
        		points = (int) gameLength.getValue();
        	}
        });
        
        label = new JLabel("First to: ");
        label.setBounds(20, 180, 200, 80);
        label.setFont(new Font("Consolas", Font.PLAIN, 14));
        
        playVsPlayer.setText("Play vs Player");
        playVsPlayer.setBounds(150, 220, 300, 40);
        playVsPlayer.setBackground(Color.GREEN);
        playVsPlayer.setForeground(Color.BLACK);
        playVsPlayer.setFont(new Font("Consolas", Font.PLAIN, 14));
        playVsPlayer.addActionListener(this);

        this.setContentPane(bgPanel);
        bgPanel.add(playVsAI);
        bgPanel.add(comboBox);
        bgPanel.add(playVsPlayer);
        bgPanel.add(gameLength);
        bgPanel.add(label);
        
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(640, 480);
        
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent act) {
        if(act.getSource() == comboBox) {
        	difficulty = comboBox.getSelectedIndex();
        }
        else if (act.getSource() == playVsAI) {
            new GameFrame(true, points, difficulty);
            this.setVisible(false);
        } 
        else if (act.getSource() == playVsPlayer) {
            new GameFrame(false, points, difficulty);
            this.setVisible(false);
        }
    }
    
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bgImg != null) {
                g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    
}

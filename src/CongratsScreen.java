import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CongratsScreen {
	
	private JFrame frame;
	private JPanel panel;
	private Picture pic;
	private Graphics2D graphics;
	private int width, height;
	
	public CongratsScreen(int w, int h, String winner) {
		makeFrame(w, h);	
		width = w;
		height = h;
		setUpGraphics();
		drawConfetti();
		makeCongrats(winner);
		frame.setContentPane(panel);
		frame.setLocation(300, 200);
		frame.setVisible(true);
	}
	
	public void makeFrame(int w, int h) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(w, h));
		frame.setTitle("Winner!");
		
		panel = new JPanel();
		panel.setLayout(null);
	}
	
	public void setUpGraphics() {
		pic = new Picture(500, 500);
		graphics = (Graphics2D) pic.getGraphics();
		ImageIcon image = new ImageIcon(pic.getImage());
		JLabel labelWithImage = new JLabel(image);
		labelWithImage.setBounds(0, 0, width, height);
		labelWithImage.setVisible(true);
		panel.add(labelWithImage);
	}
	
	public void drawConfetti() {	
		Color[] colors = {new Color(255, 40, 40), new Color(237, 133, 40), new Color(244, 226, 21),
				new Color(147, 226, 21), new Color(147, 226, 241), new Color(147, 116, 241)};	
		for (int i = 0; i < 500; i++) {
			int randomColorIndex = (int)((Math.random() * colors.length));
			graphics.setColor(colors[randomColorIndex]);
			double randomAngle = (Math.random());
			graphics.rotate(randomAngle);
			int randomX = (int)((Math.random() * width));
			int randomY = (int)((Math.random() * height));
			graphics.fillRect(randomX, randomY, 10, 5);
		}
		
	}
	
	public void makeCongrats(String playerName) {
		Graphics graphics = pic.getGraphics();
		graphics.setColor(Grid.BLACK);
		graphics.setFont(new Font("SansSerif", Font.PLAIN, 40));
		graphics.drawString(playerName, width / 3, 200);
		graphics.drawString("has won!", width / 3, 250);
	}
}

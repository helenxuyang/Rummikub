
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InstructionScreen implements ActionListener {

	private JFrame frame;
	private JPanel panel;
	private int width, height;
	private String pathStart = "RummikubPics\\";
	private String[] imagePaths = {"consecDemo.PNG", "sameDemo.PNG", "smileys.PNG"};
	private String[] instructions = {"<html>Form runs of at least 3 tiles <br> with the same color <br> and consecutive numbers,</html>",
			"<html>or with different colors <br> and the same number.</html>",
			"<html>The smiley tiles can be used <br> as tiles of any number and color.</html>"};
	
	public InstructionScreen(int w, int h) {
		width = w;
		height = h;
		makeFrame(w, h);
		makeTitle();
		for (int i = 0; i < imagePaths.length; i++) {
			makeImage(pathStart + imagePaths[i], 0, 100 + (i * 75), 200, 75);
			makeLabel(instructions[i], 225, 80 + (i * 75), 400, 100);
		}

		makeLabel("<html>On your first move, you can only use your own tiles"
				+ " and you have to form a run or runs where the sum of the tile numbers"
				+ " is greater than or equal to 30.</html>", 20, 350, 450, 60);
		makeLabel("<html>Afterwards, you can add to or modify runs on the board"
				+ " as long as all the runs you make are valid.</html>", 20, 420, 450, 50);
		makeLabel("The first player to get rid of all their tiles wins!", 75, 500, 400, 25);
		makeButton();
		frame.setContentPane(panel);
		frame.setLocation(300, 200);
		frame.setVisible(true);
	}
	
	public void makeFrame(int w, int h) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(w, h));
		frame.setTitle("Instructions");
		panel = new JPanel();
		panel.setBackground(Grid.WHITE);
		panel.setLayout(null);

	}
	
	public void makeTitle() {
		JLabel title = new JLabel("How to play Rummikub");
		title.setFont(new Font(title.getName(), Font.PLAIN, 24));
		title.setBounds(width/5 + 20, 10, width, 100);	
		panel.add(title);
	}
	
	public void makeLabel(String text, int x, int y, int w, int h) {
		JLabel label = new JLabel(text);
		label.setFont(new Font(label.getName(), Font.PLAIN, 16));
		label.setBounds(x, y, w, h);
		panel.add(label);
	}

	public void makeImage(String path, int x, int y, int w, int h) {
		ImageIcon image;
		Picture pic = new Picture(path);
		image = new ImageIcon(pic.getImage());
		JLabel labelWithImage = new JLabel(image);
		labelWithImage.setBounds(x, y, w, h);
		labelWithImage.setVisible(true);
		panel.add(labelWithImage);
	}
	
	public void makeButton() {
		JButton button = new JButton("Done");
		button.addActionListener(this);
		button.setBounds(175, 550, width / 4, 50);
		panel.add(button);
	}

	public void makeTitleScreen() {
		frame.dispose();
		TitleScreen t = new TitleScreen(500, 500);
	}
	
	public void actionPerformed(ActionEvent e) {
		makeTitleScreen();
	}
}

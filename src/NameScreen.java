import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NameScreen implements ActionListener {

	private JFrame frame;
	private JPanel panel;
	private JTextField[] textboxes;
	private String[] names;
	private int numPlayers;
	private int width, height;

	public NameScreen(int h, int w, int num) {
		width = w;
		height = h;
		numPlayers = num;
		makeFrame(h, w);
		makeLabel();
		makeTextBoxes(num);
		makeButton();
		frame.setContentPane(panel);
		frame.setVisible(true);
	}

	public void makeFrame(int h, int w) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(h,w));
		frame.setTitle("Enter names");
		width = w;
		height = h;
		panel = new JPanel();
		panel.setLayout(null);
	}

	public void makeLabel() {
		JLabel rumLabel = new JLabel("Enter the players' names.");
		rumLabel.setBounds(width/4 + 10, 50, width/2, 100);
		Font labelFont = rumLabel.getFont();
		Font f = new Font(labelFont.getName(), Font.PLAIN, 20);
		rumLabel.setFont(f);
		panel.add(rumLabel);
		frame.setLocation(300, 200);
		frame.setVisible(true);
	}

	public void makeTextBoxes(int numPlayers) {
		textboxes = new JTextField[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			JTextField box = new JTextField(20);
			box.setText("");
			box.setEditable(true);
			box.setBounds(width/4, height/3 + i*40, width/2, 30);
			textboxes[i] = box;
			panel.add(box);
		}
	}

	public void makeButton() {
		JButton button = new JButton("Done");
		button.addActionListener(this);
		int textBoxPos = textboxes[textboxes.length-1].getY();
		button.setBounds(width/3 + width/12, textBoxPos + 50, width/6, 40);
		panel.add(button);
	}

	public String[] getNames() {
		return names;
	}

	public void makeGame() {
		Game game = new Game(numPlayers, names);
	}

	public void actionPerformed(ActionEvent e) {
		names = new String[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			names[i] = textboxes[i].getText();
		}
		frame.setVisible(false);
		makeGame();
	}
}
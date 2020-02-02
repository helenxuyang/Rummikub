import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleScreen implements ActionListener {

	private JFrame frame;
	private JPanel panel;
	private JComboBox<String> numList;
	private String selected = "";
	private int width, height;
	
	public TitleScreen(int h, int w) {
		makeFrame(h, w);
		makeTitleImage();
		makeDropDown();
		makeDoneButton();
		makeInstructionsButton();	
		frame.setContentPane(panel);
		frame.setLocation(300, 200);
		frame.setVisible(true);
	}
	
	public void makeFrame(int w, int h) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(w, h));
		frame.setTitle("Rummikub");
		width = w;
		height = h;
		
		panel = new JPanel();
		panel.setLayout(null);
		frame.setVisible(true);
	}

	public void makeLabel() {
		JLabel rumLabel = new JLabel("Rummikub");
		rumLabel.setBounds(width/3, 50, 200, 100);
		rumLabel.setFont(new Font(rumLabel.getName(), Font.PLAIN, 30));
		panel.add(rumLabel);
	}
	
	public void makeTitleImage() {
		ImageIcon image;
		Picture pic = new Picture("RummikubPics\\rummikubTitle.PNG");
		image = new ImageIcon(pic.getImage());
		JLabel labelWithImage = new JLabel(image);
		labelWithImage.setBounds(60, 50, image.getIconWidth(), 100);
		labelWithImage.setVisible(true);
		panel.add(labelWithImage);
	}

	public void makeDropDown() {
		JLabel prompt = new JLabel("How many players?");
		Font f = new Font(prompt.getName(), Font.PLAIN, 20);
		prompt.setFont(f);
		prompt.setBounds(150, height/3 - 20, width/2, 60);
		panel.add(prompt);
		
		String[] nums = {"2", "3", "4"};
		numList = new JComboBox<String>(nums);
		numList.setBounds(width/3 + 10, height/3 + 50, 120, 40);
		numList.setSelectedItem("2");
		numList.addActionListener(this);
		panel.add(numList);
		frame.setVisible(true);
	}

	public void makeDoneButton() {
		JButton button = new JButton("Done");
		button.setVisible(true);
		button.setActionCommand("done");
		button.addActionListener(this);
		button.setBounds(width/3 + 30, height/2 + 30, 75, 40);
		panel.add(button);
		frame.setVisible(true);
	}
	
	public void makeInstructionsButton() {
		JButton button = new JButton("How to play");
		button.setVisible(true);
		button.setActionCommand("instructions");
		button.addActionListener(this);
		button.setBounds(width - 130, height - 100, 100, 50);
		panel.add(button);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		if (com.equals("done")) {
			selected = (String) numList.getSelectedItem();
			makeNameScreen(getNumOfPlayers());
		}
		else if (com.equals("instructions")) {
			makeInstructionScreen();
		}
	}

	public int getNumOfPlayers() {
		int num = Integer.parseInt(selected);
		return num;
	}
	
	public void makeNameScreen(int num) {
		frame.dispose();
		NameScreen n = new NameScreen(500, 500, num);
	}
	
	public void makeInstructionScreen() {
		frame.dispose();
		InstructionScreen i = new InstructionScreen(500, 700);
	}
}




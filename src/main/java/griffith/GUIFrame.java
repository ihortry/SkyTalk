/**
 * @author Ihor Tryndey 3105023 , Oleksii Babii 3104904
 * @since 2024
 * @version 1.0
 */
package griffith;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.text.WordUtils;

/**
 * @author Ihor Tryndey 3105023 , Oleksii Babii 3104904
 * Class RoundTextField which extends JTextField 
 * The main idea of this class is to create round text field to enter a statement 
 * inside chat bot
 */
public class GUIFrame extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private static JTextArea ca = new JTextArea();
	// Use RoundTextField instead of JTextField
	private static JTextField cf = new RoundTextField(20);
	// Use RoundButton instead of JButton
	private static JButton b = new RoundButton("SEND");
	
	private JLabel l = new JLabel();
	private static String input = null;
	
	public GUIFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setSize(500, 430);
		setLocation(500, 300);
		getContentPane().setBackground(Color.gray);
		setTitle("Fitness Program");

		/**
		 * Wrap text area inside the scrole panel
		 */
		JScrollPane scrollPane = new JScrollPane(ca);
		scrollPane.setBounds(20, 20, 440, 280);
		add(scrollPane);

		add(cf);
		add(b);
		l.setText("SEND");
		b.setSize(90, 30);
		b.setLocation(350, 320);
		ca.setSize(400, 310);
		ca.setLocation(20, 1);
		ca.setBackground(Color.white);
		cf.setSize(300, 30);
		cf.setLocation(20, 320);
		ca.setForeground(Color.black);
		ca.setFont(new Font("SANS_SERIF", Font.BOLD, 12));
		ca.setEditable(false);

		cf.addActionListener(this);
		b.addActionListener(this);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String userInput = getInput();
		String formattedInput = formatText(userInput);
		appendToTextArea(formattedInput);

		input = String.valueOf(userInput);
		System.out.println(input);
	}
	
	public static String formatText(String input) {
		String trimmedText = input.trim();
		return WordUtils.wrap(trimmedText, 70);
	}
	
	private static void appendToTextArea(String text) {
		ca.append("\nYou-->\n" + text + "\n");
	}
	
	private static String getInput() {
		String inputText = cf.getText();
		// Clear the input field after capturing the text
		cf.setText("");	
		return inputText;
	}
	
	public static String input() {
		String userInput = null;
		while (userInput == null) {
			userInput = input;
			System.out.println();
		}
		input = null;

		return userInput;
	}
	
	public static void output(String s) {
		ca.append("\nChatBot-->\n" + s + "\n");
	}

}

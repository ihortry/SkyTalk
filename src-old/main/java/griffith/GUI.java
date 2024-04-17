
package griffith;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.text.WordUtils;


public class GUI extends JFrame implements ActionListener{
	private long serialVersionUID;
	private JTextArea ca;
	private  JTextField cf; // Use RoundTextField instead of JTextField
	private JButton b; // Use RoundButton instead of JButton
	private JLabel l;
	private String input;
	
	public GUI(long serialVersionUID,JTextArea ca, JTextField cf,JButton b,JLabel l,String input ) {
		this.serialVersionUID = serialVersionUID;
		this.ca = ca;
		this.cf = cf;
		this.b = b;
		this.l = l;
		this.input = input;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setSize(500, 430);
		setLocation(500, 300);
		getContentPane().setBackground(Color.gray);
		setTitle("SkyTalk");

		/**
		 * Wrap text area inside the scrole panel. 
		 */
		JScrollPane scrollPane = new JScrollPane(ca);
		scrollPane.setBounds(20, 20, 440, 280);
		add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.addAdjustmentListener(new AdjustmentListener() {
		    public void adjustmentValueChanged(AdjustmentEvent e) {
		        e.getAdjustable().setValue(e.getAdjustable().getMaximum());
		    }
		});
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
	
	public String formatText(String input) {
		String trimmedText = input.trim();
		return WordUtils.wrap(trimmedText, 70);
	}
	
	public void appendToTextArea(String text) {
		ca.append("\nYou-->\n" + text + "\n");
	}

	public String getInput() {
		String inputText = cf.getText();
		cf.setText("");
		// Clear the input field after capturing the text
		return inputText;
	}

	public String input() {
		String userInput = null;
		while (userInput == null) {
			userInput = input;
			System.out.println();
		}
		input = null;

		return userInput;
	}
	
	public void output(String s) {
		ca.append("\nSkyTalk-->\n" + s + "\n");
	}
}

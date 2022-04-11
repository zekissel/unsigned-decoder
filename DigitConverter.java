import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class DigitConverter {
	// variables to be initialized and converted
	public String decimal, binary, hexadecimal;
	JTextArea outDec = new JTextArea();
	JTextArea outHex = new JTextArea();
	JTextArea outBin = new JTextArea();
	
	// input variables and JComponents
	boolean success = false;
	public String input;
	public String base = "Decimal";
	String [] bases = {"Decimal","Binary","Hexadecimal"};
	JComboBox baseSelector = new JComboBox(bases);
	JTextField inputText = new JTextField(32);
	JLabel preBin = new JLabel("0b");
	JLabel preHex = new JLabel("0x");
	JLabel error = new JLabel("BASES DO NOT MATCH OR TOO MANY BITS");
	
	public static void main (String [] args) {
		new DigitConverter();
	}
	
	public DigitConverter () {
		initializeFrame();
	}
	
	private void convertDec () {
		if (input.matches("^[0-9]+$") && input.length() <= 10) {
			error.setVisible(false);
			success = true;
			
			DecBuilder dec = new DecBuilder(input);
			decimal = dec.toString();
			BinBuilder bin = new BinBuilder(dec);
			binary = bin.toString();
			HexBuilder hex = new HexBuilder (bin);
			hexadecimal = hex.toString();
		} else {
			error.setVisible(true);
			success = false;
		}
	}
	
	private void convertBin () {
		if (input.matches("^[01]+$") && input.length() <= 32) {
			error.setVisible(false);
			success = true;
			
			BinBuilder bin = new BinBuilder(input);
			binary = bin.toString();
			HexBuilder hex = new HexBuilder (bin);
			hexadecimal = hex.toString();
			DecBuilder dec = new DecBuilder (hex);
			decimal = dec.toString();
		} else {
			error.setVisible(true);
			success = false;
		}
	}
	
	private void convertHex () {
		if (input.matches("^[0-9a-fA-F]+$") && input.length() <= 8) {
			error.setVisible(false);
			success = true;
			
			HexBuilder hex = new HexBuilder(input);
			hexadecimal = hex.toString();
			DecBuilder dec = new DecBuilder (hex);
			decimal = dec.toString();
			BinBuilder bin = new BinBuilder (dec);
			binary = bin.toString();
		} else {
			error.setVisible(true);
			success = false;
		}
	}
	
	private void compute () {
		input = inputText.getText();
		switch (base) {
			case "Decimal": convertDec(); break;
			case "Binary": convertBin(); break;
			case "Hexadecimal": convertHex(); break;
		}
		
		// update outputs
		if (success) {
			outDec.setText(" " + decimal);
			outHex.setText(" " + hexadecimal);
			
			String readBin = " ";
			BinBuilder readable = new BinBuilder(binary);
			for (int i = 0; i < 8; i++) {
				readBin = readBin + readable.getFront();
				readBin = readBin + " ";
				readable.rotateFront();
			}
			outBin.setText(readBin);
		} else {
			outBin.setText(" null");
			outDec.setText(" null");
			outHex.setText(" null");
		}
	}
	
	private void setBase () {
		base = (String)baseSelector.getSelectedItem();
		updateDisplay(base);
	}
	
	private void updateDisplay (String base) {
		switch (base) {
			case "Decimal":
				preBin.setVisible(false);
				preHex.setVisible(false);
				break;
			case "Binary": 
				preBin.setVisible(true);
				preHex.setVisible(false);
				break;
			case "Hexadecimal": 
				preBin.setVisible(false);
				preHex.setVisible(true);
				break;
		}
	}
	
	private void initializeFrame() {
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("Number Nexus");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(0,0,464,410);
		mainFrame.setLayout(null);
		mainFrame.setResizable(false);
		
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		Font font2 = new Font("SansSerif", Font.BOLD, 16);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(1,1,448,150);
		infoPanel.setLayout(null);
		infoPanel.setBackground(Color.gray);
		
		JPanel errPanel = new JPanel();
		errPanel.setBounds(1,153,448,25);
		errPanel.setBackground(Color.lightGray);
		errPanel.setLayout(null);
		error.setBounds(90,1,300,23);
		error.setVisible(false);
		error.setForeground(Color.red);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setBounds(1,180,448,180);
		outputPanel.setLayout(null);
		outputPanel.setBackground(Color.gray);
		
		JLabel labDec = new JLabel("Base 10 (Decimal): ");
		labDec.setFont(font2);
		labDec.setBounds(260,15,180,30);
		JLabel labHex = new JLabel("Base 16 (Hexadecimal): ");
		labHex.setFont(font2);
		labHex.setBounds(40,15,190,30);
		JLabel labBin = new JLabel("Base 2 (Binary): ");
		labBin.setFont(font2);
		labBin.setBounds(40,90,410,30);
		
		outDec.setBounds(265,50,130,30);
		outDec.setFont(font1);
		outDec.setEditable(false);
		outHex.setBounds(85,50,110,30);
		outHex.setFont(font1);
		outHex.setEditable(false);
		JLabel preOutHex = new JLabel("0x");
		preOutHex.setBounds(60,47,25,35);
		preOutHex.setFont(font1);
		outBin.setBounds(32,125,406,30);
		outBin.setFont(font1);
		outBin.setEditable(false);
		JLabel preOutBin = new JLabel("0b");
		preOutBin.setBounds(7,122,25,35);
		preOutBin.setFont(font1);
		
		baseSelector.setSelectedIndex(0);
		baseSelector.addActionListener(e -> setBase());
		baseSelector.setBounds(1,1,449,25);
		baseSelector.setFont(font2);
		
		preBin.setFont(font2);
		preBin.setBounds(3,30,20,40);
		preBin.setVisible(false);
		preHex.setFont(font2);
		preHex.setBounds(3,30,20,40);
		preHex.setVisible(false);

		inputText.setFont(font1);
		inputText.setBounds(23,30,402,40);

		JLabel info = new JLabel("Select base of input, enter number (max 32 bits), and press convert:");
		info.setBounds(30,76,444,20);
		JButton calculate = new JButton("Convert");
		calculate.addActionListener(e -> compute());
		calculate.setBounds(20,100,410,35);
		calculate.setFont(font1);

		infoPanel.add(preBin);
		infoPanel.add(preHex);
		infoPanel.add(inputText);
		infoPanel.add(baseSelector);
		infoPanel.add(info);
		infoPanel.add(calculate);
		
		errPanel.add(error);
		
		outputPanel.add(labBin);
		outputPanel.add(labDec);
		outputPanel.add(outDec);
		outputPanel.add(labHex);
		outputPanel.add(preOutHex);
		outputPanel.add(outHex);
		outputPanel.add(preOutBin);
		outputPanel.add(outBin);
		
		mainFrame.add(infoPanel);
		mainFrame.add(errPanel);
		mainFrame.add(outputPanel);
		mainFrame.setVisible(true);
	}
	
}
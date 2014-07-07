package GUI;

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import UTILS.Config;

public class Save extends JDialog{

	public Save(final Frame frame) {
		
		// TODO Auto-generated constructor stub
		
		setModal(true);
		setTitle("Save configuration");
		setSize(500, 150);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//setLocation(frame.getWidth()/2 - 200, frame.getHeight()/2 - 100);
		setResizable(false);
		setLocationRelativeTo (null);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 10, 480, 60);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		Label labelClass = new Label("Save to:");
		labelClass.setBounds(10,15,70,20);
		panel.add(labelClass);
		
		final TextField textField = new TextField();
		textField.setFocusable(true);
		textField.setBounds(80, 15, 280, 22);
		panel.add(textField);
		
		final Button button_2 = new Button("Ok");
		button_2.setBounds(150, 80, 80, 30);
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "A file name must be provided!");
					return;
				}
				else {
					try {
						FileWriter fw = new FileWriter(textField.getText());
						LinkedList<String> conf = Config.ACTIVELAYER.createCSV();
						for (String linea : conf) {
							fw.write(linea);
							fw.write("\n");
						}
						
						fw.close();
						dispose();
						System.out.println("Saved!");
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}	
	           
			}
		});
		getContentPane().add(button_2);
		
		Button button_1 = new Button("Browse");
		button_1.setBounds(380, 10, 80, 30);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FileDialog chooser = new FileDialog(frame,"Save",FileDialog.SAVE);
				chooser.setVisible(true);
			    textField.setText(chooser.getDirectory()+chooser.getFile()+".csv");
			    
			    button_2.requestFocus();
			}
		});
		
		panel.add(button_1);
				
		Button button_3 = new Button("Cancel");
		button_3.setBounds(250, 80, 80, 30);
		button_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		getContentPane().add(button_3);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		new Save(null);
	}
	
}

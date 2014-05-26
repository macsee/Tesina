package GUI;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import UTILS.*;

public class LoadImages extends JFrame {

	/**
	 * @param args
	 */
	private Frame ventana; 
	
	public LoadImages(Frame frame, final Layer thr1, final Layer hr1, final Layer hr2) {
		ventana = frame;
		setTitle("Load Images");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(540,240);
		
		setLocationRelativeTo (null);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 10, 520, 138);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		Label label = new Label("THR1 Image");
		label.setBounds(10, 20, 84, 17);
		panel.add(label);
		
		final TextField textField = new TextField();
		textField.setBounds(100, 20, 300, 22);
		panel.add(textField);
		
		Button button = new Button("Browse");
		button.setBounds(420, 15, 80, 30);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Opening...");
				
				FileDialog chooser = new FileDialog(ventana);
				chooser.setVisible(true);
				
				chooser.setFilenameFilter(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						return name.endsWith(".png");
					}
				});
				
				if (chooser.getFile() != null)
					textField.setText(chooser.getDirectory()+chooser.getFile());
				else
					textField.setText("");
			}
		});
		panel.add(button);
		
		Label label_1 = new Label("HR1 Image");
		label_1.setBounds(10, 60, 84, 17);
		panel.add(label_1);
		
		final TextField textField_1 = new TextField();
		textField_1.setBounds(100, 60, 300, 22);
		panel.add(textField_1);
		
		Button button_1 = new Button("Browse");
		button_1.setBounds(420, 55, 80, 30);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Opening...");
				
				FileDialog chooser = new FileDialog(ventana);
				chooser.setVisible(true);
				
				chooser.setFilenameFilter(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						return name.endsWith(".png");
					}
				});
				
				if (chooser.getFile() != null)
					textField_1.setText(chooser.getDirectory()+chooser.getFile());
				else
					textField_1.setText("");
				
			}
		});
		panel.add(button_1);
		
		Label label_2 = new Label("HR2 Image");
		label_2.setBounds(10, 100, 84, 17);
		panel.add(label_2);
		
		final TextField textField_2 = new TextField();
		textField_2.setBounds(100, 100, 300, 22);
		panel.add(textField_2);
		
		Button button_2 = new Button("Browse");
		button_2.setBounds(420, 95, 80, 30);
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Opening...");
				
				FileDialog chooser = new FileDialog(ventana);
				chooser.setVisible(true);
				
				chooser.setFilenameFilter(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						return name.endsWith(".png");
					}
				});
				
				if (chooser.getFile() != null)
					textField_2.setText(chooser.getDirectory()+chooser.getFile());
				else
					textField_2.setText("");
				
			}
		});
		panel.add(button_2);
		
		Button button_3 = new Button("Aceptar");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!textField.getText().equals("")) {
					thr1.setImage(textField.getText());
					thr1.allowDrawing();
				}
				
				if (!textField_1.getText().equals("")) {
					hr1.setImage(textField_1.getText());
					hr1.allowDrawing();
				}
				
				if (!textField_2.getText().equals("")) {
					hr2.setImage(textField_2.getText());
					hr2.allowDrawing();
				}
				
				ventana.repaint();
				dispose();
			}
		});
		button_3.setBounds(160, 170, 100, 35);
		getContentPane().add(button_3);
		
		Button button_4 = new Button("Cancelar");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.repaint();
				dispose();
			}
		});
		button_4.setBounds(280, 170, 100, 35);
		getContentPane().add(button_4);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoadImages(null, null, null, null).setVisible(true);
	}
}

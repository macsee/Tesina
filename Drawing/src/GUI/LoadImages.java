package GUI;
import java.awt.Button;
import java.awt.Choice;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import UTILS.Config;
import UTILS.Layer;

public class LoadImages extends JDialog {

	/**
	 * @param args
	 */
	private Simulator ventana; 
	
	public LoadImages(JFrame frame) {
		
		setModal(true);
		ventana = (Simulator) frame;
		setTitle("Load Image");
		setResizable(false);
		setSize(540,220);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setLocationRelativeTo (null);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 10, 520, 120);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		Label label = new Label("Image");
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
		
		Label labelLayer = new Label("Select Layer");
		labelLayer.setBounds(10, 75, 50, 30);
		panel.add(labelLayer);
		
		final Choice choiceLayer = new Choice();
		
		int index;
		
		choiceLayer.add("");
		for (index = 0; index < Config.LISTLAYERS.getSize(); index++)
			choiceLayer.add(Config.LISTLAYERS.get(index).toString());
		
		
		choiceLayer.setBounds(100,75,200,30);
		panel.add(choiceLayer);
		
		
		
		Button button_3 = new Button("Aceptar");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Some image must be selected!");
					return;
				}
				
				if (choiceLayer.getSelectedItem() == "") {
					if (Config.LISTLAYERS.isEmpty())
						JOptionPane.showMessageDialog(null, "Please create a Layer first!");
					else
						JOptionPane.showMessageDialog(null, "Some Layer must be selected!");
					
					return;
				}
				
				Layer layer = Config.getLayer(choiceLayer.getSelectedIndex()-1);
				Config.ACTIVELAYER = layer;
				layer.setImage(textField.getText());
				ventana.selectActiveLayerInList();
				
				ventana.repaint();
				dispose();
			}
		});
		button_3.setBounds(160, 150, 100, 35);
		getContentPane().add(button_3);
		
		Button button_4 = new Button("Cancelar");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.repaint();
				dispose();
			}
		});
		button_4.setBounds(280, 150, 100, 35);
		getContentPane().add(button_4);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoadImages(null).setVisible(true);
	}
}

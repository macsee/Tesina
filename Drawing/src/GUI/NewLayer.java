package GUI;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import UTILS.Config;
import UTILS.Layer;
import UTILS.ObjGeom;

public class NewLayer extends JDialog {

	/**
	 * @param args
	 */
	private Frame ventana;
	private String resolution = "";
	
	public NewLayer(Frame frame, final Layer layer) {
		
		setModal(true);
		ventana = frame;
		
		if (layer != null)
			setTitle("Edit Layer");
		else
			setTitle("New Layer");
		
		setResizable(false);
		setSize(300,250);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setLocationRelativeTo (null);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 10, 280, 80);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		Label label = new Label("Name");
		label.setBounds(10, 20, 60, 17);
		panel.add(label);
		
		final TextField textField = new TextField();
		if (layer != null)
			textField.setText(layer.LAYERID);
		else
			textField.setText("Layer"+Config.LISTLAYERS.getSize());
			
		textField.setFocusable(true);
		textField.setBounds(70, 20, 200, 22);
		panel.add(textField);
		
		CheckboxGroup cg = new CheckboxGroup ();
		
		final Checkbox checkTHR1 = new Checkbox("THR1",cg,false);
		checkTHR1.setBounds(30, 120, 80, 18);
		if ((layer != null) && (layer.LAYER_RESOLUTION == "THR1"))
			checkTHR1.setState(true);
		
		getContentPane().add(checkTHR1);
		
		final Checkbox checkHR1 = new Checkbox("HR1",cg,false);
		checkHR1.setBounds(120, 120, 80, 18);
		if ((layer != null) && (layer.LAYER_RESOLUTION == "HR1"))
			checkHR1.setState(true);
		
		getContentPane().add(checkHR1);
		
		final Checkbox checkHR2 = new Checkbox("HR2",cg,false);
		checkHR2.setBounds(210, 120, 80, 18);
		if ((layer != null) && (layer.LAYER_RESOLUTION == "HR2"))
			checkHR2.setState(true);
		
		getContentPane().add(checkHR2);
		
		if (layer != null)
			resolution = layer.LAYER_RESOLUTION;
		
		checkTHR1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == e.SELECTED) {
					checkHR1.setState(false);
					checkHR2.setState(false);
					System.out.println("THR1 selected!");
					resolution = "THR1";
				}	
			}
		});
		
		checkHR1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == e.SELECTED) {
					checkTHR1.setState(false);
					checkHR2.setState(false);
					System.out.println("HR1 selected!");
					resolution = "HR1";
				}	
			}
		});
		
		checkHR2.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == e.SELECTED) {
					checkTHR1.setState(false);
					checkHR1.setState(false);
					System.out.println("HR2 selected!");
					resolution = "HR2";
				}	
			}
		});
		
		Button button_3 = new Button("Ok");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "A name must be provided!");
					return;
				}
				else {
						
					if (resolution.contentEquals("")) {
						JOptionPane.showMessageDialog(null, "Please select resolution of Layer!");
						return;
					}	
					else {
						if (layer != null) {
							layer.LAYERID = textField.getText();
							layer.LAYER_RESOLUTION = resolution;
							for (ObjGeom obj : layer.getObjsGeom())
								obj.setRESOLUTION(resolution); //Actualizo la resolucion en los objetos de la capa actualizada
								
							Config.refreshLayerList(layer);
						}	
						else
							Config.addLayer(textField.getText(),resolution); // RECORDAR QUE CUANDO AGREGO UNA CAPA NUEVA ESTA SE SETEA COMO ACTIVA
					}
				}
				
				ventana.repaint();
				dispose();
			}
		});
		
		button_3.setBounds(40, 170, 100, 35);
		getContentPane().add(button_3);
		
		Button button_4 = new Button("Cancel");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.repaint();
				dispose();
			}
		});
		button_4.setBounds(160, 170, 100, 35);
		getContentPane().add(button_4);
	
		setVisible(true);
			
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new NewLayer(null,null).setVisible(true);
	}
}


package GUI;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.xml.crypto.dsig.TransformException;

import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;

import UTILS.*;


public class LoadRasterShape extends JFrame {

	/**
	 * @param args
	 */
	private Frame ventana;
	private String resolution = "";
	
	public LoadRasterShape(Frame frame, final Layer thr1, final Layer hr1, final Layer hr2) {
		ventana = frame;
		setTitle("Load Images");
		setResizable(false);
		setSize(540,300);
		
		setLocationRelativeTo (null);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 10, 520, 140);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		Label label = new Label("Raster Image");
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
						return name.endsWith(".tif");
					}
				});
				
				if (chooser.getFile() != null)
					textField.setText(chooser.getDirectory()+chooser.getFile());
				else
					textField.setText("");
			}
		});
		panel.add(button);
		
		Label label_1 = new Label("Shapefile");
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
						return name.endsWith(".shp");
					}
				});
				
				if (chooser.getFile() != null)
					textField_1.setText(chooser.getDirectory()+chooser.getFile());
				else
					textField_1.setText("");
				
			}
		});
		panel.add(button_1);
		
		Label label_2 = new Label("Coordinates");
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
						return name.endsWith(".points");
					}
				});
				
				if (chooser.getFile() != null)
					textField_2.setText(chooser.getDirectory()+chooser.getFile());
				else
					textField_2.setText("");
				
			}
		});
		panel.add(button_2);
		
		CheckboxGroup cg = new CheckboxGroup ();
		
		final Checkbox checkTHR1 = new Checkbox("THR1",cg,false);
		checkTHR1.setBounds(10, 185, 80, 18);
		getContentPane().add(checkTHR1);
		
		final Checkbox checkHR1 = new Checkbox("HR1",cg,false);
		checkHR1.setBounds(100, 185, 80, 18);
		getContentPane().add(checkHR1);
		
		final Checkbox checkHR2 = new Checkbox("HR2",cg,false);
		checkHR2.setBounds(190, 185, 80, 18);
		getContentPane().add(checkHR2);
		
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
					resolution = "HR2";
				}	
			}
		});
		
		Button button_3 = new Button("Aceptar");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Shape2ObjGeom shp2obj = null;
				Layer layer = null;
				
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Some raster file must be selected!");
					return;
				}
				else {
						
					if (resolution.contentEquals("THR1"))
						layer = thr1;
					else if (resolution.contentEquals("HR1"))
						layer = hr1;
					else if (resolution.contentEquals("HR2"))
						layer = hr2;
					else if (resolution == "") {
						JOptionPane.showMessageDialog(null, "Some Layer must be selected!");
						return;
					}
					
					if (!textField_1.getText().equals("") & !textField_2.getText().equals("")) {
						try {
							shp2obj= new Shape2ObjGeom(textField_1.getText(), textField_2.getText());
							if (shp2obj != null) {
								try {
									
									layer.setImage(textField.getText());
									layer.allowDrawing();
									Config.ACTIVELAYER = layer;
									layer.setObjsGeom(shp2obj.adjustProyection());
									
								} catch (MismatchedDimensionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (TransformException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (FactoryException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (org.opengis.referencing.operation.TransformException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (textField_1.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Shape file must be selected!");
						return;
					}	
					else if	(textField_2.getText().equals("")){
						JOptionPane.showMessageDialog(null, "File with coordinate points must be selected!");
						return;
					}
				}
				
				ventana.repaint();
				dispose();
			}
		});
		
		button_3.setBounds(161, 233, 100, 35);
		getContentPane().add(button_3);
		
		Button button_4 = new Button("Cancelar");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.repaint();
				dispose();
			}
		});
		button_4.setBounds(281, 233, 100, 35);
		getContentPane().add(button_4);
	
		setVisible(true);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoadRasterShape(null, null, null, null).setVisible(true);
	}
}

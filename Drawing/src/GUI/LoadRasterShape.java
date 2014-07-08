package GUI;
import java.awt.Button;
import java.awt.Choice;
import java.awt.FileDialog;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.crypto.dsig.TransformException;

import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;

import UTILS.Config;
import UTILS.Layer;
import UTILS.Shape2ObjGeom;


public class LoadRasterShape extends JDialog {

	/**
	 * @param args
	 */
	private Simulator ventana;
	private String resolution = "";
	private File currentDirectory = new File(".");
	
	public LoadRasterShape(JFrame frame) {
		
		setModal(true);
		ventana = (Simulator) frame;
		setTitle("Load Images");
		setResizable(false);
		setSize(550,350);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo (null);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 10, 530, 200);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		Label label = new Label("Raster Image");
		label.setBounds(10, 20, 100, 17);
		panel.add(label);
		
		final TextField textField = new TextField();
		textField.setBounds(120, 20, 300, 22);
		panel.add(textField);
		
		Button button = new Button("Browse");
		button.setBounds(430, 15, 80, 30);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Opening...");
				
//				JFileChooser chooser = new JFileChooser();
//				chooser.setCurrentDirectory(currentDirectory);
//				FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG & TIF Images", "png", "tif");
//				chooser.setFileFilter(filter);
//				int returnVal = chooser.showOpenDialog(ventana);
//				
//				if(returnVal == JFileChooser.APPROVE_OPTION) {
//					textField.setText(chooser.getSelectedFile().getAbsolutePath());
//					currentDirectory = new File(chooser.getSelectedFile().getParent());
//				}
				 
				
				FileDialog chooser = new FileDialog(ventana);
				chooser.setDirectory(Config.ACTIVELAYER.CURRENTDIR);
				chooser.setVisible(true);
				
				chooser.setFilenameFilter(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						System.out.println(dir);
						return name.endsWith(".tif");
					}
				});
				
				if (chooser.getFile() != null) {
					textField.setText(chooser.getDirectory()+chooser.getFile());
					Config.ACTIVELAYER.CURRENTDIR = chooser.getDirectory();
				}	
				else
					textField.setText("");
			}
		});
		panel.add(button);
		
		Label label_1 = new Label("Shapefile");
		label_1.setBounds(10, 60, 100, 17);
		panel.add(label_1);
		
		final TextField textField_1 = new TextField();
		textField_1.setBounds(120, 60, 300, 22);
		panel.add(textField_1);
		
		Button button_1 = new Button("Browse");
		button_1.setBounds(430, 55, 80, 30);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Opening...");
				
//				JFileChooser chooser = new JFileChooser();
//				chooser.setCurrentDirectory(currentDirectory);
//				
//				FileNameExtensionFilter filter = new FileNameExtensionFilter("Shape files *.shp", "shp");
//				chooser.setFileFilter(filter);
//				int returnVal = chooser.showOpenDialog(ventana);
//				
//				if(returnVal == JFileChooser.APPROVE_OPTION) {
//					textField_1.setText(chooser.getSelectedFile().getAbsolutePath());
//					currentDirectory = new File(chooser.getSelectedFile().getParent());
//				}
				
				FileDialog chooser = new FileDialog(ventana);
				chooser.setDirectory(Config.ACTIVELAYER.CURRENTDIR);
				chooser.setVisible(true);
				
				chooser.setFilenameFilter(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						return name.endsWith(".shp");
					}
				});
				
				if (chooser.getFile() != null) {
					textField_1.setText(chooser.getDirectory()+chooser.getFile());
					Config.ACTIVELAYER.CURRENTDIR = chooser.getDirectory();
				}	
				
				else
					textField_1.setText("");
				
			}
		});
		panel.add(button_1);
		
		Label label_2 = new Label("Coordinates");
		label_2.setBounds(10, 100, 100, 17);
		panel.add(label_2);
		
		final TextField textField_2 = new TextField();
		textField_2.setBounds(120, 100, 300, 22);
		panel.add(textField_2);
		
		Button button_2 = new Button("Browse");
		button_2.setBounds(430, 95, 80, 30);
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Opening...");
				
//				JFileChooser chooser = new JFileChooser();
//				chooser.setCurrentDirectory(currentDirectory);
//				
//				FileNameExtensionFilter filter = new FileNameExtensionFilter("Coordinates files *.points", "points");
//				chooser.setFileFilter(filter);
//				int returnVal = chooser.showOpenDialog(ventana);
//				
//				if(returnVal == JFileChooser.APPROVE_OPTION) {
//					textField_2.setText(chooser.getSelectedFile().getAbsolutePath());
//					currentDirectory = new File(chooser.getSelectedFile().getParent());
//				}
				
				FileDialog chooser = new FileDialog(ventana);
				chooser.setDirectory(Config.ACTIVELAYER.CURRENTDIR);
				chooser.setVisible(true);
				
				chooser.setFilenameFilter(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						return name.endsWith(".points");
					}
				});
				
				if (chooser.getFile() != null) {
					textField_2.setText(chooser.getDirectory()+chooser.getFile());
					Config.ACTIVELAYER.CURRENTDIR = chooser.getDirectory();
				}	
				else
					textField_2.setText("");
//				
			}
		});
		panel.add(button_2);
		
		
		Label label_3 = new Label("Configuration");
		label_3.setBounds(10, 140, 100, 17);
		panel.add(label_3);
		
		final TextField textField_3 = new TextField();
		textField_3.setBounds(120, 140, 300, 22);
		panel.add(textField_3);
		
		Button button_3 = new Button("Browse");
		button_3.setBounds(430, 135, 80, 30);
		button_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Opening...");
				
//				JFileChooser chooser = new JFileChooser();
//				chooser.setCurrentDirectory(currentDirectory);
//				FileNameExtensionFilter filter = new FileNameExtensionFilter("Configuration files *.csv", "csv");
//				chooser.setFileFilter(filter);
//				int returnVal = chooser.showOpenDialog(ventana);
//				
//				if(returnVal == JFileChooser.APPROVE_OPTION) {
//					textField_3.setText(chooser.getSelectedFile().getAbsolutePath());
//					currentDirectory = new File(chooser.getSelectedFile().getParent());
//				}
				
				FileDialog chooser = new FileDialog(ventana);
				chooser.setDirectory(Config.ACTIVELAYER.CURRENTDIR);
				chooser.setVisible(true);
				
				chooser.setFilenameFilter(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						return name.endsWith(".csv");
					}
				});
				
				if (chooser.getFile() != null) {
					textField_3.setText(chooser.getDirectory()+chooser.getFile());
					Config.ACTIVELAYER.CURRENTDIR = chooser.getDirectory();
				}	
				else
					textField_3.setText("");
//				
			}
		});
		panel.add(button_3);
		
		Label labelLayer = new Label("Select Layer");
		labelLayer.setBounds(20, 230, 80, 30);
		getContentPane().add(labelLayer);
		
		final Choice choiceLayer = new Choice();
		
		int index;
		
		choiceLayer.add("");
		for (index = 0; index < Config.LISTLAYERS.getSize(); index++)
			choiceLayer.add(Config.LISTLAYERS.get(index).toString());
		
		
		choiceLayer.setBounds(128,225,200,30);
		getContentPane().add(choiceLayer);
				
		Button button_4 = new Button("Aceptar");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Shape2ObjGeom shp2obj = null;
				Layer layer = null;
				
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Some raster file must be selected!");
					return;
				}
						
				if (textField_1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Shape file must be selected!");
					return;
				}
				
				if (textField_2.getText().equals("")){
					JOptionPane.showMessageDialog(null, "File with coordinate points must be selected!");
					return;
				}
				
				if (choiceLayer.getSelectedItem() == "") {
					if (Config.LISTLAYERS.isEmpty())
						JOptionPane.showMessageDialog(null, "Please create a Layer first!");
					else
						JOptionPane.showMessageDialog(null, "Some Layer must be selected!");
					
					return;
				}
							
//				if (!textField_1.getText().equals("") & !textField_2.getText().equals("")) {
					try {
						shp2obj= new Shape2ObjGeom(textField_1.getText(), textField_2.getText());
						if (shp2obj != null) {
							try {
								layer = Config.getLayer(choiceLayer.getSelectedIndex()-1); //OBTENGO LA CAPA DE LA LISTA
								layer.setImage(textField.getText());
								layer.setObjsGeom(shp2obj.adjustProyection());
								ventana.selectActiveLayerInList();
								layer.allowDrawing();
								if (!textField_3.getText().equals(""))
									layer.readCSV(textField_3.getText());
								
								Config.fillDefaultList();
								
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
//				}
				
				ventana.repaint();
				dispose();
			}
		});
		
		button_4.setBounds(160, 280, 100, 35);
		getContentPane().add(button_4);
		
		Button button_5 = new Button("Cancelar");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.repaint();
				dispose();
			}
		});
		button_5.setBounds(280, 280, 100, 35);
		getContentPane().add(button_5);
	
		setVisible(true);
			
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

				if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					dispose();
				}
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		this.setFocusable(true);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
			new LoadRasterShape(null).setVisible(true);
		
	}
}

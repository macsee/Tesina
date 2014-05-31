package GUI;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.xml.crypto.dsig.TransformException;

import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;

import OWL.CM8toOWL;
import UTILS.*;
		

public class Simulator extends JFrame{
	
	/**
	 * @param args
	 */
	private static MainPanel panelPrincipal;
	private static Layer LAYER; 
	private static Checkbox radioButtonTHR1;
	private static Checkbox radioButtonHR1;
	private static Checkbox radioButtonHR2;
	
	public Simulator() {
	
		
		Config.THR1 = new Layer("THR1");
		Config.HR1 = new Layer("HR1");
		Config.HR2 = new Layer("HR2");
		
//		LAYER = Config.THR1;
		
		setFocusable(true);
		setTitle("Tesina");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1165,710);
		
		setLocationRelativeTo (null);
		Container panel = getContentPane();
		panel.setLayout(null);
		System.out.println(getHeight());
		System.out.println(getWidth());
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 800, 40);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		Button btnRun = new Button("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					CM8toOWL CM8 = new CM8toOWL();
					Config.OUT.clear();
					
					long startTime = System.currentTimeMillis();

					Config.THR1.assertDataForObjsInLayer(CM8);
					Config.HR1.assertDataForObjsInLayer(CM8);
					Config.HR2.assertDataForObjsInLayer(CM8);
					
					Config.THR1.getAssertedDataInLayer(CM8);
					Config.HR1.getAssertedDataInLayer(CM8);
					Config.HR2.getAssertedDataInLayer(CM8);
					
					Config.THR1.getInferredDataInLayer(CM8);
					Config.HR1.getInferredDataInLayer(CM8);
					Config.HR2.getInferredDataInLayer(CM8);

					Config.OUT.addAll(Config.THR1.OUT);
					Config.OUT.addAll(Config.HR1.OUT);
					Config.OUT.addAll(Config.HR2.OUT);
					
					long endTime = System.currentTimeMillis();
					Config.OUT.add("Total Time: "+(endTime - startTime)+" milliseconds");
					
					CM8.saveOnto();
					
					for (String val : Config.OUT) {
			        	System.out.println(val);  
			        }
					
					panelPrincipal.repaint();				
			}
		});
		btnRun.setFocusable(false);
		btnRun.setBounds(5, 5, 100, 35);
		panel_1.add(btnRun);
	
		Button btnClean = new Button("Clean Layer");
		btnClean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Config.ACTIVELAYER.clean();
				panelPrincipal.repaint();
			}
		});
		btnClean.setFocusable(false);
		btnClean.setBounds(110, 5, 100, 35);
		panel_1.add(btnClean);
		
		Button btnCleanAll = new Button("Clean All");
		btnCleanAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Config.THR1.clean();
				Config.HR1.clean();
				Config.HR2.clean();
				Config.OBJCOUNT = 0;
				panelPrincipal.repaint();
			}
		});
		btnCleanAll.setFocusable(false);
		btnCleanAll.setBounds(215, 5, 100, 35);
		panel_1.add(btnCleanAll);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(810, 45, 350, 600);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		Label label = new Label("Regions Detected");
		label.setBounds(5, 5, 150, 17);
		panel_2.add(label);
		
		CheckboxGroup cg = new CheckboxGroup ();
		radioButtonTHR1 = new Checkbox("THR1",cg,true);
		radioButtonTHR1.setBounds(5, 50, 141, 23);
		radioButtonTHR1.setFocusable(false);
		
		radioButtonHR2 = new Checkbox("HR2",cg,false);
		radioButtonHR2.setBounds(5, 230, 141, 23);
		radioButtonHR2.setFocusable(false);
		
		radioButtonHR1 = new Checkbox("HR1",cg,false);
		radioButtonHR1.setBounds(5, 410, 141, 23);
		radioButtonHR1.setFocusable(false);
		
		panel_2.add(radioButtonTHR1);
		panel_2.add(radioButtonHR2);
		panel_2.add(radioButtonHR1);
		
		final JList listTHR1 = new JList(Config.THR1.getDefaultList());
		listTHR1.setFont(new Font("",0,10));
		listTHR1.setBounds(30, 80, 300, 120);
		listTHR1.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		        	ObjGeom obj = Config.THR1.getObj(listTHR1.getSelectedIndex());
		        	if (obj != null)
		        		new SetClass(obj, Config.THR1);
		        }
		    }
		});
		
		ScrollPane scrollPaneTHR1 = new ScrollPane();
		scrollPaneTHR1.add(listTHR1);
		scrollPaneTHR1.setBounds(30, 80, 300, 120);
		panel_2.add(scrollPaneTHR1);
		
		final JList listHR2 = new JList(Config.HR2.getDefaultList());
		listHR2.setFont(new Font("",0,10));
		listHR2.setBounds(30, 260, 300, 120);
		listHR2.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		        	ObjGeom obj = Config.HR2.getObj(listHR2.getSelectedIndex());
		        	if (obj != null)
		        		new SetClass(obj, Config.HR2);
		        }
		    }
		});
		
		ScrollPane scrollPaneHR2 = new ScrollPane();
		scrollPaneHR2.add(listHR2);
		scrollPaneHR2.setBounds(30, 260, 300, 120);
		panel_2.add(scrollPaneHR2);
		
		final JList listHR1 = new JList(Config.HR1.getDefaultList());
		listHR1.setFont(new Font("",0,10));
		listHR1.setBounds(30, 440, 300, 120);
		listHR1.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		        	ObjGeom obj = Config.HR1.getObj(listHR1.getSelectedIndex());
		        	if (obj != null)
		        		new SetClass(obj, Config.HR1);
		        }
		    }
		});
		
		ScrollPane scrollPaneHR1 = new ScrollPane();
		scrollPaneHR1.add(listHR1);
		scrollPaneHR1.setBounds(30, 440, 300, 120);
		panel_2.add(scrollPaneHR1);
		
		radioButtonTHR1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == e.SELECTED) {
					radioButtonHR1.setState(false);
					radioButtonHR2.setState(false);
					System.out.println("THR1 selected!");
					Config.ACTIVELAYER = Config.THR1;
					panelPrincipal.repaint();
				}	
			}
		});
		
		radioButtonHR1.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if (e.getStateChange() == e.SELECTED) {
							radioButtonTHR1.setState(false);
							radioButtonHR2.setState(false);
							System.out.println("HR1 selected!");
							Config.ACTIVELAYER = Config.HR1;
							panelPrincipal.repaint();
						}	
					}
				});
		
		radioButtonHR2.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == e.SELECTED) {
					radioButtonTHR1.setState(false);
					radioButtonHR1.setState(false);
					System.out.println("HR2 selected!");
					Config.ACTIVELAYER = Config.HR2;
					panelPrincipal.repaint();
				}	
			}
		});
				
		addKeyListener(new KeyActions(this));
		Config.ACTIVELAYER = Config.THR1;
		setMenuBar(new MenuBarAWT(this,Config.THR1, Config.HR1, Config.HR2).createMenuBar());
		panelPrincipal = new MainPanel(800, 600);
		panel.add(panelPrincipal);
		
		setVisible(true);
	}
	
	public void paint(Graphics g) {		
		paintComponents(g);
		
		if (Config.ACTIVELAYER == Config.THR1)
			radioButtonTHR1.setState(true);
		else if (Config.ACTIVELAYER == Config.HR1)
			radioButtonHR1.setState(true);
		else if (Config.ACTIVELAYER == Config.HR2) 
			radioButtonHR2.setState(true);
    }

	public static void main(String[] args) {
		new Simulator();
		System.gc();
		
		Shape2ObjGeom shp2obj;
		try {
			shp2obj = new Shape2ObjGeom("/Users/Macsee/Desktop/Examples/Raster+Shp/polygons.shp", "/Users/Macsee/Desktop/Examples/Raster+Shp/tabla.points");
			Config.ACTIVELAYER.setImage("/Users/Macsee/Desktop/Examples/Raster+Shp/image.png");
			Config.ACTIVELAYER.allowDrawing();
			//Config.ACTIVELAYER = LAYER;
			Config.ACTIVELAYER.setObjsGeom(shp2obj.adjustProyection());
			panelPrincipal.repaint();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}

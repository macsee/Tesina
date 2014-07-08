package GUI;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import OWL.CM8toOWL;
import UTILS.Config;
import UTILS.Layer;
import UTILS.ObjGeom;
import UTILS.MyThread;
import UTILS.MyTask;
		

public class Simulator extends JFrame{
	
	/**
	 * @param args
	 */
	private static MainPanel panelPrincipal;
//	private static Layer LAYER; 
	private static Checkbox radioButtonTHR1;
	private static Checkbox radioButtonHR1;
	private static Checkbox radioButtonHR2;
	private static Simulator ventana;
	private static JList listLayers;
	
	public Simulator() {
		
		Config.addLayer("Layer0", "HR2"); // Capa inicial
		ventana = this;
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
					
					Config.OUT.clear();
					
//					CM8toOWL CM8 = new CM8toOWL();
							
					boolean emptyLayers = true;
					
					for (Layer layer : Config.LAYERS)
						if (!layer.emptyObjList())
							emptyLayers = false;
					
					if (emptyLayers) {
						JOptionPane.showMessageDialog(null,"There are no objects to run!","Error",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					System.out.println("Running.....");
					
					new MyProgressBar(ventana);
	
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
				for (Layer layer : Config.LAYERS)
					layer.clean();
				
				Config.OBJCOUNT = 0;
				panelPrincipal.repaint();
			}
		});
		btnCleanAll.setFocusable(false);
		btnCleanAll.setBounds(215, 5, 100, 35);
		panel_1.add(btnCleanAll);
	
		
		/********************************************************************
		 * INICIO PANEL DE CAPAS											*
		 ********************************************************************/
		
		JPanel panelLayers = new JPanel();
		panelLayers.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelLayers.setBounds(810, 45, 350, 295);
		getContentPane().add(panelLayers);
		panelLayers.setLayout(null);
		
		Label labelLayers = new Label("Layers" );
		labelLayers.setBounds(5, 10, 50, 17);
		panelLayers.add(labelLayers);
		
		Button btnAdd = new Button("Add");
		btnAdd.setFocusable(false);
		btnAdd.setBounds(60, 5, 50, 30);
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewLayer(ventana,null);
				
			}
		});
		panelLayers.add(btnAdd);
		
		Button btnRemove = new Button("Del");
		btnRemove.setFocusable(false);
		btnRemove.setBounds(112, 5, 50, 30);
		panelLayers.add(btnRemove);
				
		Button btnEdit = new Button("Edit");
		btnEdit.setFocusable(false);
		btnEdit.setBounds(164, 5, 50, 30);
		panelLayers.add(btnEdit);
			
		listLayers = new JList(Config.LISTLAYERS);
		listLayers.setFont(new Font("",0,14));
		listLayers.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        	Config.getLayer(listLayers.getSelectedIndex());
		        	repaint();
		    }
		});
		
		ScrollPane scrollPaneLayers = new ScrollPane();
		scrollPaneLayers.add(listLayers);
		scrollPaneLayers.setBounds(30, 50, 300, 220);
		panelLayers.add(scrollPaneLayers);
		
		btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (Config.LISTLAYERS.size() == 1) {
					JOptionPane.showMessageDialog(null, "Can not remove last Layer!");
					return;
				}
				
				if (!listLayers.isSelectionEmpty())		
					if (JOptionPane.showConfirmDialog(null, "Delete layer?") == 0)
						Config.removeLayer(listLayers.getSelectedIndex());
			}
		});

		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!listLayers.isSelectionEmpty())
					new NewLayer(ventana,Config.getLayer(listLayers.getSelectedIndex()));
					//JOptionPane.showMessageDialog(null, "Can not remove last Layer!");
				
			}
		});
		/********************************************************************
		 * FIN PANEL DE CAPAS												*
		 ********************************************************************/
		
		
		/********************************************************************
		 * INICIO PANEL DE OBJETOS											*
		 ********************************************************************/
		
		JPanel panelObjs = new JPanel();
		panelObjs.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelObjs.setBounds(810, 345, 350, 300);
		getContentPane().add(panelObjs);
		panelObjs.setLayout(null);
		
		Label label = new Label("Objects Detected in Layer :");
		label.setBounds(5, 10, 180, 17);
		panelObjs.add(label);
		
		final JList listLayer  = new JList(Config.OBJSLIST);
		
		listLayer.setFont(new Font("",0,10));
		listLayer.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		        	if (Config.ACTIVELAYER != null) {
		        		ObjGeom obj = Config.ACTIVELAYER.getObj(listLayer.getSelectedIndex());
		        		if (obj != null)
		        			new SetClass(obj, Config.ACTIVELAYER);
		        	}	
		        }
		    }
		});
		
		ScrollPane scrollPaneObjs = new ScrollPane();
		scrollPaneObjs.add(listLayer);
		scrollPaneObjs.setBounds(30, 50, 300, 220);
		panelObjs.add(scrollPaneObjs);
		
		/********************************************************************
		 * FIN PANEL DE OBJETOS												*
		 ********************************************************************/
		
				
		addKeyListener(new KeyActions(this));
		setMenuBar(new MenuBarAWT(this).createMenuBar());
		panelPrincipal = new MainPanel(800, 600);
		panel.add(panelPrincipal);
		
		setVisible(true);
	}
	
	public void selectActiveLayerInList() {
		listLayers.setSelectedIndex(Config.getActiveLayerIndex());
	}
	
	public void disableMouseActions() {
		panelPrincipal.mouseOFF();
	}
	
	public void enableMouseActions() {
		panelPrincipal.mouseON();
	}
	
	public void paint(Graphics g) {		
		paintComponents(g);
    }

	public static void main(String[] args) {
		new Simulator();
		System.gc();				
	}
	
}

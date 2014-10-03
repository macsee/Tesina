package GUI;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JDialog;

//import com.hp.hpl.jena.sparql.vocabulary.DOAP;

import UTILS.Config;
import UTILS.Layer;
import UTILS.ObjGeom;


public class SetClass extends JDialog{
	
	public static int locationX = 700;
	public static int locationY = 100;
	private Choice choiceClass;
	private Choice choiceWidth;
	private Choice choiceElongation;
	private Choice choiceLength;
	private Choice choiceSurface;
	private Choice choiceTexture;
	private Choice choiceDensity;
	private Choice choiceForm;
	private Choice choiceAlign;
	private Choice choiceDisc;
	private Choice choiceSame;
	private LinkedList<ObjGeom> listObjs;
	private Checkbox chk;
	
	public SetClass(final ObjGeom obj, final Layer layer) {
		
		setTitle("Settings for P"+obj.getId());
		setModal(true);
		setLocation(locationX, locationY);
		//setLocationRelativeTo (null);
		setSize(400, 570);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		Panel panel = new Panel();
		panel.setLayout(null);
		add(panel);
		
		Label labelClass = new Label("Class:");
		labelClass.setBounds(10,15,90,20);
		panel.add(labelClass);
		
		choiceClass = new Choice();
		choiceClass.setBounds(100,10,250,30);
		
		for (String clase : Config.ONTCLASSES)
			choiceClass.add(clase);
		

		choiceClass.setFocusable(false);
		choiceClass.select(obj.getCLASE());
		panel.add(choiceClass);
				
		Label labelSurface = new Label("Surface:");
		labelSurface.setBounds(10,55,90,20);
		panel.add(labelSurface);
		
		choiceSurface = new Choice();
		choiceSurface.setBounds(100,50,100,30);
		choiceSurface.add("");
		choiceSurface.add("Small");
		choiceSurface.add("Medium");
		choiceSurface.add("Large");
		choiceSurface.setFocusable(false);
		choiceSurface.select(translateInv(obj.getSURFACE()));
		panel.add(choiceSurface);
		
		
		Label labelLength = new Label("Length:");
		labelLength.setBounds(10,95,90,20);
		panel.add(labelLength);
		
		choiceLength = new Choice();
		choiceLength.setBounds(100,90,100,30);
		choiceLength.add("");
		choiceLength.add("Small");
		choiceLength.add("Medium");
		choiceLength.add("Large");
		choiceLength.setFocusable(false);
		choiceLength.select(translateInv(obj.getLENGTH()));
		panel.add(choiceLength);
		
		
		Label labelWidth = new Label("Width:");
		labelWidth.setBounds(10,135,90,20);
		panel.add(labelWidth);
		
		choiceWidth = new Choice();
		choiceWidth.setBounds(100,130,100,30);
		choiceWidth.add("");
		choiceWidth.add("Small");
		choiceWidth.add("Medium");
		choiceWidth.add("Large");
		choiceWidth.setFocusable(false);
		choiceWidth.select(translateInv(obj.getWIDTH()));
		panel.add(choiceWidth);
		
		
		Label labelElongation = new Label("Elongation:");
		labelElongation.setBounds(10,175,90,20);
		panel.add(labelElongation);
		
		choiceElongation = new Choice();
		choiceElongation.setBounds(100,170,100,30);
		choiceElongation.add("");
		choiceElongation.add("Small");
		choiceElongation.add("Medium");
		choiceElongation.add("Large");
		choiceElongation.setFocusable(false);
		choiceElongation.select(translateInv(obj.getELONGATION()));
		panel.add(choiceElongation);
		
		
		Label labelForm = new Label("Form:");
		labelForm.setBounds(10,215,90,20);
		panel.add(labelForm);
		
		choiceForm = new Choice();
		choiceForm.setBounds(100,210,100,30);
		choiceForm.add("");
		choiceForm.add("Other");
		choiceForm.add("Square");
		choiceForm.add("Circle");
		choiceForm.add("Lineal");
		choiceForm.add("Rectangle");
		choiceForm.add("Triangle");
		choiceForm.setFocusable(false);
		choiceForm.select(translateInv(obj.getFORM()));
		panel.add(choiceForm);
		
		
		Label labelTexture = new Label("Texture:");
		labelTexture.setBounds(10,255,90,20);
		panel.add(labelTexture);
		
		choiceTexture= new Choice();
		choiceTexture.setBounds(100,250,100,30);
		choiceTexture.add("");
		choiceTexture.add("Heterogeneous");
		choiceTexture.add("Homogeneous");
		choiceTexture.setFocusable(false);
		choiceTexture.select(translateInv(obj.getTEXTURE()));
		panel.add(choiceTexture);
		
		
		Label labelDensity = new Label("Density:");
		labelDensity.setBounds(10,295,90,20);
		panel.add(labelDensity);
		
		choiceDensity = new Choice();
		choiceDensity.setBounds(100,290,100,30);
		choiceDensity.add("");
		choiceDensity.add("Low");
		choiceDensity.add("Medium");
		choiceDensity.add("High");
		choiceDensity.setFocusable(false);
		choiceDensity.select(translateInv(obj.getDENSITY()));
		panel.add(choiceDensity);
		
		
		Label labelSame = new Label("Same as:");
		labelSame.setBounds(10,335,90,20);
		panel.add(labelSame);
		
		choiceSame = new Choice();
		choiceSame.setBounds(100,330,100,30);
		choiceSame.add("");
		
		listObjs = new LinkedList<ObjGeom>();
		
		for (Layer LayerS : Config.LAYERS)
			listObjs.addAll(LayerS.getObjsGeom());
		
		listObjs.remove(obj); // Quito el objeto actual de la lista de Same
		
		for (ObjGeom objgeom : listObjs)
			choiceSame.add("P"+objgeom.getId());
		
		choiceSame.setFocusable(false);
		
		if (obj.getSAMEIND() != null)
			choiceSame.select("P"+obj.getSAMEIND());
		
		panel.add(choiceSame);
		
		Label labelAlign = new Label("Alignment:");
		labelAlign.setBounds(10,375,90,20);
		panel.add(labelAlign);
		
		choiceAlign = new Choice();
		choiceAlign.setFocusable(false);
		choiceAlign.setBounds(100,370,100,30);
		choiceAlign.add("");
		choiceAlign.add("Yes");
		choiceAlign.add("No");
		choiceAlign.select(obj.getALIGN());
		panel.add(choiceAlign);
		
		Label labelDisc = new Label("Discontinue:");
		labelDisc.setBounds(10,415,90,20);
		panel.add(labelDisc);
		
		choiceDisc = new Choice();
		choiceDisc.setFocusable(false);
		choiceDisc.setBounds(100,410,100,30);
		choiceDisc.add("");
		choiceDisc.add("Yes");
		choiceDisc.add("No");
		choiceDisc.select(obj.getDISCONTINUE());
		panel.add(choiceDisc);
		
		
		Label labelUse = new Label("Force object detection:");
		labelUse.setBounds(10,455,150,20);
		panel.add(labelUse);
		
		chk = new Checkbox();
		chk.setBounds(180,450,100,30);
		chk.setFocusable(false);
		chk.setState(obj.classificationForced());
		panel.add(chk);
		
		Button button = new Button("Ok");
		button.setBounds((getWidth()/2) - 50, (getHeight()-20-40) - 10, 100, 40);
		button.setFocusable(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				saveParamsInObj(obj);
				dispose();
				
			}
		});
		panel.add(button);
		
		button.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					saveParamsInObj(obj);
					dispose();
	            }
				
				if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
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
		
	
		setVisible(true);
	}

	/**
	 * @param args
	 * 
	 */
	
	public void saveParamsInObj(ObjGeom obj) {
		
		obj.setCLASE(choiceClass.getSelectedItem());
		obj.setWIDTH(translate(choiceWidth.getSelectedItem()));
		obj.setELONGATION(translate(choiceElongation.getSelectedItem()));
		obj.setLENGTH(translate(choiceLength.getSelectedItem()));
		obj.setSURFACE(translate(choiceSurface.getSelectedItem()));
		obj.setTEXTURE(translate(choiceTexture.getSelectedItem()));
		obj.setDENSITY(translate(choiceDensity.getSelectedItem()));
		obj.setFORM(translate(choiceForm.getSelectedItem()));
		obj.setALIGN(choiceAlign.getSelectedItem());
		obj.setDISCONTINUE(choiceDisc.getSelectedItem());
		
		if (chk.getState())
			obj.setCLASSIFIABLE();
		else
			obj.unSetCLASSIFIABLE();
		
		if (choiceSame.getSelectedIndex() != 0) {
			ObjGeom obj1 = listObjs.get(choiceSame.getSelectedIndex()-1);
			obj.setSAMEIND(obj1.getId()); // Seteo mutuamente los mismos individuos
			obj1.setSAMEIND(obj.getId());
		}	
		else
			obj.setSAMEIND(null);
		
		Config.updateDefaultList(obj);
		Config.fillDefaultList();
		
	}
	
	public String translate(String s){
		
		if (s.equals("Small") || s.equals("Low"))
			return "Petit";
		
		else if (s.equals("Medium"))
			return "Moyen";
		
		else if (s.equals("Large") || s.equals("High"))
			return "Grand";
		
		else if (s.equals("Heterogeneous"))
			return "Heterogene";
		
		else if (s.equals("Homogeneous"))
			return "Homogene";
		
		else if (s.equals("Other"))
			return "Autre";
		
		else if (s.equals("Square"))
			return "Carre";
		
		else if (s.equals("Circle"))
			return "Circulaire";
		
		else if (s.equals("Lineal"))
			return "Lineaire";
		
		else if (s.equals("Rectangle"))
			return "Rectangulaire";
		
		else if (s.equals("Triangle"))
			return "Triangulaire";
		
		else 
			return "";
	}
	
public int translateInv(String s){
		
		if (s.equals("Petit"))
			return 1;
		
		else if (s.equals("Moyen"))
			return 2;
		
		else if (s.equals("Grand"))
			return 3;
		
		else if (s.equals("Heterogene"))
			return 1;
		
		else if (s.equals("Homogene"))
			return 2;
				
		else if (s.equals("Autre"))
			return 1;
		
		else if (s.equals("Carre"))
			return 2;
		
		else if (s.equals("Circulaire"))
			return 3;
		
		else if (s.equals("Lineaire"))
			return 4;
		
		else if (s.equals("Rectangulaire"))
			return 5;
		
		else if (s.equals("Triangulaire"))
			return 6;
		
		else 
			return 0;
	}
	
	public static void main(String[] args) { 
		new SetClass(new ObjGeom(), null);
	}

}

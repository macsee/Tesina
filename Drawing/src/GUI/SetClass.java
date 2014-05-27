package GUI;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import UTILS.ObjGeom;
import UTILS.Config;


public class SetClass extends Dialog{
	
	public static int locationX = 100;
	public static int locationY = 100;
	
	public SetClass(final ObjGeom obj) {
		
		super(new Frame(), "Define attributes");
		setVisible(true);
		setLocation(locationX, locationY);
		setSize(400, 450);
		setResizable(false);
		Panel panel = new Panel();
		panel.setLayout(null);
		add(panel);
		
		Label labelClass = new Label("Class:");
		labelClass.setBounds(10,15,90,20);
		panel.add(labelClass);
		
		final Choice choiceClass = new Choice();
		choiceClass.setBounds(100,10,250,30);
		
		for (String clase : Config.ONTCLASSES)
			choiceClass.add(clase);
		
//		choiceClass.add("Unknown Class");
//		choiceClass.add("House");
//		choiceClass.add("uArea");
//		choiceClass.add("wHousing");
//		choiceClass.add("Batiment");
//		choiceClass.add("Batiment Activite");
//		choiceClass.add("Batiment Collectif");
//		choiceClass.add("Batiment Collectif Barre");
//		choiceClass.add("Batiment Collectif Tour");
//		choiceClass.add("Batiment Rural");
//		choiceClass.add("Batiment Autre");
//		choiceClass.add("Pavillon");
//		choiceClass.add("Eau");
//		choiceClass.add("Bassin Artifiel");
//		choiceClass.add("Canal");
//		choiceClass.add("Course d'Eau");
//		choiceClass.add("Entendeu d'Eau");
//		choiceClass.add("Equipement Divers");
//		choiceClass.add("Cimetiere");
//		choiceClass.add("Ilot de Circulation");
//		choiceClass.add("Parking");
//		choiceClass.add("Parking Grand");
//		choiceClass.add("Parking Petit");
//		choiceClass.add("Piscine Exterieure Privee");
//		choiceClass.add("Piscine Exterieure Publique");
//		choiceClass.add("Place");
//		choiceClass.add("Sol Nu");
//		choiceClass.add("Terrain Sport");
//		choiceClass.add("Tronçon ou Voie");
//		choiceClass.add("Chemin");
//		choiceClass.add("Chemin de Fer");
//		choiceClass.add("Route");
//		choiceClass.add("Route Grande Vitesse");
//		choiceClass.add("Route Autre");
//		choiceClass.add("Vegetation");
//		choiceClass.add("Arbre");
//		choiceClass.add("Jardin");
//		choiceClass.add("Parcelle Agricole");
//		choiceClass.add("Pelouse");
//		choiceClass.add("Surface Boisee");
//		choiceClass.add("TU Continu");
//		choiceClass.add("TU Discontinu");
//		choiceClass.add("TU Discontinu Collectif");
//		choiceClass.add("TU Discontinu Individuel");
//		choiceClass.add("TU Discontinu Individuel Densite Faible");
//		choiceClass.add("TU Discontinu Individuel Densite Moyenne");
//		choiceClass.add("TU Discontinu Individuel Densite Forte");
//		choiceClass.add("Zone Aeroportuaire");
//		choiceClass.add("Zone d'Extraction");
//		choiceClass.add("Zone Industrielle Commerciale et Tertiaire");
//		choiceClass.add("Zone Sportif e Loisir");
//		choiceClass.add("Ensamble d'Arbres");
//		choiceClass.add("Alignement d'Arbres");
//		choiceClass.add("Groupe d'Arbres");
//		choiceClass.add("Parc");
//		choiceClass.add("Parc Agglom");
//		choiceClass.add("Parc Public");
//		choiceClass.add("Parc Square");
		choiceClass.setFocusable(false);
		choiceClass.select(obj.getCLASE());
		panel.add(choiceClass);
				
		Label labelSurface = new Label("Surface:");
		labelSurface.setBounds(10,55,90,20);
		panel.add(labelSurface);
		
		final Choice choiceSurface = new Choice();
		choiceSurface.setBounds(100,50,100,30);
		choiceSurface.add("");
		choiceSurface.add("Small");
		choiceSurface.add("Medium");
		choiceSurface.add("Big");
		choiceSurface.setFocusable(false);
		choiceSurface.select(obj.getSURFACE());
		panel.add(choiceSurface);
		
		
		Label labelLength = new Label("Length:");
		labelLength.setBounds(10,95,90,20);
		panel.add(labelLength);
		
		final Choice choiceLength = new Choice();
		choiceLength.setBounds(100,90,100,30);
		choiceLength.add("");
		choiceLength.add("Small");
		choiceLength.add("Medium");
		choiceLength.add("Big");
		choiceLength.setFocusable(false);
		choiceLength.select(obj.getLENGTH());
		panel.add(choiceLength);
		
		
		Label labelWidth = new Label("Width:");
		labelWidth.setBounds(10,135,90,20);
		panel.add(labelWidth);
		
		final Choice choiceWidth = new Choice();
		choiceWidth.setBounds(100,130,100,30);
		choiceWidth.add("");
		choiceWidth.add("Small");
		choiceWidth.add("Medium");
		choiceWidth.add("Big");
		choiceWidth.setFocusable(false);
		choiceWidth.select(obj.getWIDTH());
		panel.add(choiceWidth);
		
		
		Label labelElongation = new Label("Elongation:");
		labelElongation.setBounds(10,175,90,20);
		panel.add(labelElongation);
		
		final Choice choiceElongation = new Choice();
		choiceElongation.setBounds(100,170,100,30);
		choiceElongation.add("");
		choiceElongation.add("Small");
		choiceElongation.add("Medium");
		choiceElongation.add("Big");
		choiceElongation.setFocusable(false);
		choiceElongation.select(obj.getELONGATION());
		panel.add(choiceElongation);
		
		
		Label labelForm = new Label("Form:");
		labelForm.setBounds(10,215,90,20);
		panel.add(labelForm);
		
		final Choice choiceForm = new Choice();
		choiceForm.setBounds(100,210,100,30);
		choiceForm.add("");
		choiceForm.add("Other");
		choiceForm.add("Square");
		choiceForm.add("Circle");
		choiceForm.add("Lineal");
		choiceForm.add("Rectangle");
		choiceForm.add("Triangle");
		choiceForm.setFocusable(false);
		choiceForm.select(obj.getFORM());
		panel.add(choiceForm);
		
		
		Label labelTexture = new Label("Texture:");
		labelTexture.setBounds(10,255,90,20);
		panel.add(labelTexture);
		
		final Choice choiceTexture= new Choice();
		choiceTexture.setBounds(100,250,100,30);
		choiceTexture.add("");
		choiceTexture.add("Heterogeneous");
		choiceTexture.add("Homogeneous");
		choiceTexture.setFocusable(false);
		choiceTexture.select(obj.getTEXTURE());
		panel.add(choiceTexture);
		
		
		Label labelDensity = new Label("Density:");
		labelDensity.setBounds(10,295,90,20);
		panel.add(labelDensity);
		
		final Choice choiceDensity = new Choice();
		choiceDensity.setBounds(100,290,100,30);
		choiceDensity.add("");
		choiceDensity.add("Low");
		choiceDensity.add("Medium");
		choiceDensity.add("High");
		choiceDensity.setFocusable(false);
		choiceDensity.select(obj.getDENSITY());
		panel.add(choiceDensity);
		
		
		Label labelSame = new Label("Same as:");
		labelSame.setBounds(10,335,90,20);
		panel.add(labelSame);
		
		final Choice choiceSame = new Choice();
		choiceSame.setBounds(100,330,100,30);
		choiceSame.add("");
		
		for (ObjGeom objgeom : Config.ACTIVELAYER.getObjsGeom())
			choiceSame.add("P"+objgeom.getId());
		
		choiceSame.setFocusable(false);
		//choiceDensity.select(obj.getClase());
		panel.add(choiceSame);
		
		
		
		choiceClass.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				choiceClass.setFocusable(false);
				
				if (choiceClass.getSelectedItem() != "Unknown Class") {
					choiceSurface.setEnabled(false);
					choiceWidth.setEnabled(false);
					choiceLength.setEnabled(false);
					choiceElongation.setEnabled(false);
					choiceDensity.setEnabled(false);
					choiceForm.setEnabled(false);
					choiceTexture.setEnabled(false);
					choiceSame.setEnabled(false);
				}
				else {
					choiceSurface.setEnabled(true);
					choiceWidth.setEnabled(true);
					choiceLength.setEnabled(true);
					choiceElongation.setEnabled(true);
					choiceDensity.setEnabled(true);
					choiceForm.setEnabled(true);
					choiceTexture.setEnabled(true);
					choiceSame.setEnabled(true);
				}
				
			}
		});
		
		
		Button button = new Button("Aceptar");
		button.setBounds((getWidth()/2) - 50, (getHeight()-20-40) - 10, 100, 40);
		button.setFocusable(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				obj.setCLASE(choiceClass.getSelectedItem());
				obj.setWIDTH(choiceWidth.getSelectedItem());
				obj.setELONGATION(choiceElongation.getSelectedItem());
				obj.setLENGTH(choiceLength.getSelectedItem());
				obj.setSURFACE(choiceSurface.getSelectedItem());
				obj.setTEXTURE(choiceTexture.getSelectedItem());
				obj.setDENSITY(choiceDensity.getSelectedItem());
				obj.setFORM(choiceForm.getSelectedItem());
				
				Config.ACTIVELAYER.updateDefaultList(obj);
				dispose();
			}
		});
		panel.add(button);
		
		button.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
	            {
					obj.setCLASE(choiceClass.getSelectedItem());
					Config.ACTIVELAYER.updateDefaultList(obj);
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
		
		addWindowListener(new WindowAdapter(){
			  public void windowClosing(WindowEvent we){
				//System.exit(0);
				dispose();
			  }
		});
	}

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SetClass(null);
	}

}

package UTILS;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.DefaultListModel;

import GUI.MyProgressBar;


public class Config {
	
	public static LinkedList<Layer> LAYERS = new LinkedList<Layer>();
	
	public static DefaultListModel LISTLAYERS = new DefaultListModel();
	
	public static DefaultListModel OBJSLIST = new DefaultListModel();
	
	public static Layer ACTIVELAYER = null;
	
	public static Layer THR1 = null;
	
	public static Layer HR1 = null;
	
	public static Layer HR2 = null;
	
    public static Point MOUSE_POINT = null;
    
    public static int OBJCOUNT = 0;
    
    public static boolean DRAW_INTERSECTION = false;
    
    public static boolean DRAW_DIFFERENCE = false; 
    
    public static String OS = System.getProperty("os.name").toLowerCase();
    
    public static ArrayList<String> OUT = new ArrayList<String>();
    
    public static String BASIC_CLASS = "SpatialObject";
    
    public static boolean FORCEDETECTION = false;
    
    public static boolean GETINFERRED = false;
    
    public static boolean GETINFERREDREL = false;
    
    public static Integer getActiveLayerIndex() {
    	return LAYERS.indexOf(ACTIVELAYER);
    }
    
    public static Layer getLayer(Integer index) {
    	Layer layer = LAYERS.get(index);
    	ACTIVELAYER = layer;
    	fillDefaultList();
//    	layer.allowDrawing();
    	return layer;
    }
    
    public static void addLayer(String name, String type) {
    	Layer layer = new Layer(name,type);
    	LISTLAYERS.add(LAYERS.size(), name+" - "+type);
    	LAYERS.add(layer);
    	ACTIVELAYER = layer;
    }
    
    public static void removeLayer(Integer index) {
    	LAYERS.remove(LAYERS.get(index));
    	LISTLAYERS.remove(index);
    	getLayer(0);
    }
    
    public static void refreshLayerList(Layer layer) {
    	int index = LAYERS.indexOf(layer);
    	LISTLAYERS.remove(index);
		LISTLAYERS.add(index, layer.LAYERID+" - "+layer.LAYER_RESOLUTION);
    }
        
    public static void fillDefaultList() { 
    	
    	OBJSLIST.removeAllElements();
    	
    	for (ObjGeom obj : ACTIVELAYER.getObjsGeom())
    		OBJSLIST.addElement("Polygon"+obj.getId()+" - "+obj.getCLASE());
    	
    }
        
    public static void addToDefaultList(ObjGeom obj) {
		
    	String element = "Polygon"+obj.getId()+" - "+obj.getCLASE();
    	
    	if (OBJSLIST.contains(element))
    		return;
    	
//		int i;
//		for (i=0;i < OBJSLIST.size();i++) {
//			if (((String) OBJSLIST.get(i)).contains(String.valueOf(obj.getId()))) //Compruebo que el objeto no este agregado a la lista
//				return;																 // esto ocurre al deshacer acciones	
//		}
		
    	OBJSLIST.addElement(element);
    	
//		OBJSLIST.addElement("Polygon "+obj.getId()+" - "+obj.getCLASE());
    	
	}
    
    public static void removeFromDefaultList(Integer index) {
    	OBJSLIST.remove(index);
    }
    
    
    public static void updateDefaultList(ObjGeom obj) { //Para cuando se cambia una clase
    	if (ACTIVELAYER.getObjsGeom().contains(obj)) { // Si el obj esta incluído en la capa actual refresco la lista, sino no.
    		OBJSLIST.remove(obj.getLocalID());
    		OBJSLIST.add(obj.getLocalID(), "Polygon "+obj.getId()+" - "+obj.getCLASE());
    	}	
	}
    
    public static void cleanDefaultList() {
    	OBJSLIST.removeAllElements();
    }
    
    
    public static Set<String> ONTCLASSES = new HashSet<String>(Arrays.asList(new String[] {
//    		"GeoObject",
//    		"House",
//    		"uArea",
//    		"wHousing",
    		"SpatialObject",
    		"Batiment",
    		"Batiment_Activite",
    		"Batiment Autre",
    		"Batiment_Collectif",
    		"Batiment_Collectif_Barre",
    		"Batiment_Collectif_Tour",
    		"Batiment Rural",
    		"Pavillon",
    		"Eau",
    		"Bassin_Artificiel",
    		"Canal",
    		"Course_Eau",
    		"Entendeu_Eau",
    		"Equipement_Divers",
    		"Ilot_Circulation",
    		"Cimetiere",
    		"Parking",
    		"Parking_Grand",
    		"Parking_Petit",
    		"Piscine_Exterieure",
    		"Piscine_Exterieure_Privee",
    		"Piscine_Exterieure_Publique",
    		"Place",
    		"Sol_Nu",
    		"Terrain_Sport",
    		"Terrain_Sport_Football",
    		"Terrain_Sport_Tennis",
    		"Tronçon_ou_Voie",
    		"Chemin",
    		"Chemin_de_Fer",
    		"Route",
    		"Route_Autre",
    		"Route_Grande_Vitesse",
    		"Vegetation",
    		"Arbre",
    		"Jardin",
    		"Parcelle_Agricole",
    		"Pelouse",
    		"Surface_Boisee",
    		"Surface_Artificiel",
    		"TU",
    		"TU_Continu",
    		"TU_Discontinu",
    		"TU_Discontinu_Collectif",
    		"TU_Discontinu_Individuel",
    		"TU_Disc_Ind_Densite_Faible",
    		"TU_Disc_Ind_Densite_Moyenne",
    		"TU_Disc_Ind_Densite_Forte",
    		"Zone_Aeroportuaire",
    		"Zone_Extraction",
    		"Zone_Ind_Com_Tert",
    		"Zone_Sportif_Loisir",
    		"Surface_Vegetal",
    		"Ensemble_Arbres",
    		"Alignement_Arbres",
    		"Groupe_Arbres",
    		"Parc",
    		"Parc_Agglom",
    		"Parc_Public",
    		"Parc_Square"
    		}));
  
}

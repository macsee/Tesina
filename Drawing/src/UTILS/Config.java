package UTILS;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import org.semanticweb.owlapi.model.OWLClassExpression;


public class Config {
	
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

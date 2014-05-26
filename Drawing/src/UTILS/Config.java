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
	
    public static Point MOUSE_POINT = null;
    
    public static int OBJCOUNT = 0;
    
    public static boolean DRAW_INTERSECTION = false;
    
    public static boolean DRAW_DIFFERENCE = false; 
    
    public static String OS = System.getProperty("os.name").toLowerCase();
    
    public static ArrayList<String> OUT = new ArrayList<String>();
    
    public static String BASIC_CLASS = "GeoObject";
      
    public static Set<String> ONTCLASSES = new HashSet<String>(Arrays.asList(new String[] {
    		"GeoObject",
    		"House",
    		"uArea",
    		"wHousing",
//    		"Batiment",
//    		"Batiment Activite",
//    		"Batiment Collectif",
//    		"Batiment Collectif Barre",
//    		"Batiment Collectif Tour",
//    		"Batiment Rural",
//    		"Batiment Autre",
//    		"Pavillon",
//    		"Eau",
//    		"Bassin Artifiel",
//    		"Canal",
//    		"Course d'Eau",
//    		"Entendeu d'Eau",
//    		"Equipement Divers",
//    		"Cimetiere",
//    		"Ilot de Circulation",
//    		"Parking",
//    		"Parking Grand",
//    		"Parking Petit",
//    		"Piscine Exterieure Privee",
//    		"Piscine Exterieure Publique",
//    		"Place",
//    		"Sol Nu",
//    		"Terrain Sport",
//    		"Tronçon ou Voie",
//    		"Chemin",
//    		"Chemin de Fer",
//    		"Route",
//    		"Route Grande Vitesse",
//    		"Route Autre",
//    		"Vegetation",
//    		"Arbre",
//    		"Jardin",
//    		"Parcelle Agricole",
//    		"Pelouse",
//    		"Surface Boisee",
//    		"TU Continu",
//    		"TU Discontinu",
//    		"TU Discontinu Collectif",
//    		"TU Discontinu Individuel",
//    		"TU Discontinu Individuel Densite Faible",
//    		"TU Discontinu Individuel Densite Moyenne",
//    		"TU Discontinu Individuel Densite Forte",
//    		"Zone Aeroportuaire",
//    		"Zone d'Extraction",
//    		"Zone Industrielle Commerciale et Tertiaire",
//    		"Zone Sportif e Loisir",
//    		"Ensamble d'Arbres",
//    		"Alignement d'Arbres",
//    		"Groupe d'Arbres",
//    		"Parc",
//    		"Parc Aglom",
//    		"Parc Public",
//    		"Parc Square"
    		}));
  
}

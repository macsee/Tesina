package OWL;

import java.awt.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.mindswap.pellet.DependencySet;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;
import UTILS.Config;
import UTILS.ObjGeom;

public class CM8toOWL {

	/**
	 * @param args
	 */
	
	private static String input_ontology = "/Users/Macsee/Desktop/Examples/Ontologies/input_ont.owl";
	private static String output_ontology = "/Users/Macsee/Desktop/Examples/Ontologies/output_ont.owl";
	private myOWL myOWL;
	
	private Map<OWLNamedIndividual,Set<OWLClassExpression>> ASSERTED_CLASS_RELS = new HashMap<OWLNamedIndividual,Set<OWLClassExpression>>();
	private Map<OWLNamedIndividual, Set<OWLClassExpression>> ASSERTED_CLASS_OBJS = new HashMap<OWLNamedIndividual, Set<OWLClassExpression>>();
	
	private Map<OWLNamedIndividual, Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>>> ASSERTED_PROP_OBJS = new HashMap<OWLNamedIndividual, Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>>>();
	private Map<OWLNamedIndividual, Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>>> ASSERTED_PROP_RELS = new HashMap<OWLNamedIndividual, Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>>>();
	
	private Map<OWLNamedIndividual, Integer> CountObjs = new HashMap<OWLNamedIndividual, Integer>();
	private Map<OWLNamedIndividual, ObjGeom> IND_OBJ = new HashMap<OWLNamedIndividual, ObjGeom>();
	
	public CM8toOWL() {
		// TODO Auto-generated constructor stub
		myOWL = new myOWL();
		myOWL.loadOntologyFromFile(new File(input_ontology));
	}
	
	public OWLNamedIndividual assertIndividual(ObjGeom obj) {  // Convierto los poligonos en individuos
	
		Set<OWLClassExpression> set = new HashSet<OWLClassExpression>();
		OWLNamedIndividual ind = myOWL.getIndividual("P"+obj.getId());
		String clase = obj.getCLASE();
		
		myOWL.addToIndividual(ind, myOWL.getClass("GeoObject"));
		set.add(myOWL.getClass("GeoObject"));
		
		if (clase != "GeoObject") {	
			myOWL.addToIndividual(ind, myOWL.getClass(clase));
			set.add(myOWL.getClass(clase));
		}	
			// PUEDO AGREGAR MAS TIPOS A UN INDIVIDUO CREANDO MUCHAS INSTANCIAS CON EL MISMO NOMBRE Y DISTINTOS TIPOS
	
		CountObjs.put(ind, 0); //Cuento la cantidad de veces que se relaciona con una clase
		ASSERTED_CLASS_OBJS.put(ind, set); // Guardo información de los individuos y sus clases
		IND_OBJ.put(ind, obj); // Guardo la relación entre un ObjGeom y un OWLIndividual
		return ind;	
	} 

	public OWLNamedIndividual getIndividual(ObjGeom obj) {
		return myOWL.getIndividual("P"+obj.getId());
	}
	
	public ObjGeom getObjGeom(OWLNamedIndividual ind) {
		
		return IND_OBJ.get(ind);
	
	}
	
	/************************************************************************************************************
	*	Recordar que estoy creando una nueva relacion "on the fly" por cada primitiva CM8 						*
	*	Debo plantearme si debo crear una conjuncion de clases con las primitivas antes de crear la relacion	*
	*	o si lo dejo asi. También tengo que investigar como agregar mas de una clase a una instancia			*
	*************************************************************************************************************/
	
	public void assertRelation(String cm8, ObjGeom obj1, ObjGeom obj2) {
		
		String rel = "SR_P"+obj1.getId()+"_P"+obj2.getId();
		
		OWLClassExpression relClass = myOWL.getClass(cm8);
		OWLNamedIndividual indRel = myOWL.defineInstance(rel, relClass); // hago una instancia representando la relacion CM8
		
		Set<OWLClassExpression> setRel = new HashSet<OWLClassExpression>();
		setRel.add(relClass);
		
		if (!ASSERTED_CLASS_RELS.containsKey(indRel)){
			
			Set<OWLNamedIndividual> setInd1 = new HashSet<OWLNamedIndividual>();
			Set<OWLNamedIndividual> setInd2 = new HashSet<OWLNamedIndividual>();
			
			OWLObjectPropertyExpression from = myOWL.getObjectProperty("from");
			OWLObjectPropertyExpression to = myOWL.getObjectProperty("to");
			
			OWLNamedIndividual ind1 = getIndividual(obj1);
			setInd1.add(ind1);
			
			OWLNamedIndividual ind2 = getIndividual(obj2);
			setInd2.add(ind2);
			
			OWLClassExpression relRCC8 = myOWL.getClass("RCC8");
			
			myOWL.addToIndividual(indRel,relRCC8); // hago que el mismo individuo sea miembro de RCC8
			myOWL.defineObjectProperty(from, indRel, ind1); // rel_P1_P2 : relaciona obj1 con obj2 con la relacion CM8 de argumento
			myOWL.defineObjectProperty(to, indRel, ind2);
		
			Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>> mapObj = new HashMap();
			mapObj.put(from, setInd1);
			mapObj.put(to, setInd2);
					
			ASSERTED_PROP_RELS.put(indRel, mapObj); // Guardo informacion sobre las relaciones entre las Spatial Relationships
											// y los ObjGeom
			
			setRel.add(relRCC8); 
			
			ASSERTED_CLASS_RELS.put(indRel, setRel); // Guardo en un conjunto todas las clases a las que pertenece la relacion "indRel"
		}
		
		ASSERTED_CLASS_RELS.get(indRel).addAll(setRel);

	} 
	
	public void makeIndividualsDifferent() {
		myOWL.assertDifferentIndividuals(myOWL.getAllInds());
	}
	
	public void makeSameIndividual(ObjGeom ob1, ObjGeom ob2) {
		myOWL.assertSameIndividual(getIndividual(ob1), getIndividual(ob2));
	}
	
	public void assertMaxCard(ObjGeom ob, Integer cant, String clase, String prop) {
		
		OWLClassExpression restrict = myOWL.assertMaxRestriction(myOWL.getObjectProperty(prop), cant, myOWL.getClass(clase));
		myOWL.defineInstance("P"+ob.getId(), restrict);
	}
	
	public void assertMinCard(ObjGeom ob, Integer cant, String clase, String prop) {
		
		OWLClassExpression restrict = myOWL.assertMinRestriction(myOWL.getObjectProperty(prop), cant, myOWL.getClass(clase));
		myOWL.defineInstance("P"+ob.getId(), restrict);
	}
	
	public void assertExactCard(ObjGeom ob, Integer cant, String clase, String prop) {
		
		OWLClassExpression restrict = myOWL.assertExactRestriction(myOWL.getObjectProperty(prop), cant, myOWL.getClass(clase));
		myOWL.defineInstance("P"+ob.getId(), restrict);
	}
	
	public void assertProperty(ObjGeom ob, String prop, String value) {
		
		OWLNamedIndividual indObj = getIndividual(ob);
		OWLObjectProperty indRel = myOWL.getObjectProperty(prop);
		OWLNamedIndividual indValue = myOWL.getIndividual(value); // si no existe el individuo se crea
		
		myOWL.defineObjectProperty(indRel, indObj, indValue);
		
		Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>> mapObj = new HashMap();
		Set<OWLNamedIndividual> setInd = new HashSet<OWLNamedIndividual>();
		
		setInd.add(indValue);
		mapObj.put(indRel, setInd);	
		
		ASSERTED_PROP_OBJS.put(indObj,mapObj);
	}
	
	public void countObjsOfClass(String clase, String prop) {
		
		String propInv = "";
		
		if (prop.contentEquals("to"))
			propInv = "to-1";
		else if (prop.contentEquals("to-1"))
			propInv = "to";
		if (prop.contentEquals("from"))
			propInv = "from-1";
		else if (prop.contentEquals("from-1"))
			propInv = "from";
		
		Map<OWLNamedIndividual,Integer> count = new HashMap<OWLNamedIndividual,Integer>();
		
		Set<OWLNamedIndividual> setRel = myOWL.getInstancesOf(myOWL.getClass(clase)); // Busco los primitivas CM8 que son miembros de la clase "clase"
		
		for (OWLNamedIndividual ind : setRel) {
			
			Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>> map = ASSERTED_PROP_RELS.get(ind);
			Set<OWLNamedIndividual> setOWLInd = map.get(myOWL.getObjectProperty(propInv)); // Me quedo solo con los objetos geograficos que se relacionan mediante "to" con la primitiva CM8
			
			for (OWLNamedIndividual owlInd : setOWLInd)
				if (!count.containsKey(owlInd))
					count.put(owlInd, 1);
				else
					count.put(owlInd, count.get(owlInd)+1);
		}
		
			assertCardinalityRestriccions(count, clase, prop);
		
	}
	
	
	public void assertCardinalityRestriccions(Map<OWLNamedIndividual,Integer> count, String cm8, String prop) {
		
		for (Entry<OWLNamedIndividual, Integer> entry : count.entrySet())
				assertMaxCard(getObjGeom(entry.getKey()), entry.getValue(), cm8, prop);
	}
	
	public void getAssertedDataForObjects(ObjGeom obj) {
		
		OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();

		Set<OWLClassExpression> set = ASSERTED_CLASS_OBJS.get(getIndividual(obj));
		Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel = ASSERTED_PROP_OBJS.get(getIndividual(obj));
		
		printAll(set, setRel, null, "Polygon P"+obj.getId());
//		printClasses(set,null);

	}
	
	public void getInferredDataForObjects(ObjGeom obj) {
		
			OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
			
			Set<OWLClassExpression> set = myOWL.getInferredMembershipForInd(getIndividual(obj));
			Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel = myOWL.getInferredObjPropertiesFor(getIndividual(obj));
				
			printAll(set, setRel, obj, "Polygon P"+obj.getId()); //Imprimo todos los resultados
	    	
	}
	
	public void getAssertedDataForSpatialRelations() {
		
		OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
		
		for (OWLNamedIndividual rel : ASSERTED_CLASS_RELS.keySet()) {
		
			Set<OWLClassExpression> set = ASSERTED_CLASS_RELS.get(rel);
			Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel = ASSERTED_PROP_RELS.get(rel);
			
			printAll(set, setRel, null, rel.getIRI().getFragment());
			
		}
	    	
	}
	
	public void getInferredDataForSpatialRelations() {
		
		OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
		
		for (OWLNamedIndividual rel : ASSERTED_CLASS_RELS.keySet()) {
			 
	    	printHeader(rel.getIRI().getFragment());
	    	Set<OWLClassExpression> set = myOWL.getInferredMembershipForInd(rel);
	    	printClasses(set,null);
	    	
		}
	}
	
	void printHeader(String header) {
		Config.OUT.add("############################################## "+header+" ##############################################");
	}
	
	void printClasses(Set<OWLClassExpression> set, ObjGeom obj) {
		
		OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
		
    	Config.OUT.add("Classes: ");
    	Config.OUT.add("");
    	
    	if (set != null)
    		for (OWLClassExpression owlClass : set) {
    			Config.OUT.add(renderer.render(owlClass));
    			
    			if (obj != null){ // Imprimo el resultado en la lista de la capa activa
    				obj.setCLASE(owlClass. asOWLClass().getIRI().getFragment());
    				Config.ACTIVELAYER.updateDefaultList(obj);
    			}	
    		}	
    	else
    		Config.OUT.add("Inconsistent");
    			
    	Config.OUT.add("");
    	Config.OUT.add("");
    	
	}
	
	void printRelations(Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel) {
		
		OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
		
		Config.OUT.add("Object Properties: ");
    	Config.OUT.add("");
    	
		if (setRel != null)
			for (Entry<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> entry : setRel.entrySet())
				for (OWLObject ind : entry.getValue())
	    			Config.OUT.add(renderer.render(entry.getKey())+" "+renderer.render(ind));
		else
			Config.OUT.add("Inconsistent");
		
		
		Config.OUT.add("");
		Config.OUT.add("");
		
	}
	
	void printAll(Set<OWLClassExpression> set, Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel, ObjGeom obj, String header) {
		
		printHeader(header);
		printClasses(set,obj);
		Config.OUT.add("...........................................................................................................");
		Config.OUT.add("");
		printRelations(setRel);
	}
		
	
	void printToList(ObjGeom obj) {
		
		
	}
	public void saveOnto() {
		myOWL.saveOntologyOWLFormat(new File(output_ontology));
	}
	
}

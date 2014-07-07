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
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
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
	
//	private static String input_ontology = "/Users/Macsee/Desktop/Examples/New/OntologyJava.owl";
	private static String input_ontology = "./Ontologies/OntologyJava.owl";

	private static String output_ontology = "./Ontologies/OutOntology.owl";
//	private static String output_ontology = "/Users/Macsee/Desktop/Examples/New/OutOntology.owl";
	
	private myOWL myOWL;
	
	private Map<OWLNamedIndividual,Set<OWLClassExpression>> ASSERTED_CLASS_RELS = new HashMap<OWLNamedIndividual,Set<OWLClassExpression>>();
	private Map<OWLNamedIndividual, Set<OWLClassExpression>> ASSERTED_CLASS_OBJS = new HashMap<OWLNamedIndividual, Set<OWLClassExpression>>();
	
	private Map<OWLNamedIndividual, Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>>> ASSERTED_PROP_OBJS = new HashMap<OWLNamedIndividual, Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>>>();
	private Map<OWLNamedIndividual, Map<OWLDataPropertyExpression, OWLLiteral>> ASSERTED_DATA_OBJS = new HashMap<OWLNamedIndividual, Map<OWLDataPropertyExpression, OWLLiteral>>();
	private Map<OWLNamedIndividual, Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>>> ASSERTED_PROP_RELS = new HashMap<OWLNamedIndividual, Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>>>();
	
	
	private Map<OWLNamedIndividual, Set<OWLNamedIndividual>> INCLUDED = new HashMap<OWLNamedIndividual, Set<OWLNamedIndividual>>();
	private Map<OWLNamedIndividual, ObjGeom> IND_OBJ = new HashMap<OWLNamedIndividual, ObjGeom>();
	
	public CM8toOWL() {
		// TODO Auto-generated constructor stub
		myOWL = new myOWL();
		myOWL.loadOntologyFromFile(new File(input_ontology));
		System.out.println();
	}
	
	public OWLNamedIndividual assertIndividual(ObjGeom obj) {  // Convierto los poligonos en individuos
	
		Set<OWLClassExpression> set = new HashSet<OWLClassExpression>();
	
		OWLNamedIndividual ind = myOWL.defineInstance("P"+obj.getId(), myOWL.getClass(obj.getCLASE()));
		set.add(myOWL.getClass(obj.getCLASE()));
		
//		OWLNamedIndividual ind = myOWL.getIndividual("P"+obj.getId());
//		String clase = obj.getCLASE();
//		
//		myOWL.addToIndividual(ind, myOWL.getClass(Config.BASIC_CLASS));
//		set.add(myOWL.getClass(Config.BASIC_CLASS));
//		
//		if (clase != Config.BASIC_CLASS) {	
//			myOWL.addToIndividual(ind, myOWL.getClass(clase));
//			set.add(myOWL.getClass(clase));
//		}	
			// PUEDO AGREGAR MAS TIPOS A UN INDIVIDUO CREANDO MUCHAS INSTANCIAS CON EL MISMO NOMBRE Y DISTINTOS TIPOS
	
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
	
	public String assertRelation(String cm8, ObjGeom obj1, ObjGeom obj2) {
		
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

		return rel;
	} 
	
	public void makeIndividualsDifferent(ObjGeom ob1, ObjGeom ob2) {
		
		myOWL.assertDifferentFrom(getIndividual(ob1), getIndividual(ob2));

	}
	
	public void makeAllIndsDifferentFrom(ObjGeom ob1) {
		
		for (OWLNamedIndividual ind: IND_OBJ.keySet()) {
			ObjGeom ob2 = IND_OBJ.get(ind);
			if (ob1 != ob2)
				if ((ob1.getSAMEIND() == null) || (ob1.getSAMEIND() != ob2.getId()))
					makeIndividualsDifferent(ob1, ob2);
		}
	}
	
	
	public void assertSameIndividuals(ObjGeom ob1) {
			
		Integer IDob2 = ob1.getSAMEIND();
		
			if (IDob2 != null) {
				OWLNamedIndividual ind1 = getIndividual(ob1);
				OWLNamedIndividual ind2 = myOWL.getIndividual("P"+IDob2);
				myOWL.assertSameIndividual(ind1, ind2);
			}	

	}
	
	
	public void assertMaxCard(ObjGeom ob, Integer cant, String clase, String prop) {
		
		OWLClassExpression restrict = myOWL.assertMaxRestriction(myOWL.getObjectProperty(prop), cant, myOWL.getClass(clase));
		OWLNamedIndividual ind = getIndividual(ob);
		myOWL.addToIndividual(ind, restrict);
		
		Set<OWLClassExpression> set = ASSERTED_CLASS_OBJS.get(ind);
		set.add(restrict);
		
		ASSERTED_CLASS_OBJS.put(ind, set);
		
	}
	
	public void assertMinCard(ObjGeom ob, Integer cant, String clase, String prop) {
		
		OWLClassExpression restrict = myOWL.assertMinRestriction(myOWL.getObjectProperty(prop), cant, myOWL.getClass(clase));
		
		OWLNamedIndividual ind = getIndividual(ob);
		myOWL.addToIndividual(ind, restrict);
		
		Set<OWLClassExpression> set = ASSERTED_CLASS_OBJS.get(ind);
		set.add(restrict);
		
		ASSERTED_CLASS_OBJS.put(ind, set);
	}
	
	public void assertExactCard(ObjGeom ob, Integer cant, String clase, String prop) {
		
		OWLClassExpression restrict = myOWL.assertExactRestriction(myOWL.getObjectProperty(prop), cant, myOWL.getClass(clase));
		OWLNamedIndividual ind = getIndividual(ob);
		myOWL.addToIndividual(ind, restrict);
		
		Set<OWLClassExpression> set = ASSERTED_CLASS_OBJS.get(ind);
		set.add(restrict);
		
		ASSERTED_CLASS_OBJS.put(ind, set);
	}
	
	public void assertObjProperty(ObjGeom ob, String prop, String value) {
		
		if (value.equals(""))
			return;
		
		OWLNamedIndividual indObj = getIndividual(ob);
		OWLObjectProperty indRel = myOWL.getObjectProperty(prop);
		OWLNamedIndividual indValue = myOWL.getIndividual("i"+value); // si no existe el individuo se crea
		
		myOWL.defineObjectProperty(indRel, indObj, indValue);
		
		Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>> mapObj = new HashMap();
		Set<OWLNamedIndividual> setInd = new HashSet<OWLNamedIndividual>();
		
		if ( ASSERTED_PROP_OBJS.containsKey(indObj)) {
			if ( ASSERTED_PROP_OBJS.get(indObj).containsKey(indRel)) {
				setInd = ASSERTED_PROP_OBJS.get(indObj).get(indRel);
			}
		}
		
		setInd.add(indValue);
		mapObj.put(indRel, setInd);
		
		ASSERTED_PROP_OBJS.put(indObj,mapObj);
	}
	
	public void assertBooleanProperty(ObjGeom ob, String prop, String value) {
				
		if (value.equals(""))
			return;
		
		Boolean valueB = false;
		
		if (value.equals("Yes"))
			valueB = true;
		
		OWLNamedIndividual indObj = getIndividual(ob);
		OWLDataProperty indRel = myOWL.getDataProperty(prop);
		Map<OWLDataPropertyExpression, OWLLiteral> mapObj = new HashMap();
		
		OWLLiteral lit = myOWL.getBooleanLiteral(valueB);
		
		myOWL.defineDataProperty(indRel, indObj, lit);	
	
		mapObj.put(indRel, lit);
		ASSERTED_DATA_OBJS.put(indObj,mapObj);
	}
	
	
	public void countObjsRelatedWith(ObjGeom obj, String prop) {
		
		Map<OWLNamedIndividual,Integer> count = new HashMap<OWLNamedIndividual,Integer>();
		
		OWLNamedIndividual ind = getIndividual(obj);
		
		Set<OWLNamedIndividual> indSet = myOWL.getInferredIndRelThrProp(ind, myOWL.getObjectProperty(prop));
		
		if (indSet != null) {
			
			Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> mapObj = new HashMap();
			mapObj.put(myOWL.getObjectProperty(prop), indSet);
			
			count.put(ind, indSet.size());
			assertCardinalityRestriccions(count, Config.BASIC_CLASS, prop);
			ASSERTED_PROP_OBJS.put(ind, mapObj);
			
//			if (prop.equals("isIncludedIn") && (indSet.size() > 0))
//				obj.setRESOLUTION("");
			
		}	
		
	}
	
	public void assertCardinalityRestriccions(Map<OWLNamedIndividual,Integer> count, String cm8, String prop) {
		
		for (Entry<OWLNamedIndividual, Integer> entry : count.entrySet())
			if (entry.getValue() != 0)
				assertMaxCard(getObjGeom(entry.getKey()), entry.getValue(), cm8, prop);
	}
	
	public ArrayList<String> getAssertedDataForObject(ObjGeom obj) {
		
		OWLNamedIndividual ind = getIndividual(obj);
		Set<OWLClassExpression> set = ASSERTED_CLASS_OBJS.get(ind);
		Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel = ASSERTED_PROP_OBJS.get(ind);
		Map<OWLDataPropertyExpression, OWLLiteral> setLit = ASSERTED_DATA_OBJS.get(ind);
		
		return printAll(set, setRel, setLit, null, "Polygon P"+obj.getId());
		
	}
	
	public ArrayList<String> getInferredDataForObject(ObjGeom obj) {
		
		OWLNamedIndividual ind = getIndividual(obj);
		Set<OWLClassExpression> set = myOWL.getInferredMembershipForInd(ind);
		Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel = myOWL.getInferredObjPropertiesFor(ind);
		Map<OWLDataPropertyExpression, OWLLiteral> setLit = myOWL.getInferredDataPropertiesFor(ind);
		
		return printAll(set, setRel, setLit, obj, "Polygon P"+obj.getId()); //Imprimo todos los resultados
	    	
	}
	
	public boolean checkConsistency() {
		return myOWL.checkConsistency();
	}
	
	public ArrayList<String> getAssertedDataForSpatialRelation(String rel) {
		
		OWLNamedIndividual relInd = myOWL.getIndividual(rel);
		Set<OWLClassExpression> set = ASSERTED_CLASS_RELS.get(relInd);
		Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel = ASSERTED_PROP_RELS.get(relInd);
		
		return printAll(set, setRel, new HashMap<OWLDataPropertyExpression, OWLLiteral>(), null, relInd.getIRI().getFragment());
			
	}
	
	public ArrayList<String> getInferredDataForSpatialRelation(String rel) {
			
		OWLNamedIndividual relInd = myOWL.getIndividual(rel);
    	Set<OWLClassExpression> set = myOWL.getInferredMembershipForInd(relInd);
    	
    	return printAll(set,new HashMap<OWLObjectPropertyExpression, Set<OWLNamedIndividual>>(), new HashMap<OWLDataPropertyExpression, OWLLiteral>(), null,relInd.getIRI().getFragment());
	
	}
	
	ArrayList<String> printHeader(String header) {
		ArrayList<String> out = new ArrayList<String>();
		out.add("############################################## "+header+" ##############################################");
		return out;
	}
	
	ArrayList<String> printClasses(Set<OWLClassExpression> set, ObjGeom obj) {
		
		OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
		
		ArrayList<String> out = new ArrayList<String>();
		
    	out.add("Classes: ");
    	out.add("");
    	
    	if (set == null) {
    		out.add("Inconsistent");
    		out.add("");
    		return out;
    	}
    	
    	if (set.isEmpty()) {
    		out.add("Nothing to infer");
    		out.add("");
    		return out;
    	}
    
		for (OWLClassExpression owlClass : set) {
			out.add(renderer.render(owlClass));
				if (Config.ONTCLASSES.contains(renderer.render(owlClass)) & obj != null){ // Imprimo el resultado en la lista de la capa activa
					if (myOWL.isSuperClass(myOWL.getClass(obj.getCLASE()), owlClass)) {
						System.out.println(obj.getCLASE()+ " es superclase de "+owlClass.asOWLClass().getIRI().getFragment());
						obj.setCLASE(renderer.render(owlClass));
						Config.updateDefaultList(obj);
					}	
				}
			}	
				
    	out.add("");
    	
    	return out;
	}
	
	ArrayList<String> printRelations(Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel, Map<OWLDataPropertyExpression, OWLLiteral> setLit) {
		
		OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
		
		ArrayList<String> out = new ArrayList<String>();
		
		out.add("Object Properties: ");
    	out.add("");
    	
    	if (setRel == null) {
    		out.add("Nothing to infer");
    		out.add("");
    	}
    	else {	
			for (Entry<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> entry : setRel.entrySet())
				for (OWLObject ind : entry.getValue())
	    			out.add(renderer.render(entry.getKey())+" "+renderer.render(ind));
    	}
    	
		out.add("");
		out.add("");
		out.add("Data Properties: ");
    	out.add("");
    	
    	if (setLit == null) {
    		out.add("");
    		return out;
    	}
    	else {
	    	for (Entry<OWLDataPropertyExpression, OWLLiteral> entry : setLit.entrySet())
	    			out.add(renderer.render(entry.getKey())+" "+renderer.render(entry.getValue()));
    	}	
		
		out.add("");
    	
		return out;
	}
	
	ArrayList<String> printAll(Set<OWLClassExpression> set, Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel, Map<OWLDataPropertyExpression, OWLLiteral> setLit, ObjGeom obj, String header) {
		
		ArrayList<String> out = new ArrayList<String>();
		
		out.addAll(printHeader(header));
		out.addAll(printClasses(set,obj));
		out.add("");
		out.add("...........................................................................................................");
		out.add("");
		out.addAll(printRelations(setRel,setLit));
		
		return out;
	}
		
	public void saveOnto() {
		myOWL.saveOntologyOWLFormat(new File(output_ontology));
	}
	
}

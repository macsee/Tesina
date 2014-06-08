package OWL;
import java.awt.Dialog;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.plaf.OptionPaneUI;
import javax.swing.text.html.Option;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;
import GUI.ErrorOWL;
import UTILS.Config;
import UTILS.ObjGeom;


public class myOWL {

	/**
	 * @param args
	 */
	
	public static final String DEFAULT_VARIABLES_BASE_IRI = "urn:swrl";
    private IRI IRI;
    private OWLOntologyManager MANAGER;
    private OWLOntology ONTOLOGY;
    private OWLDataFactory FACTORY;
    private String VARBASEIRI;
    private Set<OWLNamedIndividual> INDIVIDUALS = new HashSet<OWLNamedIndividual>(); 
    private File FILE;

    public void loadOntologyFromFile(File file) {
    	
    	try {
    		
    		this.MANAGER = OWLManager.createOWLOntologyManager();
			this.ONTOLOGY = MANAGER.loadOntologyFromOntologyDocument(file);
			this.IRI = ONTOLOGY.getOntologyID().getOntologyIRI();
			this.FACTORY = MANAGER.getOWLDataFactory();
	        this.VARBASEIRI = DEFAULT_VARIABLES_BASE_IRI;
	        this.FILE = file;
	        System.out.println(IRI.getNamespace());
	        System.out.println(IRI.getFragment());
	        
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    /****************************************************
     * Config the reasoner to be used					*
     ****************************************************/
    
    public OWLReasoner getReasoner(){
    	
    	return new org.semanticweb.HermiT.Reasoner(ONTOLOGY);
    	//return PelletReasonerFactory.getInstance().createNonBufferingReasoner(ONTOLOGY);
    }
    
    
    /****************************************************
     * Getting class "name" from the ontology			*
     ****************************************************/
    
    public OWLClass getClass(String name) {
    	
    	return FACTORY.getOWLClass(IRI.create(this.IRI + "#" + name));
    	
    }

    
    /****************************************************
     * Asserting class "name" in the ontology			*
     ****************************************************/
    
    public OWLClass defineClass(String name) {
    	
    	OWLClass owlclass = this.getClass(name);
    	MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLDeclarationAxiom(owlclass));
    	//REASONER.flush();
    	return owlclass;
    	
    }
    
    
    /****************************************************************
     * Asserting conjunction class "name" in the ontology			*
     ****************************************************************/
    
    public OWLClass defineConjunctionClass(String name, ArrayList<OWLClass> list) {
    	
    	Set<OWLClass> set = new HashSet<OWLClass>();
    	
    	for (OWLClass owl : list)
    		set.add(owl);
    	
    	OWLClass owlclass = this.getClass(name);
    	
    	OWLClassExpression intersection = FACTORY.getOWLObjectIntersectionOf(set);
    	
    	MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLEquivalentClassesAxiom(owlclass,intersection));
    	//REASONER.flush();
    	return owlclass;
    }

    /****************************************************************
     * Asserting disjunction class "name" in the ontology			*
     ****************************************************************/
    
    public OWLClassExpression defineDisjunctionClass(String name, ArrayList<OWLClass> list) {
    	
    	Set<OWLClass> set = new HashSet<OWLClass>();
    	
    	for (OWLClass owl : list)
    		set.add(owl);
    	
    	OWLClass owlclass = this.getClass(name);
    	
    	OWLClassExpression intersection = FACTORY.getOWLObjectUnionOf(set);
    	
    	MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLEquivalentClassesAxiom(owlclass,intersection));
    	//REASONER.flush();
    	return owlclass;
    	
    }
    
    
    /****************************************************
     * Getting individual "name" from the ontology		*
     ****************************************************/
    
    public OWLNamedIndividual getIndividual(String name) {
    	
    	return FACTORY.getOWLNamedIndividual(IRI.create(this.IRI + "#" + name));
    	
    }

    
    /****************************************************
     * Asserting instance "name" in the ontology		*
     ****************************************************/
    
    public OWLNamedIndividual defineInstance(String name, OWLClassExpression owlclass) {
    	
    	OWLNamedIndividual owlind = getIndividual(name);
    	
    	MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLClassAssertionAxiom(owlclass, owlind));
    	//REASONER.flush();
    	INDIVIDUALS.add(owlind);
    	return owlind;
    	
    }

    
    /****************************************************
     * Getting all individuals from the ontology			*
     ****************************************************/
    
    public Set<OWLNamedIndividual> getAllInds() {
    	Set<OWLNamedIndividual> setInds = ONTOLOGY.getIndividualsInSignature();
    	OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
    		
    	return setInds;
    }
    
    
    /****************************************************
     * Asserting individual "name" in the ontology		*
     ****************************************************/
    
    public OWLNamedIndividual defineIndividual(String name) {
    	
    	OWLNamedIndividual owlind = this.getIndividual(name);
    	MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLClassAssertionAxiom(null, owlind));
    	//REASONER.flush();
    	INDIVIDUALS.add(owlind);
    	return owlind;
    	
    }

    
    /********************************************************************
     * Asserting cardinalities for class "owlclass and property op		*
     ********************************************************************/
    
    public OWLClassExpression assertMaxRestriction(OWLObjectProperty op, Integer cant, OWLClass owlclass) {
    	
    	OWLClassExpression exp = FACTORY.getOWLObjectMaxCardinality(cant, op, owlclass);
    	//REASONER.flush();
    	return exp;
    }
    
    public OWLClassExpression assertMinRestriction(OWLObjectProperty op, Integer cant, OWLClass owlclass) {
    	
    	OWLClassExpression exp = FACTORY.getOWLObjectMinCardinality(cant, op, owlclass);
    	//REASONER.flush();
    	return exp;
    }
    
    public OWLClassExpression assertExactRestriction(OWLObjectProperty op, Integer cant, OWLClass owlclass) {
    	
    	OWLClassExpression exp = FACTORY.getOWLObjectExactCardinality(cant, op, owlclass);
    	//REASONER.flush();
    	return exp;
    }
    
    
    /********************************************************************
     * Getting asserted object properties for individual "ind"			*
     ********************************************************************/
    
    public Map<OWLObjectPropertyExpression, Set<OWLIndividual>> getAssertedObjPropertiesFor(OWLNamedIndividual ind) {
    	return ind.getObjectPropertyValues(ONTOLOGY);
    }

    
    /********************************************************************
     * Getting asserted individuals related with "ind" through "prop"	*
     ********************************************************************/
    
    public Set<OWLIndividual> getAssertedIndRelThrProp(OWLNamedIndividual ind, OWLObjectProperty prop) {
    	Map<OWLObjectPropertyExpression, Set<OWLIndividual>> set = getAssertedObjPropertiesFor(ind);
    	
    	if (!set.isEmpty())
    		return set.get(prop);
    	else
    		return new HashSet<OWLIndividual>();
    }
    
    
    /********************************************************************
     * Getting inferred object properties for individual "ind"			*
     ********************************************************************/
    
    public Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> getInferredObjPropertiesFor(OWLNamedIndividual ind) {
    	
    	OWLReasoner REASONER = getReasoner();
    	
    	if (REASONER.isConsistent()) { 
    		Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setInds = new HashMap<OWLObjectPropertyExpression, Set<OWLNamedIndividual>>();
   
	    	for (OWLObjectPropertyExpression obj : ONTOLOGY.getObjectPropertiesInSignature())
	    			setInds.put(obj,REASONER.getObjectPropertyValues(ind, obj).getFlattened());
    	
	    	return setInds;
    	}
    	else
    		return null;
    }
    
    /********************************************************************
     * Getting inferred individuals related with "ind" through "prop"	*
     ********************************************************************/
    
    public Set<OWLNamedIndividual> getInferredIndRelThrProp(OWLNamedIndividual ind, OWLObjectProperty prop) {
    	
    	Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> set = getInferredObjPropertiesFor(ind); 
    	if (set != null)
    		return set.get(prop);
    	else
    		return null;
    }
    
    
    /********************************************************************
     * Getting inferred data											*
     ********************************************************************/
    
    public Map<OWLNamedIndividual, Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>>> getInferredData() {
    	
    	Set<OWLNamedIndividual> setInds = getAllInds();
    	Map<OWLNamedIndividual, Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>>> resultSet = new HashMap<OWLNamedIndividual, Map<OWLObjectPropertyExpression,Set<OWLNamedIndividual>>>();
    	
    	for (OWLNamedIndividual ind : setInds) {
    		resultSet.put(ind, getInferredObjPropertiesFor(ind));
    	}
    		
    	return resultSet;
    }
    
    
    
    public boolean checkConsistency() {
    	
    	OWLReasoner REASONER = getReasoner();
    	return REASONER.isConsistent();
    		
    }
    
    /****************************************************
     * Asserting all individuals as different			*
     ****************************************************/
    
    public void assertDifferentIndividuals(Set<OWLNamedIndividual> inds) {
    	MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLDifferentIndividualsAxiom(inds));
    	//REASONER.flush();
    } 
   
    
    /****************************************************
     * Asserting ind1 same as ind2						*
     ****************************************************/
    
    public void assertSameIndividual(OWLNamedIndividual ind1, OWLNamedIndividual ind2) {
    	MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLSameIndividualAxiom(ind1, ind2));
    	//REASONER.flush();
    }
    
    /****************************************************
     * Asserting ind1 different from ind2				*
     ****************************************************/
    
    public void assertDifferentFrom(OWLNamedIndividual ind1, OWLNamedIndividual ind2) {
    	MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLDifferentIndividualsAxiom(ind1,ind2));
    	//REASONER.flush();
    }
   
    
    /****************************************************
     * Get object property "op" from the ontology		*
     ****************************************************/
    
    public OWLObjectProperty getObjectProperty(String op) {
    	
    	return FACTORY.getOWLObjectProperty(IRI.create(this.IRI + "#" + op));
    	
    }
    
    
    /****************************************************
     * Get data property "dp" from the ontology			*
     ****************************************************/
    
    public OWLDataProperty getDataProperty(String dp) {
    	
    	return FACTORY.getOWLDataProperty(IRI.create(this.IRI + "#" + dp));
    	
    }
    
    
    /*********************************************************************************
     * Declare role involving individuals ind1 and ind2 through object property "op" *
     *********************************************************************************/
    
    public void defineObjectProperty(OWLObjectPropertyExpression op, OWLNamedIndividual ind1, OWLNamedIndividual ind2) {
    	
    	if (ind1 == null | ind2 == null | op == null)
    		System.out.println("Error: some arguments are null");
    	else {
    		MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLObjectPropertyAssertionAxiom(op, ind1, ind2));
    		//REASONER.flush();
    	}	
    	
    }
    
    /********************************************************************************
     * Declare data property dp between ind1 and literal lit						*
     ********************************************************************************/
    
    public void defineDataProperty(OWLDataProperty dp, OWLNamedIndividual ind1, OWLLiteral lit) {
    	
    	if (ind1 == null | lit == null | dp == null)
    		System.out.println("Error: some arguments are null");
    	else {
    		MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLDataPropertyAssertionAxiom(dp, ind1, lit));
    		//REASONER.flush();
    	}		
    }
    
    
    public void addToIndividual(OWLNamedIndividual ind, OWLClassExpression owlclass) {
    	MANAGER.addAxiom(ONTOLOGY, FACTORY.getOWLClassAssertionAxiom(owlclass, ind));
    	//REASONER.flush();
    }
    
    
    /*********************************************************************************
     * Get asserted membership of individual "ind" 									 *
     *********************************************************************************/
    
    public Set<OWLClassExpression> getAssertedMembershipForInd(OWLNamedIndividual ind) {
    
    	if (ind != null)
    		return ind.getTypes(ONTOLOGY);
    	else {
    		System.out.println("Error: "+ind+" is not a valid individual");
    		return null;
    	}	
    	
    }
    
    
    /*********************************************************************************
     * Get inferred membership for individual "ind" 								 *
     *********************************************************************************/
    
    public Set<OWLClassExpression> getInferredMembershipForInd(OWLNamedIndividual ind) {
    	    	
    	Set<OWLClassExpression> set = new HashSet<OWLClassExpression>();
    	OWLReasoner REASONER = getReasoner();
    	
    	if (REASONER.isConsistent()) {
    		
	    	if (ind != null) {
	        	
	        	Set<OWLClassExpression> assertedClasses = ind.getTypes(ONTOLOGY);
	        	
	        	for (OWLClass c : REASONER.getTypes(ind, false).getFlattened()) 
        			
	            	if (!assertedClasses.contains(c))
	            			set.add(c);
	        	
	        	return set;
		            
	    	}
	    	else
	    		return null;
	        	      
    	}	
    	else
    		return null;
    }
    
    
    public Set<OWLNamedIndividual> getInstancesOf(OWLClass clase) {
    	
    	OWLReasoner REASONER = getReasoner();
    		       	
    	if (REASONER.isConsistent())
    		return REASONER.getInstances(clase, false).getFlattened();
    	else
    		return null;
    }
    
 
    public void saveOntologyOWLFormat(File f) {
        try {
            MANAGER.saveOntology(ONTOLOGY, new OWLXMLOntologyFormat(), IRI.create(f.toURI()));
            //REASONER.flush();
        } catch (OWLOntologyStorageException ex) {
            throw new Error("error saving ontology : ", ex);
        }

    }

    public void resetOntology() {
    	this.loadOntologyFromFile(FILE);
    }
    /*********************************************************************************
     * Print results								 								 *
     *********************************************************************************/

    public void printClassesFor(ObjGeom ob, Set<OWLClassExpression> set) {
    	
    	Config.OUT.add("############################################### Polygon P"+ob.getId()+" ###############################################");
		Config.OUT.add("Classes: ");
    	Config.OUT.add("");
    	
		for (OWLClassExpression clase : set) {
			if (Config.ONTCLASSES.contains(clase.asOWLClass().getIRI().getFragment())) {
				ob.setCLASE(clase. asOWLClass().getIRI().getFragment());
				Config.ACTIVELAYER.updateDefaultList(ob);
				Config.OUT.add(clase.asOWLClass().getIRI().getFragment());
			}	
		}
		
		Config.OUT.add("");
    	 
    	
    }
        
    public void printPropFor(Map<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> setRel) {
    	
    	OWLObjectRenderer renderer = new DLSyntaxObjectRenderer(); 
    	
    	Config.OUT.add("");
		Config.OUT.add("...........................................................................................................");
		Config.OUT.add("");
    	Config.OUT.add("Object Properties: ");
    	Config.OUT.add("");
    	
    	for (Entry<OWLObjectPropertyExpression, Set<OWLNamedIndividual>> entry : setRel.entrySet()) {
    		Set<OWLNamedIndividual> subset = entry.getValue();
    		for (OWLObject ind : subset)
    			Config.OUT.add(renderer.render(entry.getKey())+" "+renderer.render(ind));
    	}
    	
    	Config.OUT.add("");

    }
    
    
    public void printRelFor(OWLNamedIndividual rel, Set<OWLClassExpression> set) {
    	
    	OWLObjectRenderer renderer = new DLSyntaxObjectRenderer(); 
    	
    	Config.OUT.add("################################################# "+rel.getIRI().getFragment()+" ################################################");
    	Config.OUT.add("Classes: ");
    	Config.OUT.add("");
    	
    	for (OWLClassExpression owl : set) {
    		Config.OUT.add(renderer.render(owl));
    	}
    	
    	Config.OUT.add("");
    }
    
    public void printObjProp(Map<OWLObjectPropertyExpression, Set<OWLIndividual>> set) {
    	
    	OWLObjectRenderer renderer = new DLSyntaxObjectRenderer(); 
    	
    	Config.OUT.add("");
		Config.OUT.add("...........................................................................................................");
		Config.OUT.add("");
    	Config.OUT.add("Object Properties: ");
    	Config.OUT.add("");

    	
    	for (Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry : set.entrySet()) {
    		Set<OWLIndividual> subset = entry.getValue();
    		for (OWLObject ind : subset)
    			Config.OUT.add(renderer.render(entry.getKey())+" "+renderer.render(ind));
    	}
    	
    	
    	Config.OUT.add("");
    	Config.OUT.add("");
    }

}

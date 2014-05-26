package OWL;
import java.util.ArrayList;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.IntersectionClass;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;



public class JenaUtil {

	/**
	 * @param args
	 */
	String NS;
	OntModel ONTOLOGY;
	OntModel REASONER;
	StringBuilder RULES;
	String OWL;
	String RDFS;
	
		
	public void loadOntologyFromFile(String file) {
		ONTOLOGY = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		ONTOLOGY.read(file);
		
		REASONER = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC );
		REASONER.read(file);
		
		NS = ONTOLOGY.listOntologies().next().getURI()+"#"; //Obtengo el Name Space de mi ontolog’a para no tener que setearlo a mano
		RULES = new StringBuilder();

	}
	
	public OntModel getReasoner() {
		return this.REASONER;
	}
	
	
	public OntModel getOntology() {
		return this.ONTOLOGY;
	}
	
	
	public String getNS() {
		return this.NS;
	}
	

	/************************************
	 * Creating a class-less individual	*
	 ************************************/
	
	public Individual createIndividual(String ind) {
		return ONTOLOGY.createIndividual(NS+ind, null); 
	}
	
	
	
	/************************************
	 * Creating an instance of a class 	*
	 ************************************/
	
	public Individual createInstance(String ind, String clas) {
		return ONTOLOGY.createIndividual(NS+ind, ONTOLOGY.getOntClass(NS+clas)); 
	}
	
	/************************************
	 * Creating an instance of a class 	*
	 ************************************/
	
	public Individual getIndividual(String ind) {
		return ONTOLOGY.getIndividual(NS+ind);
	}
	
	/************************************
	 * Creating a class					*
	 ************************************/
	
	public OntClass createClass(String clase) {
		return ONTOLOGY.createClass(NS+clase);
	}
	
	public OntClass getOntClass(String clase) {
		return ONTOLOGY.getOntClass(NS+clase);
	}
	
	/************************************
	 * Creating an intersection class	*
	 ************************************/
	
	public IntersectionClass createIntersectionClass(String name, ArrayList<OntClass> list) {
		
		RDFList cs = ONTOLOGY.createList(); // Cs is empty
		
		for (OntClass clase : list) {
			System.out.println(clase.getURI());
			cs.cons(clase);
		}
		
		return ONTOLOGY.createIntersectionClass(name, cs);
	}
	
	
	/************************************
	 * Creating an object property		*
	 ************************************/
	
	public Property createObjectProperty(String prop) {
		return ONTOLOGY.createObjectProperty(NS+prop);
	}
	
	
	
	/****************************************************
	 * Creating a relationship between two individuals	*
	 ****************************************************/
	
	public void createRelationship(String ind1, String prop, String ind2) {
		ONTOLOGY.add(ONTOLOGY.getIndividual(NS+ind1), ONTOLOGY.getObjectProperty(NS+prop), ONTOLOGY.getIndividual(NS+ind2));
	}
	
	
	/********************************************************
	 * Getting all asserted instances of a class.			* 
	 * If success return an ArrayList of the individuals.	*
	 * Return error if class not defined					*
	 ********************************************************/
	
	public ArrayList<OntResource> getAssertedIndividualMembersOf(String clase) {
		
		OntClass clas = ONTOLOGY.getOntClass(NS+clase);
		
		if (clas == null) {
			
			System.out.println("Class \""+clas+"\" not defined. Please define it with createClass");
			return null;
			
		}
		else {
			
			ExtendedIterator iterator = clas.listInstances();
			ArrayList<OntResource> listIndividuals = new ArrayList<OntResource>();
			
			while(iterator.hasNext()) {
				OntResource individual = (OntResource)iterator.next();
				listIndividuals.add(individual);
			}
			
			return listIndividuals;
			
		}
	}
	
	
	/********************************************************
	 * Getting all inferred classes for individual ind.		* 
	 * If success return an ArrayList of classes.			*
	 * Return error if individual is not defined			*
	 ********************************************************/
	
	public ArrayList<OntResource> getInferredClassesForInd(String ind) {
		
			Individual individual = REASONER.getIndividual(NS+ind);
			
			ExtendedIterator iterator = individual.listOntClasses(true);
			ArrayList<OntResource> listClasses = new ArrayList<OntResource>();
			
			while(iterator.hasNext()) {
				OntResource clase = (OntResource)iterator.next();
				listClasses.add(clase);
			}
			
			return listClasses;
			
			//return individual.getOntClass();
	}
	
	
	/********************************************************
	 * Getting all inferred instances of a class.			* 
	 * If success return an ArrayList of the individuals.	*
	 * Return error if class not defined					*
	 ********************************************************/
	
	public ArrayList<OntResource> getInferredClassMembers(String clase) {
		
		OntClass clas = REASONER.getOntClass(NS+clase);
		
		if (clas == null) {
			
			System.out.println("Class \""+clas+"\" not defined. Please define it with createClass");
			return null;
			
		}
		else {
			
			ExtendedIterator iterator = clas.listInstances();
			ArrayList<OntResource> listIndividuals = new ArrayList<OntResource>();
			
			while(iterator.hasNext()) {
				OntResource individual = (OntResource)iterator.next();
				listIndividuals.add(individual);
			}
			
			return listIndividuals;
			
		}
	}
	
	
	
	/****************************************************************************
	 * Getting all asserted individuals related through relationship "prop".	*
	 ****************************************************************************/
	
	public ArrayList<Resource> getAssertedIndRelatedWith(String ind, String prop) {
		
		ArrayList<Resource> listIndividual = new ArrayList<Resource>();
		
		Property p = ONTOLOGY.getProperty(NS+prop);
		Resource a = ONTOLOGY.getResource(NS+ind);
		StmtIterator i = ONTOLOGY.listStatements(a, p, (RDFNode)null);
		
		while (i.hasNext()){
			Resource value = (Resource) i.nextStatement().getObject();
			listIndividual.add(value);
		}	
		
		return listIndividual;
	}
	
	
	
	/****************************************************************************************
	 * Using SPARQL to get all asserted individuals related through relationship "prop".	*
	 ****************************************************************************************/
	
	public ArrayList<Resource> getAssertedIndRelatedWithSPARQL(String ind, String prop) {
		
		String queryString =	"PREFIX rdfs: <"+NS+">\n"+ 
								"SELECT ?ind WHERE \n"+
								"{\n"+
									"rdfs:"+ind+" rdfs:"+prop+" ?ind.\n"+
									"?ind a rdfs:Child.\n"+
								"}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, ONTOLOGY);
		
		ArrayList<Resource> listIndividual = new ArrayList<Resource>();
		
		try {
			ResultSet results = qexec.execSelect() ;

		    	for ( ; results.hasNext() ; )
		    	{
		    		QuerySolution soln = results.nextSolution() ;
		    		Resource individual = soln.getResource("ind") ; // Get a result variable - must be a resource
		    		listIndividual.add(individual);
		    	}
		    	
		    	return listIndividual;
		    	 	
		  	} finally { qexec.close() ; }
			
	}

	
	
	/****************************************************************************
	 * Getting all inferred individuals related through relationship "prop".	*
	 ****************************************************************************/
	
	public ArrayList<Resource> getInferredIndRelatedWith(String ind, String prop) {
			
			ArrayList<Resource> listIndividual = new ArrayList<Resource>();
			
			Property p = REASONER.getProperty(NS+prop);
			Resource a = REASONER.getResource(NS+ind);
			StmtIterator i = REASONER.listStatements(a, p, (RDFNode)null);
			
			while (i.hasNext()) {
				Resource value = (Resource) i.nextStatement().getObject();
				listIndividual.add(value);
			}
			
			return listIndividual;
	}
	
	
	
	/****************************************************************************************
	 * Using SPARQL to get all inferred individuals related through relationship "prop".	*
	 ****************************************************************************************/
	
	public ArrayList<Resource> getInferredIndRelatedWithSPARQL(String ind, String prop) {
		
		String queryString =	"PREFIX rdfs: <"+NS+">\n"+ 
								"SELECT ?ind WHERE \n"+
								"{\n"+
									"rdfs:"+ind+" rdfs:"+prop+" ?ind.\n"+
									"?ind a rdfs:Child.\n"+
								"}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, REASONER);
		
		ArrayList<Resource> listIndividual = new ArrayList<Resource>();
		
		try {
			ResultSet results = qexec.execSelect() ;

		    	for ( ; results.hasNext() ; )
		    	{
		    		QuerySolution soln = results.nextSolution() ;
		    		Resource individual = soln.getResource("ind") ; // Get a result variable - must be a resource
		    		listIndividual.add(individual);
		    	}
		    	
		    	return listIndividual;
		    	 	
		  	} finally { qexec.close() ; }
			
	}
	
	
	
	/****************************************************************
	 * Getting all asserted properties involving individual "ind"	*
	 ****************************************************************/
	
	public ArrayList<Statement> getAssertedPropFor(String ind) {
			
			ArrayList<Statement> listStatement = new ArrayList<Statement>();
			
			Resource a = ONTOLOGY.getResource(NS+ind);
			StmtIterator i = ONTOLOGY.listStatements(a, (Property)null, (RDFNode)null);
			
			while (i.hasNext()) {
				listStatement.add(i.nextStatement());
			}
			
			return listStatement;
	}
	
	
	
	/****************************************************************************
	 * Using SPARQL to get all asserted properties involving individual "ind"	*
	 ****************************************************************************/
	
	public ArrayList<Statement> getAssertedPropForSPARQL(String ind) {
		
		String queryString =	"PREFIX rdfs: <"+NS+">\n"+ 
				"SELECT ?ind ?prop WHERE \n"+
				"{\n"+
					"rdfs:"+ind+" ?prop ?ind.\n"+
					"?ind a rdfs:Child.\n"+
				"}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, ONTOLOGY);
		
		ArrayList<Statement> listStatement = new ArrayList<Statement>();
		
		try {
		ResultSet results = qexec.execSelect() ;
		//ResultSetFormatter.out(System.out, results, query);
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			
			Property prop = ONTOLOGY.getProperty(soln.getResource("prop").getURI()); // Get a result variable - must be a resource
			Resource individual = soln.getResource("ind") ; // Get a result variable - must be a resource
			Statement s = ResourceFactory.createStatement(ONTOLOGY.getIndividual(NS+ind), prop,individual);
			listStatement.add(s);
		}
		
		return listStatement;
		 	
		} finally { qexec.close() ; }
	
	}
	
	
	
	/****************************************************************
	 * Getting all inferred properties involving individual "ind"	*
	 ****************************************************************/
	
	public ArrayList<Statement> getInferredPropFor(String ind) {
			
			ArrayList<Statement> listStatement = new ArrayList<Statement>();
			
			Resource a = REASONER.getResource(NS+ind);
			StmtIterator i = REASONER.listStatements(a, (Property)null, (RDFNode)null);
			
			while (i.hasNext()) {
				listStatement.add(i.nextStatement());
			}
			
			return listStatement;
	}
	
	
	
	/****************************************************************
	 * Getting all inferred properties involving individual "ind"	*
	 ****************************************************************/
	
	public ArrayList<Statement> getInferredPropForSPARQL(String ind) {
		
		String queryString =	"PREFIX rdfs: <"+NS+">\n"+ 
				"SELECT ?ind ?prop WHERE \n"+
				"{\n"+
					"rdfs:"+ind+" ?prop ?ind.\n"+
					"?ind a rdfs:Child.\n"+
				"}";
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, REASONER);
		
		ArrayList<Statement> listStatement = new ArrayList<Statement>();
		
		try {
		ResultSet results = qexec.execSelect() ;
		//ResultSetFormatter.out(System.out, results, query);
		for ( ; results.hasNext() ; )
		{
			QuerySolution soln = results.nextSolution() ;
			Property prop = REASONER.getProperty(soln.getResource("prop").getURI()); // Get a result variable - must be a resource
			Resource individual = soln.getResource("ind") ; // Get a result variable - must be a resource
			Statement s = ResourceFactory.createStatement(REASONER.getResource(NS+ind), prop,individual);
			listStatement.add(s);
			
		}
		
		return listStatement;
		 	
		} finally { qexec.close() ; }
	
	}
		
	public void addSWRLRule(){
		
		String rule =	"@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> . "+
						"@prefix owl:  <http://www.w3.org/2002/07/owl#> . "+ 
						"[rule1: (?y rdf:type "+NS+":Father), (?x "+NS+":isChildOf ?y), (?z "+NS+":isChildOf ?y), (?y owl:differentFrom ?z) -> (?x "+NS+":isBrotherOf ?z)]";
		Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rule));

		Model model = ModelFactory.createDefaultModel();
		
        // Create inferred model using the reasoner and write it out.
        InfModel inf = ModelFactory.createInfModel(reasoner, model);
        inf.write(System.out);
	}

//	public ArrayList<OntResource> getAssertedIndRelatedWith(Individual x, Property prop ) {
//	
//	ExtendedIterator iterator = ONTOLOGY.listIndividuals();
//	ArrayList<OntResource> listIndividuals = new ArrayList<OntResource>();
//	
//	while(iterator.hasNext()) {
//		
//		Individual ind = (Individual) iterator.next();
//		
//		if (ONTOLOGY.contains(ResourceFactory.createStatement(x,prop,ind))) {
//			//System.out.println(ind.getLocalName());
//			listIndividuals.add(ind);
//		}
//		
//	}
//	
//	return listIndividuals;
//}	
	
//	public ArrayList<Statement> getAssertedPropOf(Individual x) {
//		
//		ExtendedIterator iterator_prop = ONTOLOGY.listObjectProperties();
//		ArrayList<Statement> listStatement = new ArrayList<Statement>();
//		
//		System.out.println("Asserted properties for individual "+x.getLocalName()+":");
//		
//		while(iterator_prop.hasNext()) {
//			
//			Property prop = (Property) iterator_prop.next();
//			ArrayList<OntResource> listIndividuals = getAssertedIndRelatedWith(x, prop);
//	
//			for (OntResource ind: listIndividuals ) {
//				
//				Statement s = ResourceFactory.createStatement(x,prop,ind);
//				
//				if (ONTOLOGY.contains(s)) {
//					System.out.println(x.getLocalName()+" "+prop.getLocalName()+" "+ind.getLocalName());
//					listStatement.add(s);
//				}
//			}
//
//		}
//		
//		return listStatement;
//	}

//	public ArrayList<OntResource> getInferredIndRelatedWith(Individual x, Property prop ) {
//		
//		ExtendedIterator iterator = REASONER.listIndividuals();
//		ArrayList<OntResource> listIndividuals = new ArrayList<OntResource>();
//		
//		while(iterator.hasNext()) {
//			
//			Individual ind = (Individual) iterator.next();
//			
//			if (REASONER.contains(ResourceFactory.createStatement(x,prop,ind))) {
//				//System.out.println(ind.getLocalName());
//				listIndividuals.add(ind);
//			}
//			
//		}
//		
//		return listIndividuals;
//	}
//	
//	public ArrayList<Statement> getInferredPropOf(Individual x) {
//		
//		ExtendedIterator iterator_prop = REASONER.listObjectProperties();
//		ArrayList<Statement> listStatement = new ArrayList<Statement>();
//		
//		System.out.println("Inferred properties for individual "+x.getLocalName()+":");
//		
//		while(iterator_prop.hasNext()) {
//			
//			Property prop = (Property) iterator_prop.next();
//			ArrayList<OntResource> listIndividuals = getInferredIndRelatedWith(x, prop);
//	
//			for (OntResource ind: listIndividuals ) {
//				
//				Statement s = ResourceFactory.createStatement(x,prop,ind);
//				
//				if (REASONER.contains(s)) {
//					System.out.println(x.getLocalName()+" "+prop.getLocalName()+" "+ind.getLocalName());
//					listStatement.add(s);
//				}
//			}
//
//		}
//		
//		return listStatement;
//	}
		
    public static void printInds(ArrayList<?> list, String header) {
        System.out.println(header);
        for(int c = 0; c < header.length(); c++)
            System.out.print("=");
        System.out.println();
        
        if (list != null) {
        
	        if(!list.isEmpty()) {
		        for (Object value : list) {
		        	System.out.println(((Resource) value).getLocalName());
		        }   
	        }       
	        else
	            System.out.println("<EMPTY>");
	        
	        for(int c = 0; c < header.length(); c++)
	            System.out.print("=");
	        System.out.println();
	        System.out.println();
        }
    }
    
    public static void printStatements(ArrayList<?> list, String header) {
        System.out.println(header);
        for(int c = 0; c < header.length(); c++)
            System.out.print("=");
        System.out.println();
        
        if(!list.isEmpty()) {
	        for (Object value : list) {
	        	Statement s = (Statement) value;
	        	System.out.println(s.getSubject().getLocalName()+" "+s.getPredicate().getLocalName()+" "+((Resource) s.getObject()).getLocalName());
	        }   
        }       
        else
            System.out.println("<EMPTY>");
        
        for(int c = 0; c < header.length(); c++)
            System.out.print("=");
        System.out.println();
        System.out.println();
    }
	
}
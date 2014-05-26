package OWL;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;

//import org.semanticweb.HermiT.examples.HermiTConfigurations;
//import org.semanticweb.HermiT.examples.HermiTDebugger;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;


public class Test {
	
	/**
	 * @param file
	 */
	public void open(File file) {
		myOWL owl = new myOWL();
		
		owl.loadOntologyFromFile(file);
		
		OWLNamedIndividual p0 	= 	owl.defineInstance("P0", owl.getClass("House"));
									owl.addToIndividual(p0, owl.getClass("GeoObject"));
									
		OWLNamedIndividual p1 	= 	owl.defineInstance("P1", owl.getClass("House"));
									owl.addToIndividual(p1, owl.getClass("GeoObject"));
									
		OWLNamedIndividual p2 	= 	owl.defineInstance("P2", owl.getClass("uArea"));
									owl.addToIndividual(p2, owl.getClass("GeoObject"));
									
		OWLNamedIndividual p3 	= 	owl.defineInstance("P3", owl.getClass("GeoObject"));
		owl.addToIndividual(p3, owl.assertMaxRestriction(owl.getObjectProperty("to-1"), 2, owl.getClass("P")));
		
		
		OWLNamedIndividual SR_P0_P1 =	owl.defineInstance("sr_p0_p1", owl.getClass("RCC8"));
										owl.addToIndividual(SR_P0_P1, owl.getClass("EC"));

		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P0_P1, p0);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P0_P1, p1);
		
										
		OWLNamedIndividual SR_P0_P2 =	owl.defineInstance("sr_p0_p2", owl.getClass("RCC8"));
		
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P0_P2, p0);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P0_P2, p2);
		
										
		OWLNamedIndividual SR_P0_P3 =	owl.defineInstance("sr_p0_p3", owl.getClass("RCC8"));
										owl.addToIndividual(SR_P0_P3, owl.getClass("NTPP"));
		
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P0_P3, p0);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P0_P3, p3);
		
		
		OWLNamedIndividual SR_P1_P0 = 	owl.defineInstance("sr_p1_p0", owl.getClass("RCC8"));
		
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P1_P0, p1);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P1_P0, p0);
		
										
		OWLNamedIndividual SR_P1_P2 = 	owl.defineInstance("sr_p1_p2", owl.getClass("RCC8"));
										
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P1_P2, p1);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P1_P2, p2);
		
		
		OWLNamedIndividual SR_P1_P3 = 	owl.defineInstance("sr_p1_p3", owl.getClass("RCC8"));
										owl.addToIndividual(SR_P1_P3, owl.getClass("NTPP"));
		
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P1_P3, p1);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P1_P3, p3);
		
		
		OWLNamedIndividual SR_P2_P0 = 	owl.defineInstance("sr_p2_p0", owl.getClass("RCC8"));
		
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P2_P0, p2);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P2_P0, p0);
		
		
		OWLNamedIndividual SR_P2_P1 = 	owl.defineInstance("sr_p2_p1", owl.getClass("RCC8"));
		
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P2_P1, p2);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P2_P1, p1);
		
		
		OWLNamedIndividual SR_P2_P3 = 	owl.defineInstance("sr_p2_p3", owl.getClass("RCC8"));
										
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P2_P3, p2);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P2_P3, p3);
		
		
		OWLNamedIndividual SR_P3_P0 = 	owl.defineInstance("sr_p3_p0", owl.getClass("RCC8"));
		
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P3_P0, p3);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P3_P0, p0);
		
		
		OWLNamedIndividual SR_P3_P1 = 	owl.defineInstance("sr_p3_p1", owl.getClass("RCC8"));
		
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P3_P1, p3);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P3_P1, p1);
		
		
		OWLNamedIndividual SR_P3_P2 = 	owl.defineInstance("sr_p3_p2", owl.getClass("RCC8"));
										owl.addToIndividual(SR_P3_P2, owl.getClass("NTPP"));
		
		owl.defineObjectProperty(owl.getObjectProperty("from"), SR_P3_P2, p3);
		owl.defineObjectProperty(owl.getObjectProperty("to"), SR_P3_P2, p2);
		
		owl.assertDifferentIndividuals(owl.getAllInds());
			
		//owl.saveOntologyOWLFormat(new File("/Users/Macsee/Desktop/Examples/out.owl"));
		
		//owl.printClasses(owl.getAssertedMembershipForInd(p3), "P3 Asserted ");
		//owl.print(owl.getInferredMembershipForInd(p3), "P3 Inferred ");
		
//		owl.print(owl.getAssertedMembershipForInd(owl.getIndividual("wh1")), "WH1 Asserted ");
//		owl.print(owl.getInferredMembershipForInd(owl.getIndividual("wh1")), "WH1 Inferred ");
		
		
	}
	
	public static void main(String[] args) {
		
		
			//File file = new File("/Users/Macsee/Desktop/Examples/out.owl");
			File file = new File("/Users/Macsee/Desktop/Examples/testo.owl");
			System.out.println("Se abre la ontolog’a: "+file.getAbsolutePath());
			System.out.println();
			new Test().open(file);		
	
	}
}

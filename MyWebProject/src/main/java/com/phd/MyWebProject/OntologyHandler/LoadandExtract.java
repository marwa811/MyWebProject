package com.phd.MyWebProject.OntologyHandler;

import java.io.File;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class LoadandExtract {

	public static Set<OWLOntology> laodOntologyUsingFileName(String filename)  throws OWLOntologyCreationException, OWLException{
		OWLOntology o = null;
		Set<OWLOntology> allOntologies=null;
		try {
			 OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			 File file = new File(filename);
			 o = man.loadOntologyFromOntologyDocument(file);
			 allOntologies=man.getOntologies();
			
			 System.out.println(filename+ " loading ...");
			 }
			catch(OWLOntologyCreationException e) {
				 System.out.println("error in loading the ontology  "+e);
			}	
		return allOntologies;
	}
}
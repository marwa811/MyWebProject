package com.phd.MyWebProject.services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

import com.phd.MyWebProject.OntologyHandler.ClassAndLabel;

public interface ClassAndLabelADO {
	  //	Set<ClassAndLabel> findAllLabels();
	//  	User find(long id);
	 // 	void save(User user);
	 // 	void delete(User user);
	 // 	User findUserByName(String name);

		Set<ClassAndLabel> findAllLabels(Set<OWLOntology> ontology) throws IOException;
		List<ClassAndLabel> getMatchedClasses(ClassAndLabel owlclass, String ontologyName) throws OWLException, IOException;
}

package com.phd.MyWebProject.services.Impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

import com.phd.MyWebProject.OntologyHandler.ClassAndLabel;
import com.phd.MyWebProject.OntologyHandler.ExtractOntologyEntity;
import com.phd.MyWebProject.OntologyHandler.LoadandExtract;
import com.phd.MyWebProject.OntologyHandler.OntologyMatching;
import com.phd.MyWebProject.services.ClassAndLabelADO;

public class ClassAndLabelADOImpl implements ClassAndLabelADO{
	
	@Override
    @SuppressWarnings({ "unchecked" })	
	public Set<ClassAndLabel> findAllLabels(Set<OWLOntology> ontology) throws IOException{
		Set<ClassAndLabel> classandlabels=ExtractOntologyEntity.getOntolgyClassesLabels(ontology);
		for (ClassAndLabel a:classandlabels)
			System.out.println(a.getLabel());
		return classandlabels;	
	}

	@Override
    @SuppressWarnings({ "unchecked" })	
	public List<ClassAndLabel> getMatchedClasses(ClassAndLabel owlclass, String ontologyName) throws OWLException, IOException {
		Set<OWLOntology> Allontologies=LoadandExtract.laodOntologyUsingFileName(ontologyName);
		Set<ClassAndLabel> classandlabels2=ExtractOntologyEntity.getOntolgyClassesLabels(Allontologies);
		List<ClassAndLabel> matchedClasses=OntologyMatching.getMatchedClasses(owlclass, classandlabels2);
		return matchedClasses;
	}


}

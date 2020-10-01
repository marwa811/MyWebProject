package com.phd.MyWebProject.OntologyHandler;

import org.semanticweb.owlapi.model.OWLClass;

import com.phd.MyWebProject.OntologyHandler.ClassAndLabel;

public class ClassAndLabel {
	private  OWLClass ontologyClass;
	private  String label;

	public ClassAndLabel() {
		setOntologyClass(null);
		setLabel(null);
	}
	
	public ClassAndLabel(OWLClass c, String s) {
		this.setOntologyClass(c);
		this.setLabel(s);
	}

	public  OWLClass getOntologyClass() {
		return ontologyClass;
	}

	public void setOntologyClass(OWLClass ontologyClass) {
		this.ontologyClass = ontologyClass;
	}	
	
	public  String getLabel() {
		return label;
	}

	public  void setLabel(String label) {
		this.label = label;
	}

	
	public String print() {
		return (getOntologyClass().getIRI()+ "   "+ getLabel()+'\n');
		//System.out.println(ontologyClass.getIRI()+ "   "+ label);
	}
	
	public void add(ClassAndLabel e) {
		this.ontologyClass=e.getOntologyClass();
		this.label=e.getLabel();
	}
	
}

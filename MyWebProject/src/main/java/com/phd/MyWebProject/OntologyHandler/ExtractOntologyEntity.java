package com.phd.MyWebProject.OntologyHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.search.EntitySearcher;

public class ExtractOntologyEntity {
	
	private static IRI prefLabelIri = IRI.create("http://www.w3.org/2004/02/skos/core#prefLabel");
	private static IRI altLabelIri = IRI.create("http://www.w3.org/2004/02/skos/core#altLabel");

	///Class Extraction Methods///////	
	//get all ontologies that are imported in a given ontology//
	public static void getAllOntologyClasses(OWLOntology o) {
		Set<OWLClass> ontologyclasses=o.getClassesInSignature();
		Set<OWLOntology> importedOntologies=o.getImports();
		for(OWLOntology importedOntology: importedOntologies)
			ontologyclasses.addAll(importedOntology.getClassesInSignature());
	}
	
	//get all classes and class labels for an ontology (including imported ones)//
	public static Set<ClassAndLabel> getOntolgyClassesLabels(Set<OWLOntology> allOntologies) throws IOException{
		Set<ClassAndLabel> classandLabelSet=new HashSet<ClassAndLabel>();
		
		String classLabel;	
		ClassAndLabel cl=null; 		
		//Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("filename.txt"), "utf-8"));

		if(allOntologies.size()>0) {
	    	for(OWLOntology ontology: allOntologies) {
    			//writer.write("Ontology: "+ontology.getOntologyID().toString()+'\n');
	    		Set<OWLClass> classes = ontology.getClassesInSignature();
	    		//writer.write("No of classes: "+classes.size()+'\n');
	    		
	    		//get class_labels no duplicates, no classes without labels
	    		for (OWLClass c : classes) {
	    			classLabel=getClassName(ontology,c);	    				    			
	    			if(classLabel!=null) {
	    				cl=new ClassAndLabel(c,classLabel);
	    				if(!existsIn(classandLabelSet,cl)) {
	    					classandLabelSet.add(cl);
	    					//writer.write(cl.print());
	    				}
	    				else continue;
	    			}
	    				    		
	    		}
	    		
	  	    }
		}
		//writer.close();
		return classandLabelSet;
	}
	
	//Check if this class already exists in the class$Label Set//
	private static boolean existsIn(Set<ClassAndLabel> clSet, ClassAndLabel cl)
	{
		boolean bool=false;
		for(ClassAndLabel classandlabel:clSet) 
			if((classandlabel.getOntologyClass().getIRI()==cl.getOntologyClass().getIRI())
			||((classandlabel.getLabel()).equals(cl.getLabel())))
				bool=true;
		return bool;		
	}
	
	//Given a class and an ontology retuen the RDFS:Label for that class
	private static String getClassName(OWLOntology o, OWLClass c) throws IOException {
	      Iterator<OWLAnnotation> iterator = EntitySearcher.getAnnotations(c, o).iterator();
	      while (iterator.hasNext()) {
	      	final OWLAnnotation an = iterator.next();
	                if (an.getProperty().isLabel()) {
	                    OWLAnnotationValue val = an.getValue();

	                    if (val instanceof IRI) {
	                        return ((IRI) val).toString();
	                    } else if (val instanceof OWLLiteral) {
	                        OWLLiteral lit = (OWLLiteral) val;
	                        return lit.getLiteral();
	                    } else if (val instanceof OWLAnonymousIndividual) {
	                        OWLAnonymousIndividual ind = (OWLAnonymousIndividual) val;
	                        return ind.toStringID();
	                    } else {
	                        throw new RuntimeException("Unexpected class "+ val.getClass());
	                    }
	                }
	            }
	            return c.toStringID();
	}
	/*private static String getClassName(OWLOntology o, OWLClass c) throws IOException {
		String classLabel= new String();
		for (OWLAnnotation a : EntitySearcher.getAnnotations(c, o)) {
		// properties are of several types: rdfs-label, altLabel or prefLabel
			OWLAnnotationProperty prop = a.getProperty();
			OWLAnnotationValue val = a.getValue();
			if (val instanceof OWLLiteral) {
				// RDFS-labels, preferred or alternative labels
				if (prop.isLabel()||prop.getIRI().equals(prefLabelIri) || prop.getIRI().equals(altLabelIri)) {
					classLabel=((OWLLiteral) val).getLiteral();
				}
			}
		}	
		if(!classLabel.isEmpty())
			return classLabel;
		else return null;
	}*/
	
	//Print all classes & their labels
	public static void printClassName(Set<ClassAndLabel> classesandlabels) throws IOException {		
		for(ClassAndLabel c: classesandlabels) {
			System.out.println(c.print());
		}
	}
}
	

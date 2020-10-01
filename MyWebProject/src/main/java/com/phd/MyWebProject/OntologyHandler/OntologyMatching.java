package com.phd.MyWebProject.OntologyHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLException;

public class OntologyMatching {
	public static List<MatchedClasses> matchedClassandLabels=new ArrayList<MatchedClasses>(); 
	public static List<ClassAndLabel> listOfMatchedCAndL=null;
	public static MatchedClasses match=new MatchedClasses();

	//For a set of class and label from one ontology, find all the matched classes from another ontology//
	public static void getMatchedClasses(Set<ClassAndLabel> classandLabels1,Set<ClassAndLabel> classandLabels2) throws OWLException{
		
		for (ClassAndLabel cAndl1 : classandLabels1) {
			listOfMatchedCAndL=new ArrayList<ClassAndLabel>();

			if(isExistsPartialMatch(cAndl1,classandLabels2)) {
				ClassAndLabel FistArgument=new ClassAndLabel(cAndl1.getOntologyClass(),cAndl1.getLabel());
				match.setClassAndLabelobject(FistArgument);
				match.setMatchedClassAndLabelList(listOfMatchedCAndL);
				System.out.println("Fist Argument:  "+FistArgument.print() );
				System.out.println("Second Argument:");
				for(ClassAndLabel test:listOfMatchedCAndL)
					System.out.println(test.print());
			}
			
			matchedClassandLabels.add(match);
		}
	}

	//For any owlclass from one ontology, find all the matched classes from another ontology//
	public static List<ClassAndLabel> getMatchedClasses(ClassAndLabel owlclass,Set<ClassAndLabel> classandLabels2) throws OWLException{				
			listOfMatchedCAndL=new ArrayList<ClassAndLabel>();

			if(isExistsPartialMatch(owlclass.getLabel(),classandLabels2)) {
				return listOfMatchedCAndL;
			}
			return null;
	}

	//test if there are any match in the other ontology, if yes put all matches in listOfMatchedCAndL
	private static boolean isExistsPartialMatch(String label, Set<ClassAndLabel> clset) {
		boolean flag=false;
		for(ClassAndLabel c:clset) {
			if(c.getLabel().toLowerCase().contains(label.toLowerCase())) {
				listOfMatchedCAndL.add(c);
				flag= true;
				}
		}
		return flag;
	}
	private static boolean isExistsPartialMatch(ClassAndLabel cl, Set<ClassAndLabel> clset) {
		boolean flag=false;
		for(ClassAndLabel c:clset) {
			if(c.getLabel().toLowerCase().contains(cl.getLabel().toLowerCase())) {
				listOfMatchedCAndL.add(c);
				flag= true;
				}
		}
		return flag;
	}
	
	private static boolean isExistsExactMatch(ClassAndLabel cl, Set<ClassAndLabel> clset) {
		for(ClassAndLabel c:clset) {
			if(c.getLabel().toLowerCase().equals(cl.getLabel().toLowerCase())) {
				return true;
				}
		}
		return false;
	}
}

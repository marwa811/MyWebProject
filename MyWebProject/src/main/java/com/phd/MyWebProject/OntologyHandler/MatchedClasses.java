package com.phd.MyWebProject.OntologyHandler;

import java.util.List;

public class MatchedClasses {
	private ClassAndLabel classAndLabelobject;
	private List<ClassAndLabel> matchedClassAndLabelList;
	
	public MatchedClasses() {
		setClassAndLabelobject(null);
		setMatchedClassAndLabelList(null);
	}
	
	public MatchedClasses(ClassAndLabel c, List<ClassAndLabel> s) {
		this.setClassAndLabelobject(c);
		this.setMatchedClassAndLabelList(s);
	}
	
	public ClassAndLabel getClassAndLabelobject() {
		return classAndLabelobject;
	}
	public void setClassAndLabelobject(ClassAndLabel classAndLabel) {
		this.classAndLabelobject = classAndLabel;
	}
	
	public List<ClassAndLabel> getMatchedClassAndLabelList() {
		return matchedClassAndLabelList;
	}
	public void setMatchedClassAndLabelList(List<ClassAndLabel> matchedClassAndLabel) {
		this.matchedClassAndLabelList = matchedClassAndLabelList;
	}
}

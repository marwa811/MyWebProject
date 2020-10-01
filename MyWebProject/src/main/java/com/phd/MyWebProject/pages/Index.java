package com.phd.MyWebProject.pages;


import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.semanticweb.owlapi.model.OWLClass;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.slf4j.Logger;

import de.uni_stuttgart.vis.vowl.owl2vowl.Owl2Vowl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.phd.MyWebProject.OntologyHandler.ClassAndLabel;
import com.phd.MyWebProject.OntologyHandler.LoadandExtract;
import com.phd.MyWebProject.services.ClassAndLabelADO; 
/**
 * Start page of application MyWebProject.
 */
public class Index
{
  @Inject
  private Logger logger;

  @InjectPage
  private About about;

  @Inject
  private Block block;
  
  @Property
  private UploadedFile file;
  
  @Property
  @Persist
  private Set<ClassAndLabel> classandlabels;
 
  @Property
  @Persist
  private List<ClassAndLabel> matchedclassandlabels;
    
  @Property
  private ClassAndLabel classandlabel;
  
  @Property
  private List<String> labels;
  
  @Property
  private String label;
  
  @Property
  private OWLClass owlclass;
  
  private File owlFile;
  private Set<OWLOntology> ontology1;
  
  @Inject
  private ClassAndLabelADO classlabelDAO;
  
  @InjectPage
  private webVOWL webVOWL;
  
  @Property
  @Persist(PersistenceConstants.FLASH)
  private String message;
  
  @Property
  @Persist(PersistenceConstants.FLASH)
  private String message2;


  @OnEvent(component = "upload", value = "selected")
  public void onUpload() throws Exception {
	  owlFile = new File(file.getFileName());
	   
      file.write(owlFile);
            
      logger.info("File "+owlFile.getName()+" was selected");
	  
     int noOfClasses= extractClassesandLabelsfromOntology(owlFile);
	  
      logger.info("Done");
     
      if (noOfClasses==0)
    	  message="The ontology has No classes!!";
      else 		        
    	  message="Classes for "+owlFile.getName()+" Ontology , "
    	  		+ "The ontology has "+ noOfClasses+ " classes";
     }

  @OnEvent(component = "visualize", value = "selected")
  public Object onVisualize() throws Exception{
	  
	  owlFile = new File(file.getFileName()); 
      file.write(owlFile);            
      logger.info("File "+owlFile.getName()+" was selected");
	  return webVOWL;
  }

  void onLabel(String label) throws OWLOntologyCreationException, IOException, OWLException {
	  logger.info("class label is: "+label );
	  OWLClass ontClass=getOWLCLass(label);
	  ClassAndLabel c1=new ClassAndLabel(ontClass,label);
	  // extract classes and labels from envo.owl
	  File envoFile = new File("envo.owl");
	  
      logger.info("File "+envoFile.getName()+" was selected");
	  
      //Set<ClassAndLabel> targetclassandlabels=ExtractOntologyEntity.getOntolgyClassesLabels(allOntologies);
      extractClassesandLabelsfromTargetOntology(envoFile,c1);
      message2="Matched Classes for class: "+label;
  }
  
  //convert OWL file to JSON format for visualization
  private File convertOWLtoJSON(File owlfile) throws Exception {
	  InputStream OWLOntologyFile;
    
      //convert the file to InputStream
      try {
    	  OWLOntologyFile = new FileInputStream(owlFile);
      }catch (Exception e) 
      {
    	  throw new IOException(e);
      }
      //convert OWL Ontology file to .json format (OWL2VOWL format)
      
      Owl2Vowl convertedFile;
      try {
 
    	  convertedFile= new Owl2Vowl(OWLOntologyFile);
          logger.info("File Converted to json format successfully!");
    	  
      }catch (Exception e)
      {
    	  logger.info(" File not Converted to json format successfully!"+ e.getMessage());
    	  throw new Exception(e);
      }                
      //Output json file for the ontology
      File outJSONfile=new File(file.getFileName().replaceAll("owl","json"));
      convertedFile.writeToFile(outJSONfile);
      logger.info(outJSONfile.getName()+" Ontology JSON file outputted successfully!"); 
      return outJSONfile;
  }
  
  
  
  private void loadOntology(File owlFile) throws OWLOntologyCreationException, OWLException {
	  ontology1=new HashSet<OWLOntology>();
	  ontology1=LoadandExtract.laodOntologyUsingFileName(owlFile.getName());
      logger.info("File "+owlFile.getName()+" was loaded successfully");
  }
  
  private int extractClassesandLabelsfromOntology(File owlFile) throws IOException, OWLOntologyCreationException, OWLException {
	  loadOntology(owlFile);
	  labels=new ArrayList<String>();  
	  classandlabels=new HashSet<ClassAndLabel>();
	  classandlabels=classlabelDAO.findAllLabels(ontology1);
	  for(ClassAndLabel a:classandlabels) {
    	  labels.add(a.getLabel());
      }
	     for(String s: labels)
	    	  logger.info(s); 
	     return classandlabels.size();
  }
  
  private void extractClassesandLabelsfromTargetOntology(File owlFile,ClassAndLabel owlclass) throws IOException, OWLOntologyCreationException, OWLException {
	  loadOntology(owlFile);
	  matchedclassandlabels=new ArrayList<ClassAndLabel>();
	  matchedclassandlabels=classlabelDAO.getMatchedClasses(owlclass, owlFile.getName());
  }
  
  //To return Grid results//
  public Set<ClassAndLabel> getclassandlabels() throws IOException {	
	  return classandlabels;
	  }
  
  //To return Grid of (matched classes) results//
  public List<ClassAndLabel> getmatchedclassandlabels() throws IOException {	
	  return matchedclassandlabels;
	  }
  
  public OWLClass getOWLCLass(String lbl) {
	  for(ClassAndLabel clset:classandlabels) {
		  if(lbl.equals(clset.getLabel()))
			  return clset.getOntologyClass();
	  }
	  return null;
  }
  
  // Handle call with an unwanted context
  Object onActivate(EventContext eventContext)
  {
    return eventContext.getCount() > 0 ?
        new HttpError(404, "Resource not found") :
        null;
  }
}

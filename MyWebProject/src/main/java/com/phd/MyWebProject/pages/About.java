package com.phd.MyWebProject.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.slf4j.Logger;

import de.uni_stuttgart.vis.vowl.owl2vowl.Owl2Vowl;

public class About
{
	  @Inject
	  private Logger logger;
	  
      @Property
      private UploadedFile file;

      public void onSuccess() throws Exception
      {
          File owlFile = new File(file.getFileName());
       
          file.write(owlFile);
          
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
              logger.info(convertedFile.getJsonAsString()+"    Converted to json format successfully!");
        	  
          }catch (Exception e)
          {
        	  logger.info(" File not Converted to json format successfully!"+ e.getMessage());
        	  throw new Exception(e);
          }
                    
          //Output json file for the ontology
          File out=new File(file.getFileName().replaceAll("owl","json"));
          convertedFile.writeToFile(out);
          logger.info(out.getName()+"    Ontology JSON file outputted successfully!");

      }
      
      @Persist(PersistenceConstants.FLASH)
      @Property
      private String message;


      Object onUploadException(FileUploadException ex)
      {
          message = "Upload exception: " + ex.getMessage();
          return this;
      }

}

package com.phd.MyWebProject.pages;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.slf4j.Logger;

@Import(stylesheet= {"context:css/webvowl.app.css","context:css/webvowl.css" },
		library={"context:js/d3.min.js","context:js/webvowl.js","context:js/webvowl.app.js"})

public class webVOWL {
		
	@Environmental 
	private JavaScriptSupport javaScriptSupport; 
	
	@Inject
	private Logger logger;

    @SuppressWarnings("deprecation")
	public void afterRender() {
  //     	javaScriptSupport.require("context:js/webvowl.app.js");

//		javaScriptSupport.addScript("webvowl.app();");
//    javaScriptSupport.addScript("webvowl.app().initialize;");
  //  	javaScriptSupport.require("webvowl.app");
    	
    }
    
    //@SuppressWarnings("deprecation")
	//public void callJavaScript() {
    	//javaScriptSupport.addScript("window.onload =webvowl.app().initialize;");
    //	javaScriptSupport.require("app.js").invoke("app.initialize");
    
    }
   
    

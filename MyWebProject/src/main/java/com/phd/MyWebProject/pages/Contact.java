package com.phd.MyWebProject.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.PublishEvent;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.Hidden;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

@Import (library="context:js/test.js")
public class Contact
{
    @OnEvent("answer")
    @PublishEvent
	public void answer(@RequestParameter("myData") String test) 
    { 
    	System.out.println("tesssst:  "+ test);	
    }    
}

package dinamica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Transaction module for the general error manager
 * 
 * <br>
 * Creation date: jan/13/2004<br>
 * Last Update: 2009-09-01<br>
 * (c) 2004 Martin Cordova<br>
 * This code is released under the LGPL license<br>
 * @author Martin Cordova
 * */
public class ErrorHandler extends GenericTransaction
{

	/* (non-Javadoc)
	 * @see dinamica.GenericTransaction#service(dinamica.Recordset)
	 */
	public int service(Recordset inputParams) throws Throwable
	{
		String fechaActual= new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		String sistemaOperativo = System.getProperty("os.name");
		String file;
		  
		if(sistemaOperativo.equals("Windows 7") || sistemaOperativo.equals("Windows 8") || sistemaOperativo.equals("Windows 10")) 
			file =  "C:/log/logDebugErrorHandle_"+fechaActual+".txt";
		else
			file = "./logDebugErrorHandle_"+fechaActual+".txt";
		
		File newLogFile = new File(file);
		FileWriter fw;
		String str="";
		
		if(!newLogFile.exists())
			fw = new FileWriter(newLogFile);
		else
			fw = new FileWriter(newLogFile,true);
		
		BufferedWriter bw=new BufferedWriter(fw);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		str = "----------"+timestamp+"----------";	
		bw.write(str);
		bw.flush();
		bw.newLine();
		bw.newLine();
		
		str = "Ejecutando ErrorHandler...";	
		bw.write(str);
		bw.flush();
		bw.newLine();	

		//borrar archivo temporal si se esta usando uno
		String fileName = getRequest().getParameter("_tempfile");
		if (fileName!=null && !fileName.equals("")) {
			File f = new File(fileName);
			f.delete();
		} else {
			fileName = getRequest().getParameter("file");
			if (fileName!=null && !fileName.equals("")) {
				File f = new File(fileName);
				f.delete();
			}
		}
		
		//capture referer page
		String referer = getRequest().getHeader("Referer");
		if (referer==null)
			referer = "[No disponible]";
		
		getRequest().setAttribute("dinamica.error.referer", referer);
		getRequest().setAttribute("dinamica.error.context", getRequest().getContextPath());
		
	    String key = "dinamica.error.exception";
		Throwable err = (Throwable)getRequest().getAttribute(key);
	    String errUri = (String)getRequest().getAttribute("dinamica.error.url");
	    
	    if (errUri==null) {
	    	errUri = (String)getRequest().getAttribute("javax.servlet.error.request_uri");
	    	getRequest().setAttribute("dinamica.error.url", errUri);
	    }
	    
	    //error triggered by servlet distinct from Controller or maybe a filter?
	    if (err==null) {
			err = (Throwable)getRequest().getAttribute("javax.servlet.error.exception");
			getRequest().setAttribute("dinamica.error.description", err.getMessage());
	    }
		
		//default log to container
		if (err!=null) {
		
			getContext().log("[Dinamica_Exception] " + (String)getRequest().getAttribute("dinamica.error.description") 
		    		+ " context: " + getRequest().getContextPath() 
		    		+ " uri:" + errUri 
		    		+ " referer:" + referer);
			
			String isForward = (String) getRequest().getAttribute("javax.servlet.forward.request_uri");
			if (isForward!=null && getRequest().getAttribute("javax.servlet.error.request_uri")==null) {
				getContext().log("[Dinamica_Info] ErrorHandler invocado con un FORWARD desde el Controller, dejando traza del error...");
				err.printStackTrace();
			}
			
			str = "[Dinamica_Exception] " + (String)getRequest().getAttribute("dinamica.error.description") 
					+ " context: " + getRequest().getContextPath() 
					+ " uri:" + errUri 
					+ " referer:" + referer;	
					bw.write(str);
					bw.flush();
					bw.newLine();
					bw.close();
		}
		    		
		try
		{

			super.service(inputParams);
			
			// capture stack trace for exceptions
			// raised by non-Dinamica Actions
			String trace = (String)getRequest().getAttribute("dinamica.error.stacktrace");
			if (trace==null)
			{
				//this exception was not raised by dinamica.Controller
				//use standard J2EE request attributes
				if (err!=null)
				{			
					//get stack trace
					StringWriter s = new StringWriter();
					err.printStackTrace(new PrintWriter(s));
					getRequest().setAttribute("dinamica.error.stacktrace", s.toString());
				}
					
			}

		}
		catch (Throwable e)
		{
			//log to container if the error handler fails
			getContext().log("[Dinamica_Warning] ErrorHandler failed: " + e.getMessage());
			throw e;
		}

		return 0;
		
	}

}

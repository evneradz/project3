import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import java.lang.String;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

public class UploadServlet extends HttpServlet {
   
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 1600 * 1600;
   private int maxMemSize = 1600 * 1600;
   private File file ;
   String info;
   String[] reply = new String[2];
   String details = "";
   String rotation= "0";
   String height= "400";
   String width = "400"; 
String store_name;



   public void init( ){
      // Get the file location where it would be stored.
      filePath = 
             getServletContext().getInitParameter("file-upload"); 
   }
   public void doPost(HttpServletRequest request, 
               HttpServletResponse answer)
              throws ServletException, java.io.IOException {
      // Check that we have a file upload request
      isMultipart = ServletFileUpload.isMultipartContent(request);
      answer.setContentType("text/html");
      java.io.PrintWriter out = answer.getWriter( );
      if( !isMultipart ){
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
	 out.println("<p>No file uploaded</p>"); 
         out.println("</body>");
         out.println("</html>");


         return;
      }

      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);

      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("/usr/share/tomcat6/webapps/upload/image/"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );

      try{ 
      // Parse the request to get file items.
      List fileItems = upload.parseRequest(request);
	
      // Process the uploaded file items
      Iterator i = fileItems.iterator();
	
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet upload</title>");  
      out.println("</head>");
      out.println("<body>");
      while ( i.hasNext () ) 
      {
         FileItem fi = (FileItem)i.next();
         if ( !fi.isFormField () )	
         {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            String fileName = fi.getName();
            String contentType = fi.getContentType();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            // create the file
            if( fileName.lastIndexOf("/") >= 0 ){
               file = new File( filePath + 
               fileName.substring( fileName.lastIndexOf("/"))) ;

            }else{
               file = new File( filePath + 
               fileName.substring(fileName.lastIndexOf("/")+1)) ;
			   
            }
		
		reply[0] = store_name = fileName; 
		fi.write( file ) ;   	    	              
         }
		 
		 else {
        //create xml file
             if(fi.getFieldName().equals("details")) {
                 details = fi.getString();
             }
			 reply[1] = details;
            
			PrintWriter out_xml = new PrintWriter("/usr/share/tomcat6/webapps/upload/WEB-INF/" + store_name.replaceFirst("[.][^.]+$", "") + ".xml");
			out_xml.println("<image>\n <details>" + reply[1] + "</details>\n <height>" + height + "</height>\n <width>" + width + "</width>\n <rotation>" + rotation + "</rotation>\n</image>");
			out_xml.close();
            
			//reply[1] = details;
			
		 }
		 
        }
	
          //  fi.write( file ) ;
          out.println("" + store_name + "<br>" +reply[1]+"<br>");
	  out.println("<table><tr><td width='210'></td><td> <img border=2 src=image/"+ store_name +" width=500 height=500></td></tr></table>");

	

    
      out.println("</body>");
      out.println("</html>");

   }catch(Exception ex) {
       System.out.println(ex);
   }
//button to return to home page
	out.println("<li><a href="+"http://83.212.101.53:8080/upload/start"+">back</a></li>");


   }
 public void doGet(HttpServletRequest request, 
                       HttpServletResponse answer)
        throws ServletException, java.io.IOException {
	//send all home page	
	answer.setContentType("text/html");
        String[] reply;
        File folder = new File("/usr/share/tomcat6/webapps/upload/image/");
        File[] listOfFiles = folder.listFiles(); 
        reply = new String[listOfFiles.length];
        


        for(int i = 0; i < reply.length; i++) {
            reply[i] = listOfFiles[i].getName();
        }       
		request.setAttribute("styles", reply);
		RequestDispatcher view = request.getRequestDispatcher("upload.jsp");
		


		try {    
		    view.forward(request, answer);
		} 
		catch (ServletException ex) {} 
		catch (IOException ex) {} 
		
   } 
}

